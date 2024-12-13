package gdg.gideon.week2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gdg.gideon.week2.domain.ArticleEntity;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByTitle(String title);

    Optional<List<ArticleEntity>> findByAuthor(String author);
}
