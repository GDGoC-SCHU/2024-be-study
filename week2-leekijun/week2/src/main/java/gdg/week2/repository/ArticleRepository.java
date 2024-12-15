package gdg.week2.repository;

import gdg.week2.domain.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<ArticleEntity, Long> {
    Optional<ArticleEntity> findByTitle(String title);

    // 1. requestParam 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    // 2. pathVariable 방식으로 author를 검색하여 article을 찾는 api (GetMapping) 같이 사용
    List<ArticleEntity> findByAuthor(String author);

}
