package com.codepulse.codepulseproject.model;
import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ai_reviews")
public class AiReviewEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "pull_request_id",nullable = false)
    private PullRequestEntity pullRequest;

    @Column(name = "commit_sha" , nullable = false)
    private String commitSha;

    @Column(name = "feedback" , nullable = false)
    private String feedBack;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;


    protected AiReviewEntity(){}

    public AiReviewEntity(PullRequestEntity pullRequest , String commitSha , String feedBack)
    {
        this.pullRequest = pullRequest;
        this.commitSha = commitSha;
        this.feedBack = feedBack;
        createdAt = Instant.now();
    }


    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public PullRequestEntity getPullRequest()
    {
        return pullRequest;
    }

    public void setPullRequest(PullRequestEntity pullRequest)
    {
        this.pullRequest = pullRequest;
    }

    public String getCommitSha()
    {
        return commitSha;
    }

    public void setCommitSha(String commitSha)
    {
        this.commitSha = commitSha;
    }

    public String getFeedBack()
    {
        return feedBack;
    }

    public void setFeedBack(String feedBack)
    {
        this.feedBack = feedBack;
    }

    public Instant getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt)
    {
        this.createdAt = createdAt;
    }
}
