package gdg.gideon.week2.domain;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "article")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
}