package me.gokulnair.instagrat.service;

import lombok.RequiredArgsConstructor;
import me.gokulnair.instagrat.controller.dto.CommentDto;
import me.gokulnair.instagrat.entity.Comment;
import me.gokulnair.instagrat.repo.CommentRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;

    public Mono<Comment> createComment(CommentDto commentDto, Long userId, Long postId) {

        Comment comment =Comment.builder()
                .comment(commentDto.comment())
                .createdAt(LocalDateTime.now())
                .build();

        return commentRepository.save(comment)
                .flatMap(savedComment->{
                    savedComment.setCommentId(savedComment.getId());
                    return commentRepository.save(savedComment);
                })
                .doOnNext(comment1 -> System.out.println("The comment saved again with id"+comment1.getCommentId()))
                .flatMap(comment1 ->
                    commentRepository.createUserCommentRelation(comment1.getCommentId(),userId)
                                    .then(commentRepository.createPostCommentRelation(postId,comment1.getCommentId()))
                            .thenReturn(comment1)


                );

    }
}
