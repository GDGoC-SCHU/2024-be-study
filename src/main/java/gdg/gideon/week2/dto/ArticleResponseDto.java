package gdg.gideon.week2.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
