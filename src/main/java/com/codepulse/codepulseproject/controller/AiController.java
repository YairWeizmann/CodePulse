package com.codepulse.codepulseproject.controller;

import com.codepulse.codepulseproject.service.Reviews.AiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class AiController
{
    AiService aiService;

    public AiController(AiService aiService)
    {
        this.aiService = aiService;
    }


    @GetMapping("/ai")
    public String testAi()
    {
        return aiService.testAi();
    }
}
