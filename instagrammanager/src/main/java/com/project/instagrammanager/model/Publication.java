package com.project.instagrammanager.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Publication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;
    private String url;
    private String description;
    private Date date_to_post;

    public Publication(String url, String description, Date date_to_post) {
        this.url = url;
        this.description = description;
        this.date_to_post = date_to_post;
    }

    public Date getDate_to_post() {
        return date_to_post;
    }

    public void setDate_to_post(Date date_to_post) {
        this.date_to_post = date_to_post;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public Publication() {

    }

    @Override
    public String toString() {
        return "Publication{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", date_to_post=" + date_to_post +
                '}';
    }
}
