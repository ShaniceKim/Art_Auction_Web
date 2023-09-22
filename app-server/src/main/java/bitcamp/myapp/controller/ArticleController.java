package bitcamp.myapp.controller;

import bitcamp.myapp.service.ArticleService;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.Article;
import bitcamp.myapp.vo.Status;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
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
    model.addAttribute("currentPage", currentPage);
  }

  @PostMapping("add")
  public String add(
          @RequestParam("currentPage") int currentPage,
          Article article,
          MultipartFile photofile, // 파일 업로드 파라미터로 지정
          HttpSession session) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/auth/form";
    }


    article.setWriter(loginUser);


    if (photofile.getSize() > 0) {
      String uploadFileUrl = ncpObjectStorageService.uploadFile(
              "bitgallery", "article/", photofile);
      article.setPhoto(uploadFileUrl);
    }
    articleService.add(article);
    return "redirect:list?status=expected&currentPage=" + currentPage;
  }


  @GetMapping("delete")
  public String delete(@RequestParam("currentPage") int currentPage,
                       Integer articleNo,
                       HttpSession session) throws Exception {
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/auth/form";
    }

    Article article = articleService.get(articleNo);

    if (article == null || article.getArticleNo() != loginUser.getNo()) {
      throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
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
                       Model model) throws Exception {
    model.addAttribute("currentPage", currentPage);
    Article article = articleService.get(articleNo);

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
    }
    return "article/detail";
  }

  @GetMapping("list")
  public void list(
          @RequestParam("currentPage") int currentPage,
          @RequestParam(value = "status", required = false) Status status,
          @RequestParam(value = "artist", required = false) String artist,
          Model model) throws Exception {
    model.addAttribute("currentPage", currentPage);

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
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/auth/form";
    }

    Article a = articleService.get(article.getArticleNo());

    if (a == null || a.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("작성자만 수정 가능합니다.");
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