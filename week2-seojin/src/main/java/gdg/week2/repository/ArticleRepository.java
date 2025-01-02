package gdg.week2.repository;

import gdg.week2.domain.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByTitle(String title);
    Optional<ArticleEntity> findByAuthor(String author);
}
