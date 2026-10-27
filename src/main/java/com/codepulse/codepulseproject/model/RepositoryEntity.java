package com.codepulse.codepulseproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "repositories")
public class RepositoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "github_id", nullable = false, unique = true)
    private Long githubId;

    @Column(nullable = false, columnDefinition = "text")
    private String owner;

    @Column(nullable = false, columnDefinition = "text")
    private String name;

    // Required so Hibernate can create objects when loading rows.
    protected RepositoryEntity() {
    }

    public RepositoryEntity(Long githubId, String owner, String name) {
        this.githubId = githubId;
        this.owner = owner;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long getGithubId() {
        return githubId;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }
}