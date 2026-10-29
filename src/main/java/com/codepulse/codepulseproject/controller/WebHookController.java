package com.codepulse.codepulseproject.controller;// Lets us work with JSON as a tree.
import com.codepulse.codepulseproject.service.WebHookService;
import org.springframework.web.bind.annotation.RequestMapping; // Uses the @RequestMapping annotation
import tools.jackson.databind.JsonNode;// Converts JSON text into JsonNode objects.
import tools.jackson.databind.ObjectMapper; // Lets us create POST endpoints.
import org.springframework.web.bind.annotation.PostMapping; // Gets the data sent inside the HTTP request body.
import org.springframework.web.bind.annotation.RequestBody; // Marks this class as a REST/API controller.
import org.springframework.web.bind.annotation.RestController;



@RestController //Tells Spring boot this class handles Http/Api requests and returns a response
@RequestMapping("/github")
public class WebHookController
{
    private final WebHookService webHookService;

    // Spring gives us the GithubService automatically.
    public WebHookController(WebHookService webHookService)
    {
      this.webHookService = webHookService;
    }


   @PostMapping("/webhook") // When a POST request is sent to /github/webhook, Spring runs the method below.
    public String receiveWebHook(@RequestBody String body) throws Exception
   {
       ObjectMapper objectMapper = new ObjectMapper();
       JsonNode json = objectMapper.readTree(body);

       String action = json.get("action").asString();

       // Process only opened and reopened PRs.
       if (!"opened".equals(action) && !"reopened".equals(action))
       {
           return "Event ignored";
       }

       // Let the service coordinate the workflow.
       webHookService.processPullRequest(json);

       return "Webhook received";
   }
}
