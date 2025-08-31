package me.gokulnair.instagrat.service;

import lombok.RequiredArgsConstructor;
import me.gokulnair.instagrat.controller.dto.PostDto;
import me.gokulnair.instagrat.entity.Post;
import me.gokulnair.instagrat.repo.PostRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;


    public Mono<Post> createPost(PostDto postDto, Long userId) {

        Post post=Post.builder()
                .title(postDto.title())
                .content(postDto.content())
                .createdAt(LocalDateTime.now())
                .build();


        return postRepository.save(post)
                .flatMap(savedPost->{
                    savedPost.setPostId(savedPost.getId());
                    return postRepository.save(savedPost);
                })
                .doOnNext(saved -> System.out.println("✅ Post saved: " + saved.getId()))
                .flatMap(savedPost ->
                        postRepository.createUserPostRelation(savedPost.getPostId(), userId)
                                .then(Mono.fromRunnable(() -> System.out.println("✅ Relation created")))
                                .thenReturn(savedPost)
                );

    }

    public Flux<Post> getAllPosts(){
        return postRepository.findAll();
    }

    //when I send my userId , then fetch the posts t

}
