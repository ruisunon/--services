package by.andd3dfx.templateapp.persistence.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Builder
@RedisHash("Article")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Article {

    private Long id;
    private String title;
    private String summary;
    private String text;
    private LocalDateTime timestamp;
    private String author;

    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
