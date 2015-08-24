package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the MultiRelationalEntity entity.
 */
public class MultiRelationalEntityDTO implements Serializable {

    private Long id;

    private Long oneToOneEntityId;
    private Set<ManyToManyEntityDTO> manyToManyEntitys = new HashSet<>();

    private Long oneToManyEntityId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOneToOneEntityId() {
        return oneToOneEntityId;
    }

    public void setOneToOneEntityId(Long oneToOneEntityId) {
        this.oneToOneEntityId = oneToOneEntityId;
    }

    public Set<ManyToManyEntityDTO> getManyToManyEntitys() {
        return manyToManyEntitys;
    }

    public void setManyToManyEntitys(Set<ManyToManyEntityDTO> manyToManyEntitys) {
        this.manyToManyEntitys = manyToManyEntitys;
    }

    public Long getOneToManyEntityId() {
        return oneToManyEntityId;
    }

    public void setOneToManyEntityId(Long oneToManyEntityId) {
        this.oneToManyEntityId = oneToManyEntityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultiRelationalEntityDTO multiRelationalEntityDTO = (MultiRelationalEntityDTO) o;

        if ( ! Objects.equals(id, multiRelationalEntityDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MultiRelationalEntityDTO{" +
                "id=" + id +
                '}';
    }
}
