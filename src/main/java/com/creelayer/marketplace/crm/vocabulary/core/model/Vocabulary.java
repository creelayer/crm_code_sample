package com.creelayer.marketplace.crm.vocabulary.core.model;

import com.creelayer.marketplace.crm.vocabulary.core.exception.VocabularyItemException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor
@NotNull
@Entity
@Table(indexes = {
        @Index(name = "idx_m_a_u", columnList = "realm, alias", unique = true),
})
public class Vocabulary extends Aggregate<Vocabulary> {

    @Getter
    @Id
    @GeneratedValue
    private UUID uuid;

    @Getter
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "uuid", column = @Column(name = "realm"))})
    private Realm realm;

    @Getter
    @Basic(optional = false)
    private String name;

    @Getter
    @Basic
    private String alias;

    @Getter
    @Setter
    @Basic(optional = false)
    private boolean isEditable = true;

    @Getter
    @Setter
    @Basic(optional = false)
    private boolean isDeletable = true;

    @OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "vocabulary", referencedColumnName = "uuid")
    private final Set<VocabularyItem> items = new HashSet<>();

    public Vocabulary(Realm realm, String name, String alias) {
        this.realm = realm;
        this.name = name;
        this.alias = alias;
    }

    public Optional<VocabularyItem> getItem(String alias) {
        return items.stream().filter(e -> e.getAlias().equals(alias)).findAny();
    }

    public void putItem(VocabularyItem item) {

        VocabularyItem exist = getItem(item.getAlias()).orElse(null);

        if (exist != null && !item.isEditable())
            throw new VocabularyItemException("Item exist and is not editable");

        items.removeIf(e -> e.getAlias().equals(item.getAlias()));

        items.add(item);
    }


    public void removeItem(String alias) {

        VocabularyItem item = getItem(alias).orElseThrow(() -> new VocabularyItemException("Item is not exist"));

        if (!item.isDeletable())
            throw new VocabularyItemException("Item is not deletable");

        if (item.isDefault())
            throw new VocabularyItemException("Cant delete default item");

        items.removeIf(e -> e.getAlias().equals(item.getAlias()));
    }
}
