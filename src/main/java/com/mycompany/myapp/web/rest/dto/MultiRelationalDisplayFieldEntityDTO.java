package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the MultiRelationalDisplayFieldEntity entity.
 */
public class MultiRelationalDisplayFieldEntityDTO implements Serializable {

    private Long id;

    private Long oneToOneDisplayFieldEntityId;

    private String oneToOneDisplayFieldEntityDisplayField;
    private Set<ManyToManyDisplayFieldEntityDTO> manyToManyDisplayFieldEntitys = new HashSet<>();

    private Long oneToManyDisplayFieldEntityId;

    private String oneToManyDisplayFieldEntityDisplayField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOneToOneDisplayFieldEntityId() {
        return oneToOneDisplayFieldEntityId;
    }

    public void setOneToOneDisplayFieldEntityId(Long oneToOneDisplayFieldEntityId) {
        this.oneToOneDisplayFieldEntityId = oneToOneDisplayFieldEntityId;
    }

    public String getOneToOneDisplayFieldEntityDisplayField() {
        return oneToOneDisplayFieldEntityDisplayField;
    }

    public void setOneToOneDisplayFieldEntityDisplayField(String oneToOneDisplayFieldEntityDisplayField) {
        this.oneToOneDisplayFieldEntityDisplayField = oneToOneDisplayFieldEntityDisplayField;
    }

    public Set<ManyToManyDisplayFieldEntityDTO> getManyToManyDisplayFieldEntitys() {
        return manyToManyDisplayFieldEntitys;
    }

    public void setManyToManyDisplayFieldEntitys(Set<ManyToManyDisplayFieldEntityDTO> manyToManyDisplayFieldEntitys) {
        this.manyToManyDisplayFieldEntitys = manyToManyDisplayFieldEntitys;
    }

    public Long getOneToManyDisplayFieldEntityId() {
        return oneToManyDisplayFieldEntityId;
    }

    public void setOneToManyDisplayFieldEntityId(Long oneToManyDisplayFieldEntityId) {
        this.oneToManyDisplayFieldEntityId = oneToManyDisplayFieldEntityId;
    }

    public String getOneToManyDisplayFieldEntityDisplayField() {
        return oneToManyDisplayFieldEntityDisplayField;
    }

    public void setOneToManyDisplayFieldEntityDisplayField(String oneToManyDisplayFieldEntityDisplayField) {
        this.oneToManyDisplayFieldEntityDisplayField = oneToManyDisplayFieldEntityDisplayField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        MultiRelationalDisplayFieldEntityDTO multiRelationalDisplayFieldEntityDTO = (MultiRelationalDisplayFieldEntityDTO) o;

        if ( ! Objects.equals(id, multiRelationalDisplayFieldEntityDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MultiRelationalDisplayFieldEntityDTO{" +
                "id=" + id +
                '}';
    }
}
