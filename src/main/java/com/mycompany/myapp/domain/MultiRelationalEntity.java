package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A MultiRelationalEntity.
 */
@Entity
@Table(name = "MULTI_RELATIONAL_ENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MultiRelationalEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @OneToOne
    private OneToOneEntity oneToOneEntity;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "MULTI_RELATIONAL_ENTITY_MANY_TO_MANY_ENTITY",
               joinColumns = @JoinColumn(name="multi_relational_entitys_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="many_to_many_entitys_id", referencedColumnName="ID"))
    private Set<ManyToManyEntity> manyToManyEntitys = new HashSet<>();

    @ManyToOne
    private OneToManyEntity oneToManyEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OneToOneEntity getOneToOneEntity() {
        return oneToOneEntity;
    }

    public void setOneToOneEntity(OneToOneEntity oneToOneEntity) {
        this.oneToOneEntity = oneToOneEntity;
    }

    public Set<ManyToManyEntity> getManyToManyEntitys() {
        return manyToManyEntitys;
    }

    public void setManyToManyEntitys(Set<ManyToManyEntity> manyToManyEntitys) {
        this.manyToManyEntitys = manyToManyEntitys;
    }

    public OneToManyEntity getOneToManyEntity() {
        return oneToManyEntity;
    }

    public void setOneToManyEntity(OneToManyEntity oneToManyEntity) {
        this.oneToManyEntity = oneToManyEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultiRelationalEntity multiRelationalEntity = (MultiRelationalEntity) o;

        if ( ! Objects.equals(id, multiRelationalEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MultiRelationalEntity{" +
                "id=" + id +
                '}';
    }
}