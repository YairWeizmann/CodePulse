package com.codepulse.codepulseproject.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestTokenController
{
    // Gets the value of "github.token" from Spring configuration.
    @Value("${github.token}")
    private String githubToken;

    // Runs when someone sends GET /check-token.
    @GetMapping("/check-token")
    public String checkToken()
    {

        // Checks if the token is missing or empty.
        if (githubToken == null || githubToken.isBlank()) {
            return "GitHub token NOT loaded";
        }

        // Token exists, but we do NOT print the secret itself.
        return "GitHub token loaded successfully";
    }
}
