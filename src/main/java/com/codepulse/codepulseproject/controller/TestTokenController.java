package com.codepulse.codepulseproject.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/check")
public class TestTokenController
{
    // Gets the value of "github.token" from Spring configuration.
    @Value("${github.token}")
    private String githubToken;

    //Gets the value for openai.api.key from Spring configuration.
    @Value("${openai.api.key}")
    private String openAiApiToken;

    // Runs when someone sends GET /check-token.
    @GetMapping("/github-token")
    public String checkGitToken() {

        // Checks if the token is missing or empty.
        if (githubToken == null || githubToken.isBlank()) {
            return "GitHub token NOT loaded";
        }

        // Token exists, but we do NOT print the secret itself.
        return "GitHub token loaded successfully";
    }


    @GetMapping("/openapi-token")
    public String checkOpenAiToken() {
        if(openAiApiToken == null || openAiApiToken.isBlank())
            return "Open Ai Api token not loaded";

        return "Open ai Api token loaded Successfully";
    }
}
