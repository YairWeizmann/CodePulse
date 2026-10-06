package com.codepulse.codepulseproject.controller;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController //Tells Spring boot this class handles Http/Api requests and returns a response
public class WebHookController
{
   @PostMapping("/github/webhook")
    public String receiveWebHook(@RequestBody String body)
   {
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

       System.out.println("Action: " + action);
       System.out.println("PR #" + prNumber);
       System.out.println("Repository: " + repository);
       System.out.println("Author: " + author);
       System.out.println("Branch: " + sourceBranch + " -> " + targetBranch);

       return "Webhook received";
   }
}
