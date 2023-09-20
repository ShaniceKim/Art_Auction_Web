package bitcamp.myapp.controller;

import bitcamp.myapp.service.ArticleService;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.vo.Article;
import bitcamp.myapp.vo.Status;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/article")
public class ArticleController {

  @Autowired
  ArticleService articleService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @GetMapping("form")
  public void form() {
  }

  @PostMapping("add")
  public String add(
          Article article,
          MultipartFile photofile,
          HttpSession session) throws Exception {

//    User loginUser = (User) session.getAttribute("loginUser");
//    if (loginUser == null) {
//      return "redirect:/auth/form";
//    }

    article.setWriter(new User(1));

    if (photofile.getSize() > 0) {
      String uploadFileUrl = ncpObjectStorageService.uploadFile(
              "bitgallery", "article/", photofile);
      article.setPhoto(uploadFileUrl);
    }
    articleService.add(article);
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(int articleNo) throws Exception {
    Article article = articleService.get(articleNo);
    if (article == null) {
      throw new Exception("해당 번호의 게시글이 없습니다");
    }
    else if (article.getStatus() != Status.expected) {
      throw new Exception("시작되지 않은 경매만 삭제가 가능합니다.");
    } else if (articleService.delete(article.getArticleNo()) == 0) {
      throw new Exception("삭제 오류");
    } else {
      return "redirect:list";
    }
  }

  @GetMapping("detail/{articleNo}")
  public String detail(@PathVariable int articleNo, Model model) throws Exception {
    Article article = articleService.get(articleNo);
    if (article != null) {
      articleService.increaseViewCount(articleNo);
      model.addAttribute("article", article);
    }
    return "article/detail";
  }

  @GetMapping("list")
  public void list(Status status, Model model) throws Exception {
    model.addAttribute("list", articleService.list(status));
  }

  @PostMapping("update")
  public String update(Article article, MultipartFile photofile, HttpSession session) throws Exception{
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

    String uploadFileUrl = ncpObjectStorageService.uploadFile(
            "bitgallery", "article/", photofile);
    article.setPhoto(uploadFileUrl);

    articleService.update(article);
    return "redirect:/article/list?status=" + a.getStatus();
  }

  @PostMapping("bid")
  public String bid() throws Exception {
    return "";
  }
}