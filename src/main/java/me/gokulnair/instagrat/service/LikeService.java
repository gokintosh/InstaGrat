package me.gokulnair.instagrat.service;

import lombok.RequiredArgsConstructor;
import me.gokulnair.instagrat.repo.LikeRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;


    public Mono<Void> likePost(Long userId, Long postId) {
        return likeRepository.likePost(userId,postId);
    }
}
