package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A OneToManyDisplayFieldEntity.
 */
@Entity
@Table(name = "ONE_TO_MANY_DISPLAY_FIELD_ENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OneToManyDisplayFieldEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "display_field")
    private String displayField;

    @OneToMany(mappedBy = "oneToManyDisplayFieldEntity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MultiRelationalDisplayFieldEntity> multiRelationalDisplayFieldEntitys = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayField() {
        return displayField;
    }

    public void setDisplayField(String displayField) {
        this.displayField = displayField;
    }

    public Set<MultiRelationalDisplayFieldEntity> getMultiRelationalDisplayFieldEntitys() {
        return multiRelationalDisplayFieldEntitys;
    }

    public void setMultiRelationalDisplayFieldEntitys(Set<MultiRelationalDisplayFieldEntity> multiRelationalDisplayFieldEntitys) {
        this.multiRelationalDisplayFieldEntitys = multiRelationalDisplayFieldEntitys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OneToManyDisplayFieldEntity oneToManyDisplayFieldEntity = (OneToManyDisplayFieldEntity) o;

        if ( ! Objects.equals(id, oneToManyDisplayFieldEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OneToManyDisplayFieldEntity{" +
                "id=" + id +
                ", displayField='" + displayField + "'" +
                '}';
    }
}
