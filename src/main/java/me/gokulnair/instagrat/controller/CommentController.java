package me.gokulnair.instagrat.controller;


import lombok.RequiredArgsConstructor;
import me.gokulnair.instagrat.controller.dto.CommentDto;
import me.gokulnair.instagrat.entity.Comment;
import me.gokulnair.instagrat.service.CommentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("{userId}/comment/{postId}")
    Mono<Comment> createComment(@RequestBody CommentDto commentDto, @PathVariable Long userId, @PathVariable Long postId){
        return commentService.createComment(commentDto,userId,postId);
    }


}
