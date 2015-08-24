package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the ManyToManyDisplayFieldEntity entity.
 */
public class ManyToManyDisplayFieldEntityDTO implements Serializable {

    private Long id;

    private String displayField;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ManyToManyDisplayFieldEntityDTO manyToManyDisplayFieldEntityDTO = (ManyToManyDisplayFieldEntityDTO) o;

        if ( ! Objects.equals(id, manyToManyDisplayFieldEntityDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ManyToManyDisplayFieldEntityDTO{" +
                "id=" + id +
                ", displayField='" + displayField + "'" +
                '}';
    }
}
