package com.codepulse.codepulseproject.model;
import jakarta.persistence.*;

@Entity //tells Hibernate this class is special i want to map it to database table
@Table(name = "pull_requests") // to which table
public class PullRequestEntity
{
    @Id //tells hibernate which is the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "repository_id", nullable = false)
    private RepositoryEntity repository;

    @Column(name = "pr_number",nullable = false)
    private int prNumber;

    @Column(name = "pr_title",nullable = false)
    private String prTitle;

    @Column(name = "state",nullable = false)
    private String state;

    @Column(name = "latest_commit_sha",nullable = false)
    private String latestCommitSha;


    protected PullRequestEntity(){}

    public PullRequestEntity(RepositoryEntity repository , int prNumber , String prTitle , String state , String latestCommitSha){
        this.repository = repository;
        this.prNumber = prNumber;
        this.state = state;
        this.prTitle = prTitle;
        this.latestCommitSha = latestCommitSha;
    }

    public void updateDetails(String prTitle, String state, String latestCommitSha)
    {
        this.prTitle = prTitle;
        this.state = state;
        this.latestCommitSha = latestCommitSha;
    }

    public long getId() {
        return id;
    }

    public RepositoryEntity getRepository() {
        return repository;
    }

    public int getPrNumber() {
        return prNumber;
    }

    public String getPrTitle() {
        return prTitle;
    }

    public String getState() {
        return state;
    }

    public String getLatestCommitSha() {
        return latestCommitSha;
    }

}
