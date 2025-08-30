package me.gokulnair.instagrat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ai.vectorstore.VectorStore;

@RestController
@RequestMapping("/grok")
@RequiredArgsConstructor
public class GrokController {

    private final VectorStore vectorStore;

    // we need to fetch the posts from database






}
