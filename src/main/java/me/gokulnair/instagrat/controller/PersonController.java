package me.gokulnair.instagrat.controller;

import me.gokulnair.instagrat.model.Person;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final ChatClient chatClient;

    public PersonController(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder.defaultAdvisors(new PromptChatMemoryAdvisor(chatMemory),
                        new SimpleLoggerAdvisor())
        .build();
    }

    @GetMapping
    public Mono<List<Person>> findAll(){
        PromptTemplate pt=new PromptTemplate(
                """
                        Return a current list of 10 famous persons if exists or generate a new list with random values.
                                        Each object should contain an auto-incremented id field.
                                        Do not include any explanations or additional text.
                        """
        );
        return Mono.fromCallable(() ->
                this.chatClient.prompt(pt.create())
                        .call()
                        .entity(new ParameterizedTypeReference<List<Person>>() {})
        )
                .timeout(Duration.ofSeconds(180))
                .subscribeOn(Schedulers.boundedElastic()); // Move blocking work to a safe thread
    }

    @GetMapping("/{id}")
    public Mono<Person> findById(@PathVariable String id) {
        return Mono.fromCallable(() -> {
            PromptTemplate pt = new PromptTemplate("""
                Find and return the object with id {id} in a current list of persons.
                """);
            Prompt prompt = pt.create(Map.of("id", id));
            return this.chatClient.prompt(prompt)
                    .call()
                    .entity(Person.class);
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
