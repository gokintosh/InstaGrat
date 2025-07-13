package me.gokulnair.instagrat.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Node("Post")
@Data
@Builder
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @Property("postId")
    private Long postId;

    private String title;

    private String content;

    private LocalDateTime createdAt;

}
