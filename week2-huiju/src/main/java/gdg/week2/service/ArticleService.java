package gdg.week2.service;

import gdg.week2.dto.ArticleCreateDto;
import gdg.week2.dto.ArticleResponseDto;
import gdg.week2.dto.ArticleUpdateDto;
import gdg.week2.domain.ArticleEntity;
import gdg.week2.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     *  모든 게시글 불러오기
     */
    public List<ArticleResponseDto> getAllArticle() {
        List<ArticleEntity> articleEntityList = articleRepository.findAll();
        List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();

        for(ArticleEntity articleEntity : articleEntityList) {
            ArticleResponseDto articleResponseDto = new ArticleResponseDto(
                    articleEntity.getId(),
                    articleEntity.getTitle(),
                    articleEntity.getContent(),
                    articleEntity.getAuthor(),
                    articleEntity.getCreatedAt()
                    );
            articleResponseDtoList.add(articleResponseDto);
        }

        return articleResponseDtoList;
    }

    /**
     * title 로 게시글 찾기
     */
    public ArticleResponseDto getArticle(String title) {
        ArticleEntity articleEntity = articleRepository.findByTitle(title).get();

        ArticleResponseDto articleResponseDto = new ArticleResponseDto(
                articleEntity.getId(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getAuthor(),
                articleEntity.getCreatedAt()
        );
        return articleResponseDto;
    }

    /**
     * 게시글 생성하기
     */
    public String create(ArticleCreateDto articleCreateDto) {
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setTitle(articleCreateDto.getTitle());
        articleEntity.setContent(articleCreateDto.getContent());
        articleEntity.setAuthor(articleCreateDto.getAuthor());
        articleEntity.setCreatedAt(LocalDateTime.now());

        articleRepository.save(articleEntity);

        return "success";
    }

    public ArticleResponseDto update(Long id, ArticleUpdateDto articleUpdateDto) {
        ArticleEntity articleEntity = articleRepository.findById(id).get();
        articleEntity.setContent(articleUpdateDto.getContent());
        articleRepository.save(articleEntity);

        return new ArticleResponseDto(
                articleEntity.getId(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getAuthor(),
                articleEntity.getCreatedAt()
        );
    }

    public String delete(Long id) {
        articleRepository.deleteById(id);
        return "success";
    }


    public ArticleResponseDto getArticle(Long id) {
        ArticleEntity articleEntity = articleRepository.findById(id).get();

        ArticleResponseDto articleResponseDto = new ArticleResponseDto(
                articleEntity.getId(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getAuthor(),
                articleEntity.getCreatedAt()
        );

        return articleResponseDto;
    }

    /**
     * 1. author로 게시글 찾기 (requestParam 및 pathVariable 대응)
     */
    public ArticleResponseDto getArticleByAuthor(String author) {
        ArticleEntity articleEntity = articleRepository.findByAuthor(author).orElseThrow(
                () -> new IllegalArgumentException("No article found for author: " + author)
        );

        return new ArticleResponseDto(
                articleEntity.getId(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getAuthor(),
                articleEntity.getCreatedAt()
        );
    }

    /**
     * 2. 데이터베이스 내의 모든 게시글 삭제하기
     *
     * @return
     */
    public String deleteAllArticles() {
        articleRepository.deleteAll();
        return null;
    }

    /**
     * 3. 특정 Id의 게시글 삭제하기
     * - pathVariable, requestParam, requestBody에 대응
     */
    public String deleteById(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new IllegalArgumentException("No article found with id: " + id);
        }
        articleRepository.deleteById(id);
        return "Article with id " + id + " deleted successfully";
    }
}
