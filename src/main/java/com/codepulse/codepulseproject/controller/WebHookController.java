package com.codepulse.codepulseproject.controller;
// Lets us work with JSON as a tree.
import com.codepulse.codepulseproject.service.GithubService;
import org.springframework.web.bind.annotation.RequestMapping;
import tools.jackson.databind.JsonNode;
// Converts JSON text into JsonNode objects.
import tools.jackson.databind.ObjectMapper;
// Lets us create POST endpoints.
import org.springframework.web.bind.annotation.PostMapping;
// Gets the data sent inside the HTTP request body.
import org.springframework.web.bind.annotation.RequestBody;
// Marks this class as a REST/API controller.
import org.springframework.web.bind.annotation.RestController;

@RestController //Tells Spring boot this class handles Http/Api requests and returns a response
@RequestMapping("/github")
public class WebHookController
{
    // Reference to our GithubService.
    private final GithubService githubService;

    // Spring gives us the GithubService automatically.
    public WebHookController(GithubService githubService) {
        this.githubService = githubService;
    }


   @PostMapping("/webhook") // When a POST request is sent to /github/webhook, Spring runs the method below.
    public String receiveWebHook(@RequestBody String body) throws Exception {
       ObjectMapper objectMapper = new ObjectMapper();

       JsonNode json = objectMapper.readTree(body);

       String action = json.get("action").asString();
       int prNumber = json.get("number").asInt();

       String repository =
               json.get("repository")
                       .get("full_name").asString();

       String author =
               json.get("sender")
                       .get("login").asString();

       String sourceBranch =
               json.get("pull_request")
                       .get("head")
                       .get("ref").asString();

       String targetBranch =
               json.get("pull_request")
                       .get("base")
                       .get("ref").asString();


       String[] repoParts = repository.split("/");

       String owner = repoParts[0];
       String repo = repoParts[1];

       // Ask GithubService to get the changed code.
       String patch =
               githubService.getPullRequestPatch(
                       owner,
                       repo,
                       prNumber
               );


       System.out.println("Action: " + action);
       System.out.println("PR #" + prNumber);
       System.out.println("Repository: " + repository);

       System.out.println("PATCH:");
       System.out.println(patch);


       return "Webhook received";
   }
}
