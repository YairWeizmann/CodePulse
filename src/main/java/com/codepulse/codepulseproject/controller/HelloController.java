package com.codepulse.codepulseproject.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // Tells Spring boot this class handles HTTP/API requests and returns response
public class HelloController
{
  @GetMapping("/hello")
    public String sayHello()
  {
      return "Hello from Api";
  }
}
