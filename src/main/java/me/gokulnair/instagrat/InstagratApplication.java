package me.gokulnair.instagrat;

import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InstagratApplication {

	public static void main(String[] args) {
		SpringApplication.run(InstagratApplication.class, args);
	}

	@Bean
	InMemoryChatMemory chatMemory(){
		return new InMemoryChatMemory();
	}


}
