package me.gokulnair.instagrat.controller;


import lombok.RequiredArgsConstructor;
import me.gokulnair.instagrat.service.LikeService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class PostLikeController {


    private final LikeService likeService;

    @PostMapping("/{userId}/like/{postId}")
    Mono<Void> likePost(@PathVariable Long userId,@PathVariable Long postId){
        return likeService.likePost(userId,postId);
    }
}
