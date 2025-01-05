package gdg.week2.dto.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;

}
