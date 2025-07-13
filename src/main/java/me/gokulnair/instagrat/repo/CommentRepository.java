package me.gokulnair.instagrat.repo;

import me.gokulnair.instagrat.entity.Comment;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

public interface CommentRepository extends ReactiveNeo4jRepository<Comment,Long> {


    @Query("""
    MATCH (user:AppUser {userId: $userId})
    MATCH (comment:Comment {commentId: $commentId})
    MERGE (user)-[relationship:CREATED_COMMENT]->(comment)
    ON CREATE SET 
        relationship.createdAt = localdatetime(),
        relationship.updatedAt = localdatetime()
    ON MATCH SET
        relationship.updatedAt = localdatetime()
    """)
    Mono<Void> createUserCommentRelation(@Param("commentId") Long commentId, @Param("userId") Long userId);


    @Query("""
    MATCH (post:Post {postId: $postId})
    MATCH (comment:Comment {commentId: $commentId})
    MERGE (post)-[relationship:HAS_COMMENT]->(comment)
    ON CREATE SET 
        relationship.createdAt = localdatetime(),
        relationship.updatedAt = localdatetime()
    ON MATCH SET
        relationship.updatedAt = localdatetime()
    """)
    Mono<Void> createPostCommentRelation(@Param("postId") Long postId, @Param("commentId") Long commentId);
}
