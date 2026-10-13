package com.codepulse.codepulseproject.controller;


import com.codepulse.codepulseproject.service.PracticeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/practice")
public class PracticeController
{
    private final PracticeService practiceService;

    public  PracticeController(PracticeService practiceService){
        this.practiceService = practiceService;
    }


    @GetMapping("/hello")
    public String getHello()
    {
        return this.practiceService.helloPractice();
    }

}
