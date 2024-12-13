package gdg.gideon.week2.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gdg.gideon.week2.domain.ArticleEntity;
import gdg.gideon.week2.dto.ArticleCreateDto;
import gdg.gideon.week2.dto.ArticleResponseDto;
import gdg.gideon.week2.dto.ArticleUpdateDto;
import gdg.gideon.week2.repository.ArticleRepository;

@Service
public class ArticleService {
    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<ArticleResponseDto> getAllArticle() {
        List<ArticleEntity> articleEntities = articleRepository.findAll();

        List<ArticleResponseDto> responseDtos = new ArrayList<>();

        for (ArticleEntity articleEntity : articleEntities) {
            ArticleResponseDto responseDto = new ArticleResponseDto(
                    articleEntity.getId(),
                    articleEntity.getTitle(),
                    articleEntity.getContent(),
                    articleEntity.getAuthor(),
                    articleEntity.getCreatedAt());
            responseDtos.add(responseDto);
        }

        return responseDtos;
    }

    public ArticleResponseDto getArticle(String title) {
        ArticleEntity articleEntity = articleRepository.findByTitle(title).get();

        ArticleResponseDto responseDto = new ArticleResponseDto(
                articleEntity.getId(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getAuthor(),
                articleEntity.getCreatedAt());

        return responseDto;
    }

    public String createArticle(ArticleCreateDto articleCreateDto) {
        ArticleEntity articleEntity = new ArticleEntity();

        articleEntity.setTitle(articleCreateDto.getTitle());
        articleEntity.setAuthor(articleCreateDto.getAuthor());
        articleEntity.setContent(articleCreateDto.getContent());
        articleEntity.setCreatedAt(LocalDateTime.now());

        articleRepository.save(articleEntity);
        return "Created new Article";
    }

    public String createManyArticle(List<ArticleCreateDto> articleCreateDtos) {
        for (ArticleCreateDto articleCreateDto : articleCreateDtos) {
            ArticleEntity articleEntity = new ArticleEntity();

            articleEntity.setTitle(articleCreateDto.getTitle());
            articleEntity.setAuthor(articleCreateDto.getAuthor());
            articleEntity.setContent(articleCreateDto.getContent());
            articleEntity.setCreatedAt(LocalDateTime.now());

            articleRepository.save(articleEntity);
        }
        return "Created many Articles";
    }

    public ArticleResponseDto updateArticle(Long id, ArticleUpdateDto articleUpdateDto) {
        ArticleEntity articleEntity = articleRepository.findById(id).get();

        articleEntity.setContent(articleUpdateDto.getContent());

        return new ArticleResponseDto(
                articleEntity.getId(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getAuthor(),
                articleEntity.getCreatedAt());
    }

    public String deleteArticle(Long id) {
        articleRepository.deleteById(id);
        return "Article Deleted";
    }

    public String deleteAllArticle() {
        articleRepository.deleteAll();
        return "All Article Deleted";
    }

    public ArticleResponseDto getArticle(Long id) {
        ArticleEntity articleEntity = articleRepository.findById(id).get();

        return new ArticleResponseDto(
                articleEntity.getId(),
                articleEntity.getTitle(),
                articleEntity.getContent(),
                articleEntity.getAuthor(),
                articleEntity.getCreatedAt());
    }

    public List<ArticleResponseDto> getArticleByAuthor(String author) {
        List<ArticleEntity> articleEntities = articleRepository.findByAuthor(author).get();
        List<ArticleResponseDto> responseDtos = new ArrayList<ArticleResponseDto>();

        for (ArticleEntity articleEntity : articleEntities) {
            responseDtos.add(new ArticleResponseDto(
                    articleEntity.getId(),
                    articleEntity.getTitle(),
                    articleEntity.getContent(),
                    articleEntity.getAuthor(),
                    articleEntity.getCreatedAt()));
        }

        return responseDtos;
    }
}
