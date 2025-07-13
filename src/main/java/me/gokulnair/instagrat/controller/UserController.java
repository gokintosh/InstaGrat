package me.gokulnair.instagrat.controller;

import lombok.RequiredArgsConstructor;
import me.gokulnair.instagrat.entity.AppUser;
import me.gokulnair.instagrat.service.UserService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public Mono<AppUser> createUser(@RequestParam String name) {
        return userService.createUser(name);
    }

    @GetMapping
    public Flux<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/{followerId}/follow/{followeeId}")
    public Mono<AppUser> follow(@PathVariable Long followerId, @PathVariable Long followeeId) {
        return userService.followUser(followerId, followeeId);
    }
}