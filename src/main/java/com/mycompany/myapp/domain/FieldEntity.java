package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.mycompany.myapp.domain.enumeration.Language;

/**
 * A FieldEntity.
 */
@Entity
@Table(name = "FIELD_ENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FieldEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "name")
    private String name;

    @NotNull
    @Size(min = 5, max = 30)        
    @Column(name = "email", length = 30, nullable = false)
    private String email;
    
    @Column(name = "a_double")
    private Double aDouble;

    @NotNull        
    @Column(name = "a_required_double", nullable = false)
    private Double aRequiredDouble;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "an_enum")
    private Language anEnum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getaDouble() {
        return aDouble;
    }

    public void setaDouble(Double aDouble) {
        this.aDouble = aDouble;
    }

    public Double getaRequiredDouble() {
        return aRequiredDouble;
    }

    public void setaRequiredDouble(Double aRequiredDouble) {
        this.aRequiredDouble = aRequiredDouble;
    }

    public Language getAnEnum() {
        return anEnum;
    }

    public void setAnEnum(Language anEnum) {
        this.anEnum = anEnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FieldEntity fieldEntity = (FieldEntity) o;

        if ( ! Objects.equals(id, fieldEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FieldEntity{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", email='" + email + "'" +
                ", aDouble='" + aDouble + "'" +
                ", aRequiredDouble='" + aRequiredDouble + "'" +
                ", anEnum='" + anEnum + "'" +
                '}';
    }
}
