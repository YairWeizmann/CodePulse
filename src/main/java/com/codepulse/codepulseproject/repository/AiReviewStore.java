package com.codepulse.codepulseproject.repository;

import com.codepulse.codepulseproject.model.AiReviewEntity;
import com.codepulse.codepulseproject.model.PullRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AiReviewStore extends JpaRepository<AiReviewEntity, Long>
{
    AiReviewEntity findByPullRequestAndCommitSha(PullRequestEntity pullRequest, String commitSha);
}