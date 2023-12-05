package com.creelayer.marketplace.crm.vocabulary.core.model;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.*;


@NoArgsConstructor
@NotNull
@Entity
public class VocabularyItem extends Aggregate<VocabularyItem> {


    @Id
    @GeneratedValue
    private UUID uuid;

    @Getter
    private String alias;

    @Getter
    @Setter
    @Basic(optional = false)
    private String name;

    @Getter
    @Setter
    @Basic
    private Integer weight;

    @Getter
    @Setter
    @Basic(optional = false)
    private boolean isDefault;

    @Getter
    @Setter
    @Basic(optional = false)
    private boolean isEditable = true;

    @Getter
    @Setter
    @Basic(optional = false)
    private boolean isDeletable = true;

    @Getter
    @Setter
    @Basic(optional = false)
    private boolean isForward;

    @Getter
    @Type(ListArrayType.class)
    @Column(name = "forwards", columnDefinition = "text[]")
    private  List<String> forwards = new ArrayList<>();

    public VocabularyItem(String alias, String name) {
        this.alias = alias;
        this.name = name;
    }

    public VocabularyItem(String alias, String name, Integer weight, boolean isForward, Set<String> forwards) {
        this.alias = alias;
        this.name = name;
        this.weight = weight;
        this.isForward = isForward;
        this.forwards = forwards.stream().toList();
    }
}
