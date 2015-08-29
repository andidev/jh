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
 * A CommentedEntity.
 */
@Entity
@Table(name = "COMMENTEDENTITY")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CommentedEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "commented_field")
    private String commentedField;

    @OneToMany(mappedBy = "commentedEntity")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommentedRelationship> commentedRelationships = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentedField() {
        return commentedField;
    }

    public void setCommentedField(String commentedField) {
        this.commentedField = commentedField;
    }

    public Set<CommentedRelationship> getCommentedRelationships() {
        return commentedRelationships;
    }

    public void setCommentedRelationships(Set<CommentedRelationship> commentedRelationships) {
        this.commentedRelationships = commentedRelationships;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CommentedEntity commentedEntity = (CommentedEntity) o;

        if ( ! Objects.equals(id, commentedEntity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CommentedEntity{" +
                "id=" + id +
                ", commentedField='" + commentedField + "'" +
                '}';
    }
}
