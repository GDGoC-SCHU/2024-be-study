package gdg.week2.repository;

import gdg.week2.domain.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByTitle(String title);

    List<ArticleEntity> findByAuthor(String author);
}
//1
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    List<ArticleEntity> findByAuthor(String author);
}

//3
public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    void deleteAll();
}
