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
 * A OneToOneDisplayFieldEntity.
 */
@Entity
@Table(name = "ONETOONEDISPLAYFIELDENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OneToOneDisplayFieldEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "display_field")
    private String displayField;

    @OneToOne(mappedBy = "oneToOneDisplayFieldEntity")
    @JsonIgnore
    private MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntity;

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

    public MultiRelationalDisplayFieldEntity getMultiRelationalDisplayFieldEntity() {
        return multiRelationalDisplayFieldEntity;
    }

    public void setMultiRelationalDisplayFieldEntity(MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntity) {
        this.multiRelationalDisplayFieldEntity = multiRelationalDisplayFieldEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OneToOneDisplayFieldEntity oneToOneDisplayFieldEntity = (OneToOneDisplayFieldEntity) o;

        if ( ! Objects.equals(id, oneToOneDisplayFieldEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OneToOneDisplayFieldEntity{" +
                "id=" + id +
                ", displayField='" + displayField + "'" +
                '}';
    }
}
