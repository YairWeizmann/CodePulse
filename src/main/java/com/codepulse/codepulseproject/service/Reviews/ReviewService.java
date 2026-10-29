package com.codepulse.codepulseproject.service.Reviews;

import com.codepulse.codepulseproject.service.GithubService;
import org.springframework.stereotype.Service;

@Service
public class ReviewService
{
    private final AiService aiService;
    private final GithubService githubService;

    public ReviewService(AiService aiService , GithubService githubService)
    {
        this.aiService = aiService;
        this.githubService = githubService;
    }


    public String reviewPullRequest(String owner , String repo , int prNumber) throws Exception
    {
         String patch = githubService.getPullRequestPatch(owner,repo,prNumber);

         return aiService.reviewPr(patch);
    }



}
