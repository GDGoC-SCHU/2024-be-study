package gdg.gideon.week2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import gdg.gideon.week2.dto.ArticleCreateDto;
import gdg.gideon.week2.dto.ArticleDeleteDto;
import gdg.gideon.week2.dto.ArticleRequestDto;
import gdg.gideon.week2.dto.ArticleResponseDto;
import gdg.gideon.week2.services.ArticleService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ArticleController {

    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/example")
    public ResponseEntity<String> example() {
        String response = "Hello World";
        return ResponseEntity.ok(response);
    }

    @RequestMapping(path = "/articles", method = RequestMethod.GET)
    public List<ArticleResponseDto> gettAllArticleResponseDtos() {
        List<ArticleResponseDto> response = articleService.getAllArticle();
        return response;
    }

    // Get title from url path
    @GetMapping("/article-path/{title}")
    public ArticleResponseDto getArticleByPath(@PathVariable("title") String title) {
        ArticleResponseDto response = articleService.getArticle(title);
        return response;
    }

    // Get title from parameter
    @GetMapping("/article-param")
    public ArticleResponseDto getArticleByParam(@RequestParam("title") String title) {
        ArticleResponseDto response = articleService.getArticle(title);
        return response;
    }

    // Get title from body
    @GetMapping("/article-body")
    public ArticleResponseDto getArticleByBody(@RequestBody ArticleRequestDto articleRequestDto) {
        ArticleResponseDto response = articleService.getArticle(articleRequestDto.getTitle());
        return response;
    }

    @PostMapping("/article")
    public String CreateNewArticle(@RequestBody ArticleCreateDto articleCreateDto) {
        // TODO: process POST request
        String response = articleService.createArticle(articleCreateDto);

        return response;
    }

    @PostMapping("/many-article")
    public String CreateManyArticle(@RequestBody List<ArticleCreateDto> articleCreateDtos) {
        // TODO: process POST request
        String response = articleService.createManyArticle(articleCreateDtos);

        return response;
    }

    /**
     * 과제 - 아래 3가지 api를 완성하여 제출하세요.
     * 총 6개의 api 완성
     */
    // 1. requestParam 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    @GetMapping("/article-by-author")
    public List<ArticleResponseDto> getArticleByAuthor(@RequestParam("author") String author) {
        List<ArticleResponseDto> response = articleService.getArticleByAuthor(author);
        return response;
    }

    // 2. pathVariable 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    @GetMapping("/article-by-author/{author}")
    public List<ArticleResponseDto> getArticleByAuthorPath(@PathVariable("author") String author) {
        List<ArticleResponseDto> response = articleService.getArticleByAuthor(author);
        return response;
    }

    // 3. 데이터베이스 내의 모든 게시글을 삭제하는 api (DeleteMapping)
    @GetMapping("/delete-all-article")
    public String deleteAllArticle() {
        String response = articleService.deleteAllArticle();
        return response;
    }

    // 4. 데이터 전송 방식 3가지(pathVariable requestParam, requestBody), 로 데이터베이스 내의 특정 Id의
    // 게시글을 삭제하는 api
    // 총 3개의 api
    @GetMapping("/delete-article-by-id")
    public String deleteArticleById(@RequestParam("id") Long id) {
        String response = articleService.deleteArticle(id);
        return response;
    }

    // 2. pathVariable 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    @GetMapping("/delete-article-by-id/{id}")
    public String deleteArticleByIdPath(@PathVariable("id") Long id) {
        String response = articleService.deleteArticle(id);
        return response;
    }

    @GetMapping("/delete-article-by-id-body")
    public String deleteArticleByIdBody(@RequestBody ArticleDeleteDto articleDeleteDto) {
        String response = articleService.deleteArticle(articleDeleteDto.getId());
        return response;
    }
}
