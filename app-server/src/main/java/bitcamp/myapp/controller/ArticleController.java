package bitcamp.myapp.controller;

import bitcamp.myapp.App;
import bitcamp.myapp.service.ArticleService;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.Article;
import bitcamp.myapp.vo.Authority;
import bitcamp.myapp.vo.Status;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {

  @Autowired
  ArticleService articleService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @GetMapping("form")
  public void form(
          @RequestParam("currentPage") int currentPage,
          Model model) {
    //System.out.println("Article-form");
    model.addAttribute("currentPage", currentPage);
  }

  @PostMapping("add")
  public ResponseEntity<Object> add(
          @RequestParam("currentPage") int currentPage,
          Article article,
          MultipartFile photofile, // 파일 업로드 파라미터로 지정
          HttpSession session) throws Exception {

    User loginUser = App.loginHandler.getUser(session.getId());
    if(loginUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요한 서비스입니다");
    }

    if (article.getEndPrice() <= article.getCurPrice()) {
      throw new Exception("시작가격은 즉시구입 가격보다 높아야 합니다.");
    }
    if (article.getCurPrice() * 2 >= article.getEndPrice()) {
      throw new Exception("즉시구입 가격은 시작가격의 2배 이상으로 해야합니다.");
    }

    article.setWriter(loginUser);

    if (photofile.getSize() > 0) {
      String uploadFileUrl = ncpObjectStorageService.uploadFile(
              "bitgallery", "article/", photofile);
      article.setPhoto(uploadFileUrl);
    }

    int result = articleService.add(article);
    if (result == 2) {
      return ResponseEntity.ok("add_no_title");
    } else if (result == 3) {
      return ResponseEntity.ok("add_no_artist");
    } else if (result == 4) {
      return ResponseEntity.ok("add_no_photo");
    }
    return ResponseEntity.ok("add_commit");
  }


  @GetMapping("delete")
  public String delete(@RequestParam("currentPage") int currentPage,
                       Integer articleNo,
                       HttpSession session) throws Exception {
    User loginUser = App.loginHandler.getUser(session.getId());
    if (loginUser == null) {
      return "redirect:/auth/form";
    }

    Article article = articleService.get(articleNo);

    if (article == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    } else if (!(article.getWriter().getNo() == loginUser.getNo() || loginUser.getAuthority() == Authority.ADMIN)) {
      throw new Exception("삭제 권한이 없습니다.");
    } else if (article.getStatus() != Status.expected) {
      throw new Exception("시작되지 않은 경매만 삭제가 가능합니다.");
    } else if (articleService.delete(article.getArticleNo()) == 0) {
      throw new Exception("삭제 오류");
    } else {
      return "redirect:list?status=expected&currentPage=" + currentPage;
    }
  }


  @GetMapping("detail")
  public String detail(@RequestParam("currentPage") int currentPage,
                       @RequestParam("articleNo") int articleNo,
                       @RequestParam("path") int path,
                       HttpSession session,
                       Model model) throws Exception {
    //System.out.println("detail - currentPage : " + currentPage);
    model.addAttribute("currentPage", currentPage);

    Article article = articleService.get(articleNo);

    User loginUser = App.loginHandler.getUser(session.getId());
    if (loginUser != null && (loginUser.getNo() == article.getWriter().getNo() || loginUser.getAuthority() == Authority.ADMIN)) {
      model.addAttribute("editable", true);
    }

    if (article != null) {
      article.setPath(path);
      articleService.increaseViewCount(articleNo);

      if (article.getStatus() == Status.expected) {
        article.setRemainTime((article.getStartDate().getTime() - new Timestamp(System.currentTimeMillis()).getTime()) / 1000);
      } else if (article.getStatus() == Status.progress) {
        article.setRemainTime((article.getEndDate().getTime() - new Timestamp(System.currentTimeMillis()).getTime()) / 1000);
      } else {
        article.setRemainTime(200);
      }
      model.addAttribute("article", article);
      model.addAttribute("currentPage", currentPage);
    }
    return "article/detail";
  }

  @GetMapping("list")
  public void list(
          @RequestParam("currentPage") int currentPage,
          @RequestParam(value = "status", required = false) Status status,
          @RequestParam(value = "artist", required = false) String artist,
          HttpSession session,
          Model model) throws Exception {
    model.addAttribute("currentPage", currentPage);

    User loginUser = App.loginHandler.getUser(session.getId());
    if (loginUser != null) {
      model.addAttribute("writable", true);
    }

    if (status != null) {
      articleService.list(status, model);
    } else if (artist != null) {
      articleService.search(artist, model);
    }
  }

  @GetMapping("update")
  public String update(@RequestParam("currentPage") int currentPage,
                       @RequestParam("articleNo") int articleNo,
                       Model model) throws Exception {
    model.addAttribute("currentPage", currentPage);
    Article article = articleService.get(articleNo);
    if (article != null) {
      model.addAttribute("article", article);
    }
    return "article/update";
  }

  @PostMapping("commit")
  public String commit(@RequestParam("currentPage") int currentPage,
                       Article article,
                       MultipartFile photofile,
                       HttpSession session) throws Exception {
    User loginUser = App.loginHandler.getUser(session.getId());
    if (loginUser == null) {
      return "redirect:/auth/form";
    }

    Article a = articleService.get(article.getArticleNo());

    if (a == null) {
      throw new Exception("해당 번호의 게시글이 없습니다.");
    } else if (!(a.getWriter().getNo() == loginUser.getNo() || loginUser.getAuthority() == Authority.ADMIN)) {
      throw new Exception("수정 권한이 없습니다.");
    } else if (a.getStatus() != Status.expected) {
      throw new Exception("시작되지 않은 경매만 수정 가능합니다.");
    }

    if (photofile.getSize() > 0) {
      String uploadFileUrl = ncpObjectStorageService.uploadFile(
              "bitgallery", "article/", photofile);
      article.setPhoto(uploadFileUrl);
    }

    articleService.update(article);
    return "redirect:/article/list?status=" + a.getStatus() + "&currentPage=" + currentPage;
  }

  @ResponseBody
  @GetMapping("/getEvent")
  public List<Article> getAuctionArticlesByDate(@RequestParam String date) throws Exception {
    return articleService.findAuctionArticlesByDate(date);
  }

  @GetMapping("tender")

  @PostMapping("bid")
  public String bid() throws Exception {
    return "";
  }

  @PostMapping("buy")
  public String buy() throws Exception {
    return "";
  }

}