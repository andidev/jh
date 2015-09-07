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
 * A OneToOneEntity.
 */
@Entity
@Table(name = "ONE_TO_ONE_ENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OneToOneEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = "oneToOneEntity")
    @JsonIgnore
    private MultiRelationalEntity multiRelationalEntity;

    @OneToOne
    private User userEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultiRelationalEntity getMultiRelationalEntity() {
        return multiRelationalEntity;
    }

    public void setMultiRelationalEntity(MultiRelationalEntity multiRelationalEntity) {
        this.multiRelationalEntity = multiRelationalEntity;
    }

    public User getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(User user) {
        this.userEntity = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OneToOneEntity oneToOneEntity = (OneToOneEntity) o;

        if ( ! Objects.equals(id, oneToOneEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OneToOneEntity{" +
                "id=" + id +
                '}';
    }
}
