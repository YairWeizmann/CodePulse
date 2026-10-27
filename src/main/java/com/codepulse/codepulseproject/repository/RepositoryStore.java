package com.codepulse.codepulseproject.repository;

import com.codepulse.codepulseproject.model.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryStore extends JpaRepository<RepositoryEntity, Long>
{
    RepositoryEntity findByGithubId(Long githubId);
}