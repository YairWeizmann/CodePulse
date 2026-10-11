package com.codepulse.codepulseproject.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class GithubService
{
    // Gets our private GitHub token from application.properties.
    @Value("${github.token}")
    private String githubToken;

    // Used to send HTTP requests to GitHub.
    private final HttpClient client = HttpClient.newHttpClient();

    // Used to read JSON responses.
    private final ObjectMapper objectMapper = new ObjectMapper();


    public String getPullRequestPatch(
            String owner,
            String repo,
            int prNumber) throws Exception {

        // Build the GitHub API URL for this Pull Request.
        String url =
                "https://api.github.com/repos/"
                        + owner + "/"
                        + repo + "/pulls/"
                        + prNumber + "/files";

        // Create a GET request to GitHub.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))

                // Proves to GitHub that CodePulse has permission.
                .header("Authorization", "Bearer " + githubToken)

                // Ask GitHub to return JSON.
                .header("Accept", "application/vnd.github+json")

                .GET()
                .build();

        // Send the request and receive GitHub's response.
        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        // Convert GitHub's JSON response into a JsonNode.
        JsonNode files =
                objectMapper.readTree(response.body());

        StringBuilder patches = new StringBuilder();

        // GitHub returns an array because a PR can change many files.
        for (JsonNode file : files) {

            String filename =
                    file.get("filename").asString();

            JsonNode patchNode =
                    file.get("patch");

            // Some files may not contain a patch.
            if (patchNode != null) {

                String patch =
                        patchNode.asString();

                patches.append("FILE: ")
                        .append(filename)
                        .append("\n");

                patches.append(patch)
                        .append("\n\n");
            }
        }

        return patches.toString();
    }
}
