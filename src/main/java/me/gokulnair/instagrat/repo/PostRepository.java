package me.gokulnair.instagrat.repo;

import me.gokulnair.instagrat.entity.Post;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

public interface PostRepository extends ReactiveNeo4jRepository<Post,Long> {

    @Query("""
            MATCH (user:AppUser {userId: $userId})
            MATCH (post:Post {postId: $postId})
            MERGE (user)-[relationship:CREATED_POST]->(post)
            ON CREATE SET 
            relationship.createdAt = localdatetime(),
            relationship.updatedAt = localdatetime()
            ON MATCH SET
            relationship.updatedAt = localdatetime()
            """)
    Mono<Void> createUserPostRelation(@Param("postId") Long postId, @Param("userId") Long userId);

}


