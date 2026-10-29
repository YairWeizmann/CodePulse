package com.codepulse.codepulseproject.service;
import com.codepulse.codepulseproject.model.AiReviewEntity;
import com.codepulse.codepulseproject.model.PullRequestEntity;
import com.codepulse.codepulseproject.model.RepositoryEntity;
import com.codepulse.codepulseproject.service.Repositories.AiReviewSaveService;
import com.codepulse.codepulseproject.service.Repositories.PullRequestService;
import com.codepulse.codepulseproject.service.Repositories.RepositoryService;
import com.codepulse.codepulseproject.service.Reviews.ReviewService;
import org.springframework.stereotype.Service;
import tools.jackson.databind.JsonNode;

@Service
public class WebHookService
{
    private final RepositoryService repositoryService;
    private final PullRequestService pullRequestService;
    private final ReviewService reviewService;
    private final AiReviewSaveService aiReviewSaveService;

    public WebHookService(RepositoryService repositoryService, PullRequestService pullRequestService, ReviewService reviewService, AiReviewSaveService aiReviewSaveService)
    {
        this.repositoryService = repositoryService;
        this.pullRequestService = pullRequestService;
        this.reviewService = reviewService;
        this.aiReviewSaveService = aiReviewSaveService;
    }


    // Main workflow: coordinates the smaller methods.
    public void processPullRequest(JsonNode json) throws Exception
    {
        JsonNode repoJson = json.get("repository");

        String repository = repoJson.get("full_name").asString();
        String[] repoParts = repository.split("/");
        String owner = repoParts[0];
        String repo = repoParts[1];

        RepositoryEntity savedRepo = saveRepository(repoJson, owner, repo);
        PullRequestEntity savedPr = savePullRequest(json, savedRepo);
        String review = generateAndSaveReview(owner, repo, savedPr);

        printSummary(json, repository, savedPr, review);
    }


    // Extract repository information and save it.
    private RepositoryEntity saveRepository(JsonNode repoJson, String owner, String repo)
    {
        Long githubId = repoJson.get("id").asLong();

        RepositoryEntity savedRepo = repositoryService.saveRepository(githubId, owner, repo);

        System.out.println("Saved repository ID: " + savedRepo.getId());

        return savedRepo;
    }


    // Extract PR information and save it with its repository reference.
    private PullRequestEntity savePullRequest(JsonNode json, RepositoryEntity savedRepo)
    {
        JsonNode prJson = json.get("pull_request");

        int prNumber = json.get("number").asInt();
        String prTitle = prJson.get("title").asString();
        String state = prJson.get("state").asString();
        String latestCommitSha = prJson.get("head").get("sha").asString();

        PullRequestEntity savedPr = pullRequestService.savePullRequest(savedRepo, prNumber, prTitle, state, latestCommitSha);

        System.out.println("Saved PR database ID: " + savedPr.getId());

        return savedPr;
    }


    // Generate feedback and save it for the reviewed commit.
    private String generateAndSaveReview(String owner, String repo, PullRequestEntity savedPr) throws Exception
    {
        String review = reviewService.reviewPullRequest(owner, repo, savedPr.getPrNumber());

        AiReviewEntity savedReview = aiReviewSaveService.saveReview(savedPr, savedPr.getLatestCommitSha(), review);

        System.out.println("Saved AI review ID: " + savedReview.getId());

        return review;
    }


    // Display the result in the console.
    private void printSummary(JsonNode json, String repository, PullRequestEntity savedPr, String review)
    {
        System.out.println("Action: " + json.get("action").asString());
        System.out.println("PR #" + savedPr.getPrNumber());
        System.out.println("Repository: " + repository);
        System.out.println("AI Review:");
        System.out.println(review);
    }
}
