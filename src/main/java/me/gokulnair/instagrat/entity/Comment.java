package me.gokulnair.instagrat.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.time.LocalDateTime;

@Node
@Data
@Builder
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    @Property("commentId")
    private Long commentId;

    private String comment;

    private LocalDateTime createdAt;
}
