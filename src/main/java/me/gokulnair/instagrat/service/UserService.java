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
        return userRepository.save(user);
    }

    public Flux<AppUser> getAllUsers() {
        return userRepository.findAll();
    }
}