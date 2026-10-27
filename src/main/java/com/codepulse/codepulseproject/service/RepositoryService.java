package com.codepulse.codepulseproject.service;
import com.codepulse.codepulseproject.model.RepositoryEntity;
import com.codepulse.codepulseproject.repository.RepositoryStore;
import org.springframework.stereotype.Service;


@Service
public class RepositoryService
{
    private final RepositoryStore repositoryStore;

    public RepositoryService(RepositoryStore repositoryStore)
    {
        this.repositoryStore = repositoryStore;
    }


    // Receives repository information extracted from the GitHub webhook.
    // Returns the existing repository or the newly saved one.
    public RepositoryEntity saveRepository(Long githubId , String owner , String name)
    {
        // Search PostgreSQL for a repository with this GitHub ID.
        RepositoryEntity existing = repositoryStore.findByGithubId(githubId);

        if(existing != null)
            return existing;

        RepositoryEntity newRepo = new RepositoryEntity(githubId,owner,name);

        // Save the object as a database row.
        return repositoryStore.save(newRepo);
    }

}
