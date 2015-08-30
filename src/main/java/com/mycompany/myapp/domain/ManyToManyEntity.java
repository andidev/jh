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
 * A ManyToManyEntity.
 */
@Entity
@Table(name = "MANY_TO_MANY_ENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ManyToManyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @ManyToMany(mappedBy = "manyToManyEntitys")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<MultiRelationalEntity> multiRelationalEntitys = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "MANY_TO_MANY_ENTITY_USER_ENTITY",
               joinColumns = @JoinColumn(name="many_to_many_entitys_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="user_entitys_id", referencedColumnName="ID"))
    private Set<User> userEntitys = new HashSet<>();

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

    public Set<User> getUserEntitys() {
        return userEntitys;
    }

    public void setUserEntitys(Set<User> users) {
        this.userEntitys = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ManyToManyEntity manyToManyEntity = (ManyToManyEntity) o;

        if ( ! Objects.equals(id, manyToManyEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ManyToManyEntity{" +
                "id=" + id +
                '}';
    }
}
