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


    // 1. requestParam 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    // @GetMapping은 HTTP GET 요청을 처리하는 메서드
    // "/article-by-author-param" URL로 요청이 오면 이 메서드가 실행
    // @RequestParam은 URL의 쿼리 파라미터에서 "author" 값을 가져
    @GetMapping("/article-by-author-param")
    public ArticleResponseDto getArticleByAuthorParam(@RequestParam String author) {
        ArticleResponseDto response = articleService.getArticleByAuthor(author);
        return response;
    }


    // 2. pathVariable 방식으로 author를 검색하여 article을 찾는 api (GetMapping)
    // "/article-by-author-path/{author}" 경로에서 {author} 값이 변수로 매핑
    // @PathVariable은 URL 경로의 일부를 변수로 바인딩
    @GetMapping("/article-by-author-path/{author}")
    public ArticleResponseDto getArticleByAuthorPath(@PathVariable String author) {
        ArticleResponseDto response = articleService.getArticleByAuthor(author);
        return response;
    }



    // 3. 데이터베이스 내의 모든 게시글을 삭제하는 api (DeleteMapping)
    // @DeleteMapping은 HTTP DELETE 요청을 처리하는 메서드
    @DeleteMapping("/articles")
    public ResponseEntity<String> deleteAllArticles() {
        // 데이터베이스의 모든 게시글을 삭제하는 서비스 메서드 호출
        articleService.deleteAllArticles();
        // 상태 코드를 204 (No Content)로 설정하고 메시지를 반환
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All articles have been deleted.");
    }


    // 4. 데이터 전송 방식 3가지(pathVariable requestParam, requestBody), 로 데이터베이스 내의 특정 Id의 게시글을 삭제하는 api
    // 총 3개의 api

    // 4-1. pathVariable 방식으로 특정 Id의 게시글을 삭제하는 api
    @DeleteMapping("/article-by-id-path/{id}")
    public ResponseEntity<String> deleteArticleByIdPath(@PathVariable Long id) {
        // ID를 기반으로 게시글을 삭제
        articleService.delete(id);
        // 성공 메시지를 반환하며 HTTP 상태 코드는 204로 설정
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Article with ID " + id + " has been deleted.");
    }

    // 4-2. requestParam 방식으로 특정 Id의 게시글을 삭제하는 api
    @DeleteMapping("/article-by-id-param")
    public ResponseEntity<String> deleteArticleByIdParam(@RequestParam Long id) {
        // 쿼리 파라미터에서 ID를 가져와 삭제
        articleService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Article with ID " + id + " has been deleted.");
    }

    // 4-3. requestBody 방식으로 특정 Id의 게시글을 삭제하는 api
    // @RequestBody는 HTTP 요청 본문에서 데이터를 가져와 매핑
    @DeleteMapping("/article-by-id-body")
    public ResponseEntity<String> deleteArticleByIdBody(@RequestBody ArticleRequestDto requestDto) {
        // 요청 본문에서 가져온 ID를 기반으로 게시글을 삭제
        articleService.delete(requestDto.getId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Article with ID " + requestDto.getId() + " has been deleted.");
    }
}
