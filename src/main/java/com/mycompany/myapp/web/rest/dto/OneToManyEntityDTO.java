package com.mycompany.myapp.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the OneToManyEntity entity.
 */
public class OneToManyEntityDTO implements Serializable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OneToManyEntityDTO oneToManyEntityDTO = (OneToManyEntityDTO) o;

        if ( ! Objects.equals(id, oneToManyEntityDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OneToManyEntityDTO{" +
                "id=" + id +
                '}';
    }
}
