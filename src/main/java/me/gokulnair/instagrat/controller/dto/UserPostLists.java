package me.gokulnair.instagrat.controller.dto;

import me.gokulnair.instagrat.entity.Post;

import java.util.List;

public record UserPostLists(Long UserId, List<Post>posts) {

}
