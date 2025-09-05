package me.gokulnair.instagrat;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.mistralai.MistralAiChatModel;
import org.springframework.ai.mistralai.api.MistralAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InstagratApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagratApplication.class, args);
	}

	@Bean
	public ChatClient chatClient(@Value("${spring.ai.mistralai.apiKey}") String apiKey) {
		MistralAiChatModel mistralClient = new MistralAiChatModel(new MistralAiApi(apiKey));

		return ChatClient.builder(mistralClient).build();



	}


}
