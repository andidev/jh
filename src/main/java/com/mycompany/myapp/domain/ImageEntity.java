package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A ImageEntity.
 */
@Entity
@Table(name = "IMAGEENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImageEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Lob
    @Column(name = "image_field")
    private byte[] imageField;

    @NotNull
    @Size(min = 100, max = 5000000)        
    @Lob
    @Column(name = "validated_image_field", nullable = false)
    private byte[] validatedImageField;
    
    @Lob
    @Column(name = "blob_field")
    private byte[] blobField;

    @NotNull
    @Size(min = 0, max = 5000000)        
    @Lob
    @Column(name = "validated_blob_field", nullable = false)
    private byte[] validatedBlobField;
    
    @Column(name = "string_field")
    private String stringField;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImageField() {
        return imageField;
    }

    public void setImageField(byte[] imageField) {
        this.imageField = imageField;
    }

    public byte[] getValidatedImageField() {
        return validatedImageField;
    }

    public void setValidatedImageField(byte[] validatedImageField) {
        this.validatedImageField = validatedImageField;
    }

    public byte[] getBlobField() {
        return blobField;
    }

    public void setBlobField(byte[] blobField) {
        this.blobField = blobField;
    }

    public byte[] getValidatedBlobField() {
        return validatedBlobField;
    }

    public void setValidatedBlobField(byte[] validatedBlobField) {
        this.validatedBlobField = validatedBlobField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ImageEntity imageEntity = (ImageEntity) o;

        if ( ! Objects.equals(id, imageEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ImageEntity{" +
                "id=" + id +
                ", imageField='" + imageField + "'" +
                ", validatedImageField='" + validatedImageField + "'" +
                ", blobField='" + blobField + "'" +
                ", validatedBlobField='" + validatedBlobField + "'" +
                ", stringField='" + stringField + "'" +
                '}';
    }
}
