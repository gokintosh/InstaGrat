package me.gokulnair.instagrat.repo;

import me.gokulnair.instagrat.entity.AppUser;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserRepository extends ReactiveNeo4jRepository<AppUser, Long> {
    Mono<AppUser> findByName(String name);
}