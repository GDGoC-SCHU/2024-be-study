package gdg.week2.controller;

import gdg.week2.dto.ArticleCreateDto;
import gdg.week2.dto.ArticleRequestDto;
import gdg.week2.dto.ArticleResponseDto;
import gdg.week2.dto.ArticleUpdateDto;
import gdg.week2.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // rest-ful controller 임을 명시
public class ArticleController {

    /**
     * IoC - "제어의 역전"으로 인해 개발자가 직접 객체를 생성하지 않고
     * DI - 스프링으로부터 "의존성 주입"을 받아 객체를 사용할 수 있다.
     * 자세한 내용은 3주차에 다룰 예정
     * articleService = new ArticleService();
     * 이렇게 객체를 생성하지 않고 사용
     */
    private ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/example")
    public ResponseEntity<String> example() {
        String responseBody = "Hello, World!";
        return ResponseEntity.status(200).body(responseBody);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles")
    public List<ArticleResponseDto> getAllArticle() {
        List<ArticleResponseDto> response = articleService.getAllArticle();
        return response;
    }

    /**
     * Get 요청 - 데이터를 URL에 포함시켜 요청
     * URL에 데이터를 포함시키는 방법 - 1.pathVariable, 2.requestParam
     * pathVariable
     **/
    @GetMapping("/article-path/{title}")
    public ArticleResponseDto getArticleByPath(@PathVariable String title) {
        ArticleResponseDto response = articleService.getArticle(title);
        return response;
    }

    /**
     * requestParam
     **/
    @GetMapping("/article-param")
    public ArticleResponseDto getArticleByParam(@RequestParam String title) {
        ArticleResponseDto response = articleService.getArticle(title);
        return response;
    }

    /**
     * requestBody
     **/
    @GetMapping("/article-body")
    public ArticleResponseDto getArticleByBody(@RequestBody ArticleRequestDto requestDto) {
        ArticleResponseDto response = articleService.getArticle(requestDto.getTitle());
        return response;
    }

    /**
     * responseEntity
     **/
    @GetMapping("/article-responseEntity/{title}")
    public ResponseEntity<ArticleResponseDto> responseEntityExample(@PathVariable String title) {
        ArticleResponseDto response = articleService.getArticle(title);
        return ResponseEntity.status(200).body(response);
    }

    /**
     * method = post
     *
     * @PostMapping("/article-create")
     */
    @RequestMapping(method = RequestMethod.POST, path = "/article-create")
    public String createArticle(@RequestBody ArticleCreateDto articleCreateDto) {
        String message = articleService.create(articleCreateDto);
        return message;
    }

    /**
     * patch - 수정
     * @RequestBody articleUpdateDto 는 수정할 내용을 담는 객체입니다.
     * @RequestParam id 는 수정할 게시글의 id 입니다.
     */
    @PatchMapping("/article-patch")
    public ResponseEntity<ArticleResponseDto> update(@RequestParam Long id, @RequestBody ArticleUpdateDto articleUpdateDto) {
        ArticleResponseDto response = articleService.update(id, articleUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/article-delete")
    public String delete(@RequestParam Long id) {
        String message = articleService.delete(id);
        return message;
    }


    @GetMapping("/article")
    public ArticleResponseDto getArticle() {
        ArticleResponseDto article = articleService.getArticle(1L);
        return article;
    }

    /**
     * 과제 - 아래 3가지 api를 완성하여 제출하세요.
     * 총 6개의 api 완성
     */
    // 1. requestParam 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    @GetMapping("/article/author")
    public ArticleResponseDto findArticle(@RequestParam String author) {
        return articleService.getArticleByAuthor(author);
    }

    // 2. pathVariable 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    @GetMapping("/article/{author}")
    public ArticleResponseDto findArticleByAuthor(@PathVariable String author) {
        return articleService.getArticleByAuthor(author);
    }

    // 3. 데이터베이스 내의 모든 게시글을 삭제하는 api (DeleteMapping)
    @DeleteMapping("/articles")
    public void deleteAllArticle() {
        articleService.deleteAllArticle();
    }


    // 4. 데이터 전송 방식 3가지(pathVariable requestParam, requestBody), 로 데이터베이스 내의 특정 Id의 게시글을 삭제하는 api
    // 총 3개의 api
    @DeleteMapping("/article/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleService.delete(id);
    }
    @DeleteMapping("/article/id")
    public void deleteArticleById(@RequestParam Long id) {
        articleService.delete(id);
    }
    @DeleteMapping("/article")
    public void deleteArticle(@RequestBody ArticleResponseDto request) {
        articleService.delete(request.getId());
    }
}
