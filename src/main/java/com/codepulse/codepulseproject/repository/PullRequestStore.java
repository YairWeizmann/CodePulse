package com.codepulse.codepulseproject.repository;

import com.codepulse.codepulseproject.model.PullRequestEntity;
import com.codepulse.codepulseproject.model.RepositoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PullRequestStore extends JpaRepository<PullRequestEntity, Long>
{
    PullRequestEntity findByRepositoryAndPrNumber(RepositoryEntity repository, int prNumber);
}