package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the OneToOneDisplayFieldEntity entity.
 */
public class OneToOneDisplayFieldEntityDTO implements Serializable {

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

        OneToOneDisplayFieldEntityDTO oneToOneDisplayFieldEntityDTO = (OneToOneDisplayFieldEntityDTO) o;

        if ( ! Objects.equals(id, oneToOneDisplayFieldEntityDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OneToOneDisplayFieldEntityDTO{" +
                "id=" + id +
                ", displayField='" + displayField + "'" +
                '}';
    }
}
