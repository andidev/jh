package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A MultiRelationalDisplayFieldEntity.
 */
@Entity
@Table(name = "MULTIRELATIONALDISPLAYFIELDENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MultiRelationalDisplayFieldEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @OneToOne
    private OneToOneDisplayFieldEntity oneToOneDisplayFieldEntity;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "MULTIRELATIONALDISPLAYFIELDENTITY_MANYTOMANYDISPLAYFIELDENTITY",
               joinColumns = @JoinColumn(name="multirelationaldisplayfieldentitys_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="manytomanydisplayfieldentitys_id", referencedColumnName="ID"))
    private Set<ManyToManyDisplayFieldEntity> manyToManyDisplayFieldEntitys = new HashSet<>();

    @ManyToOne
    private OneToManyDisplayFieldEntity oneToManyDisplayFieldEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OneToOneDisplayFieldEntity getOneToOneDisplayFieldEntity() {
        return oneToOneDisplayFieldEntity;
    }

    public void setOneToOneDisplayFieldEntity(OneToOneDisplayFieldEntity oneToOneDisplayFieldEntity) {
        this.oneToOneDisplayFieldEntity = oneToOneDisplayFieldEntity;
    }

    public Set<ManyToManyDisplayFieldEntity> getManyToManyDisplayFieldEntitys() {
        return manyToManyDisplayFieldEntitys;
    }

    public void setManyToManyDisplayFieldEntitys(Set<ManyToManyDisplayFieldEntity> manyToManyDisplayFieldEntitys) {
        this.manyToManyDisplayFieldEntitys = manyToManyDisplayFieldEntitys;
    }

    public OneToManyDisplayFieldEntity getOneToManyDisplayFieldEntity() {
        return oneToManyDisplayFieldEntity;
    }

    public void setOneToManyDisplayFieldEntity(OneToManyDisplayFieldEntity oneToManyDisplayFieldEntity) {
        this.oneToManyDisplayFieldEntity = oneToManyDisplayFieldEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultiRelationalDisplayFieldEntity multiRelationalDisplayFieldEntity = (MultiRelationalDisplayFieldEntity) o;

        if ( ! Objects.equals(id, multiRelationalDisplayFieldEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MultiRelationalDisplayFieldEntity{" +
                "id=" + id +
                '}';
    }
}
