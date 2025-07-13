package me.gokulnair.instagrat.repo;

import me.gokulnair.instagrat.entity.Post;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;


public interface LikeRepository extends ReactiveNeo4jRepository<Post,Long>{
    @Query("""
        MATCH (user:AppUser {userId: $userId})
        MATCH (post:Post {postId: $postId})
        MERGE (user)-[like:LIKES]->(post)
        ON CREATE SET 
            like.createdAt = localdatetime(),
            like.updatedAt = localdatetime()
        ON MATCH SET
            like.updatedAt = localdatetime()
        """)
    Mono<Void> likePost(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query("""
        MATCH (user:AppUser {userId: $userId})-[like:LIKES]->(post:Post {postId: $postId})
        DELETE like
        """)
    Mono<Void> unlikePost(@Param("userId") Long userId, @Param("postId") Long postId);

    @Query("""
        MATCH (post:Post {postId: $postId})<-[like:LIKES]-()
        RETURN count(like)
        """)
    Mono<Long> countLikes(@Param("postId") Long postId);

    @Query("""
        MATCH (user:AppUser {userId: $userId})-[like:LIKES]->(post:Post {postId: $postId})
        RETURN count(like) > 0
        """)
    Mono<Boolean> hasLiked(@Param("userId") Long userId, @Param("postId") Long postId);
}
