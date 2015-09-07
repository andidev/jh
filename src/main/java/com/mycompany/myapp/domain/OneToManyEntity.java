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
 * A OneToManyEntity.
 */
@Entity
@Table(name = "ONE_TO_MANY_ENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OneToManyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "oneToManyEntity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MultiRelationalEntity> multiRelationalEntitys = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MultiRelationalEntity> getMultiRelationalEntitys() {
        return multiRelationalEntitys;
    }

    public void setMultiRelationalEntitys(Set<MultiRelationalEntity> multiRelationalEntitys) {
        this.multiRelationalEntitys = multiRelationalEntitys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OneToManyEntity oneToManyEntity = (OneToManyEntity) o;

        if ( ! Objects.equals(id, oneToManyEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OneToManyEntity{" +
                "id=" + id +
                '}';
    }
}
