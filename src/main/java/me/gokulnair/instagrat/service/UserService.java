package me.gokulnair.instagrat.service;

import lombok.RequiredArgsConstructor;
import me.gokulnair.instagrat.entity.AppUser;
import me.gokulnair.instagrat.repo.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono<AppUser> createUser(String name) {
        AppUser user = new AppUser();
        user.setName(name);
        return userRepository.save(user)
                .flatMap(savedUser->{
                    savedUser.setUserId(savedUser.getId());
                    return userRepository.save(savedUser);
                }
                )
                ;
    }

    public Flux<AppUser> getAllUsers() {
        return userRepository.findAll();
    }


    //implement follow functionality

    public Mono<AppUser> followUser(Long followerId, Long followeeId) {
        return userRepository.findById(followerId)
                .zipWith(userRepository.findById(followeeId))
                .flatMap(tuple -> {
                    AppUser follower = tuple.getT1();
                    AppUser followee = tuple.getT2();
                    follower.getFollowing().add(followee);
                    return userRepository.save(follower);
                });
    }
}