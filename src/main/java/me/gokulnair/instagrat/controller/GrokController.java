package me.gokulnair.instagrat.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.gokulnair.instagrat.controller.dto.UserPostLists;
import me.gokulnair.instagrat.entity.Post;
import me.gokulnair.instagrat.repo.PostRepository;
import me.gokulnair.instagrat.repo.UserRepository;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.ai.vectorstore.VectorStore;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brok")
@RequiredArgsConstructor
public class GrokController {

    private final VectorStore vectorStore;

    private final ObjectMapper mapper;

    private final PostRepository postRepository;

    private final ChatClient chatClient;

    private final VectorStore store;

    // we need to fetch the posts from database


    @GetMapping("/getposts/{userId}")
    Mono<Boolean> getAllPosts(@PathVariable Long userId){
        return postRepository.getAllPostsForUser(userId)
                .collectList().map(post-> {
            try {
                return Document.builder().id(userId.toString()).text(mapper.writeValueAsString(new UserPostLists(userId,post))).build();

            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).flatMap(docs->Mono.fromCallable(()->{
            vectorStore.add(List.of(docs));
            return true;
                }).subscribeOn(Schedulers.boundedElastic())

                );


    }

    @RequestMapping("/askbrok/{userId}")
    Mono<String> askBrok(@PathVariable String userId, @RequestParam String prompt){


        PromptTemplate pt=new PromptTemplate("""
                {query}.
                Only consider posts from user {userId}.
                Summarise their latest posts clearly.
                """);

        Prompt p=pt.create(
                Map.of("query",prompt,
                        "userId",userId
                )
        );

//        SearchRequest searchRequest = SearchRequest.builder().query(prompt).filterExpression(Map.of("userId", userId)).build();

        return Mono.fromCallable(()->
                this.chatClient.prompt(p)
                        .advisors(new QuestionAnswerAdvisor(
                                store
                        )).call()
                        .content()
                ).subscribeOn(Schedulers.boundedElastic());
    }


    






}
