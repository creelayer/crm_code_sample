package com.creelayer.marketplace.crm.common.jpaSpecificationProjection;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import jakarta.persistence.metamodel.*;
import jakarta.persistence.metamodel.Attribute.PersistentAttributeType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;
import java.util.*;
import java.util.regex.Pattern;

import static jakarta.persistence.metamodel.Attribute.PersistentAttributeType.*;
import static java.util.regex.Pattern.*;

public abstract class QueryUtils {

	private static final String IDENTIFIER = "[._$[\\P{Z}&&\\P{Cc}&&\\P{Cf}&&\\P{Punct}]]+";
	static final String COLON_NO_DOUBLE_COLON = "(?<![:\\\\]):";

	private static final Pattern NAMED_PARAMETER = Pattern.compile(COLON_NO_DOUBLE_COLON + IDENTIFIER + "|#" + IDENTIFIER,
			CASE_INSENSITIVE);


	private static final Map<PersistentAttributeType, Class<? extends Annotation>> ASSOCIATION_TYPES;


	static {

		StringBuilder builder = new StringBuilder();

		Map<PersistentAttributeType, Class<? extends Annotation>> persistentAttributeTypes = new HashMap<>();
		persistentAttributeTypes.put(ONE_TO_ONE, OneToOne.class);
		persistentAttributeTypes.put(ONE_TO_MANY, null);
		persistentAttributeTypes.put(MANY_TO_ONE, ManyToOne.class);
		persistentAttributeTypes.put(MANY_TO_MANY, null);
		persistentAttributeTypes.put(ELEMENT_COLLECTION, null);

		ASSOCIATION_TYPES = Collections.unmodifiableMap(persistentAttributeTypes);
	}

	/**
	 * Exclude constructor to prevent instantiation.
	 */
	private QueryUtils() {

	}

	/**
	 * Returns whether the given query contains named parameters.
	 *
	 * @param query can be {@literal null} or empty.
	 * @return whether the given query contains named parameters.
	 */
	@Deprecated
	static boolean hasNamedParameter(@Nullable String query) {
		return StringUtils.hasText(query) && NAMED_PARAMETER.matcher(query).find();
	}

	/**
	 * Turns the given {@link Sort} into {@link jakarta.persistence.criteria.Order}s.
	 *
	 * @param sort the {@link Sort} instance to be transformed into JPA {@link jakarta.persistence.criteria.Order}s.
	 * @param from must not be {@literal null}.
	 * @param cb must not be {@literal null}.
	 * @return a {@link List} of {@link jakarta.persistence.criteria.Order}s.
	 */
	public static List<jakarta.persistence.criteria.Order> toOrders(Sort sort, From<?, ?> from, CriteriaBuilder cb) {

		if (sort.isUnsorted()) {
			return Collections.emptyList();
		}

		Assert.notNull(from, "From must not be null");
		Assert.notNull(cb, "CriteriaBuilder must not be null");

		List<jakarta.persistence.criteria.Order> orders = new ArrayList<>();

		for (Order order : sort) {
			orders.add(toJpaOrder(order, from, cb));
		}

		return orders;
	}

	/**
	 * Creates a criteria API {@link jakarta.persistence.criteria.Order} from the given {@link Order}.
	 *
	 * @param order the order to transform into a JPA {@link jakarta.persistence.criteria.Order}
	 * @param from the {@link From} the {@link Order} expression is based on
	 * @param cb the {@link CriteriaBuilder} to build the {@link jakarta.persistence.criteria.Order} with
	 * @return Guaranteed to be not {@literal null}.
	 */
	@SuppressWarnings("unchecked")
	private static jakarta.persistence.criteria.Order toJpaOrder(Order order, From<?, ?> from, CriteriaBuilder cb) {

		PropertyPath property = PropertyPath.from(order.getProperty(), from.getJavaType());
		Expression<?> expression = toExpressionRecursively(from, property);

		if (order.isIgnoreCase() && String.class.equals(expression.getJavaType())) {
			Expression<String> upper = cb.lower((Expression<String>) expression);
			return order.isAscending() ? cb.asc(upper) : cb.desc(upper);
		} else {
			return order.isAscending() ? cb.asc(expression) : cb.desc(expression);
		}
	}

	static <T> Expression<T> toExpressionRecursively(From<?, ?> from, PropertyPath property) {
		return toExpressionRecursively(from, property, false);
	}

	static <T> Expression<T> toExpressionRecursively(From<?, ?> from, PropertyPath property, boolean isForSelection) {
		return toExpressionRecursively(from, property, isForSelection, false);
	}

	/**
	 * Creates an expression with proper inner and left joins by recursively navigating the path
	 *
	 * @param from the {@link From}
	 * @param property the property path
	 * @param isForSelection is the property navigated for the selection or ordering part of the query?
	 * @param hasRequiredOuterJoin has a parent already required an outer join?
	 * @param <T> the type of the expression
	 * @return the expression
	 */
	@SuppressWarnings("unchecked")
	static <T> Expression<T> toExpressionRecursively(From<?, ?> from, PropertyPath property, boolean isForSelection,
			boolean hasRequiredOuterJoin) {

		String segment = property.getSegment();

		boolean isLeafProperty = !property.hasNext();

		boolean requiresOuterJoin = requiresOuterJoin(from, property, isForSelection, hasRequiredOuterJoin);

		// if it does not require an outer join and is a leaf, simply get the segment
		if (!requiresOuterJoin && isLeafProperty) {
			return from.get(segment);
		}

		// get or create the join
		JoinType joinType = requiresOuterJoin ? JoinType.LEFT : JoinType.INNER;
		Join<?, ?> join = getOrCreateJoin(from, segment, joinType);

		// if it's a leaf, return the join
		if (isLeafProperty) {
			return (Expression<T>) join;
		}

		PropertyPath nextProperty = Objects.requireNonNull(property.next(), "An element of the property path is null");

		// recurse with the next property
		return toExpressionRecursively(join, nextProperty, isForSelection, requiresOuterJoin);
	}

	/**
	 * Checks if this attribute requires an outer join. This is the case e.g. if it hadn't already been fetched with an
	 * inner join and if it's an optional association, and if previous paths has already required outer joins. It also
	 * ensures outer joins are used even when Hibernate defaults to inner joins (HHH-12712 and HHH-12999).
	 *
	 * @param from the {@link From} to check for fetches.
	 * @param property the property path
	 * @param isForSelection is the property navigated for the selection or ordering part of the query? if true, we need
	 *          to generate an explicit outer join in order to prevent Hibernate to use an inner join instead. see
	 *          https://hibernate.atlassian.net/browse/HHH-12999
	 * @param hasRequiredOuterJoin has a parent already required an outer join?
	 * @return whether an outer join is to be used for integrating this attribute in a query.
	 */
	private static boolean requiresOuterJoin(From<?, ?> from, PropertyPath property, boolean isForSelection,
			boolean hasRequiredOuterJoin) {

		String segment = property.getSegment();

		// already inner joined so outer join is useless
		if (isAlreadyInnerJoined(from, segment))
			return false;

		Bindable<?> propertyPathModel;
		Bindable<?> model = from.getModel();

		// required for EclipseLink: we try to avoid using from.get as EclipseLink produces an inner join
		// regardless of which join operation is specified next
		// see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=413892
		// still occurs as of 2.7
		ManagedType<?> managedType = null;
		if (model instanceof ManagedType) {
			managedType = (ManagedType<?>) model;
		} else if (model instanceof SingularAttribute
				&& ((SingularAttribute<?, ?>) model).getType() instanceof ManagedType) {
			managedType = (ManagedType<?>) ((SingularAttribute<?, ?>) model).getType();
		}
		if (managedType != null) {
			propertyPathModel = (Bindable<?>) managedType.getAttribute(segment);
		} else {
			propertyPathModel = from.get(segment).getModel();
		}

		// is the attribute of Collection type?
		boolean isPluralAttribute = model instanceof PluralAttribute;

		boolean isLeafProperty = !property.hasNext();

		if (propertyPathModel == null && isPluralAttribute) {
			return true;
		}

		if (!(propertyPathModel instanceof Attribute)) {
			return false;
		}

		Attribute<?, ?> attribute = (Attribute<?, ?>) propertyPathModel;

		// not a persistent attribute type association (@OneToOne, @ManyToOne)
		if (!ASSOCIATION_TYPES.containsKey(attribute.getPersistentAttributeType())) {
			return false;
		}

		boolean isCollection = attribute.isCollection();
		// if this path is an optional one to one attribute navigated from the not owning side we also need an
		// explicit outer join to avoid https://hibernate.atlassian.net/browse/HHH-12712
		// and https://github.com/eclipse-ee4j/jpa-api/issues/170
		boolean isInverseOptionalOneToOne = PersistentAttributeType.ONE_TO_ONE == attribute.getPersistentAttributeType()
				&& StringUtils.hasText(getAnnotationProperty(attribute, "mappedBy", ""));

		if (isLeafProperty && !isForSelection && !isCollection && !isInverseOptionalOneToOne && !hasRequiredOuterJoin) {
			return false;
		}

		return hasRequiredOuterJoin || getAnnotationProperty(attribute, "optional", true);
	}

	@Nullable
	private static <T> T getAnnotationProperty(Attribute<?, ?> attribute, String propertyName, T defaultValue) {

		Class<? extends Annotation> associationAnnotation = ASSOCIATION_TYPES.get(attribute.getPersistentAttributeType());

		if (associationAnnotation == null) {
			return defaultValue;
		}

		Member member = attribute.getJavaMember();

		if (!(member instanceof AnnotatedElement)) {
			return defaultValue;
		}

		Annotation annotation = AnnotationUtils.getAnnotation((AnnotatedElement) member, associationAnnotation);
		return annotation == null ? defaultValue : (T) AnnotationUtils.getValue(annotation, propertyName);
	}

	/**
	 * Returns an existing join for the given attribute if one already exists or creates a new one if not.
	 *
	 * @param from the {@link From} to get the current joins from.
	 * @param attribute the {@link Attribute} to look for in the current joins.
	 * @param joinType the join type to create if none was found
	 * @return will never be {@literal null}.
	 */
	private static Join<?, ?> getOrCreateJoin(From<?, ?> from, String attribute, JoinType joinType) {

		for (Join<?, ?> join : from.getJoins()) {

			if (join.getAttribute().getName().equals(attribute)) {
				return join;
			}
		}
		return from.join(attribute, joinType);
	}

	/**
	 * Return whether the given {@link From} contains an inner join for the attribute with the given name.
	 *
	 * @param from the {@link From} to check for joins.
	 * @param attribute the attribute name to check.
	 * @return true if the attribute has already been inner joined
	 */
	private static boolean isAlreadyInnerJoined(From<?, ?> from, String attribute) {

		for (Fetch<?, ?> fetch : from.getFetches()) {

			if (fetch.getAttribute().getName().equals(attribute) //
					&& fetch.getJoinType().equals(JoinType.INNER)) {
				return true;
			}
		}

		for (Join<?, ?> join : from.getJoins()) {

			if (join.getAttribute().getName().equals(attribute) //
					&& join.getJoinType().equals(JoinType.INNER)) {
				return true;
			}
		}

		return false;
	}
}
