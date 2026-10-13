package com.codepulse.codepulseproject.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service; //Grants us the permission to use @Service annotation



@Service
public class AiService
{
   private final ChatClient chatClient;

   public AiService(ChatClient.Builder builder)
   {
       this.chatClient = builder.build();
   }

   public String testAi() {
       return chatClient.prompt().user("Say Hello in one short sentence.").call().content();
   }









}
