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


    // 1. requestParam 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    public List<ArticleResponseDto> getArticlesByAuthor(String author) {
        List<ArticleEntity> articleEntityList = articleRepository.findByAuthor(author);
        List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();
        for (ArticleEntity articleEntity : articleEntityList) {
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

    // 2. pathVariable 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    public List<ArticleResponseDto> getArticlesByAuthor(String author) {
        List<ArticleEntity> articleEntityList = articleRepository.findByAuthor(author);
        List<ArticleResponseDto> articleResponseDtoList = new ArrayList<>();
        for (ArticleEntity articleEntity : articleEntityList) {
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


   //3. 데이터베이스 내의 모든 게시글을 삭제하는 api (DeleteMapping)
    public String deleteAllArticles() {
        articleRepository.deleteAll();
        return "모든 게시글이 데이터베이스에서 삭제되었습니다";
    }




}
