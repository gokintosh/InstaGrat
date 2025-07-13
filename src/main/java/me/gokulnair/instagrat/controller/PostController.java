package me.gokulnair.instagrat.controller;


import lombok.RequiredArgsConstructor;
import me.gokulnair.instagrat.controller.dto.PostDto;
import me.gokulnair.instagrat.entity.Post;
import me.gokulnair.instagrat.service.PostService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/{userId}")
    Mono<Post> createPost(@PathVariable Long userId, @RequestBody PostDto postDto){
        return postService.createPost(postDto,userId);
    }
}
