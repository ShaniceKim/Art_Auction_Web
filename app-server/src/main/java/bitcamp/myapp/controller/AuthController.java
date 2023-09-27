package bitcamp.myapp.controller;

import bitcamp.myapp.App;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  UserService userService;

  @GetMapping("form")
  public void form(@CookieValue(required = false) String email, Model model) {

    model.addAttribute("email", email);
  }

  @PostMapping("login")
  public String login(
          String email,
          String password,
          String saveEmail,
          HttpSession session,
          Model model,
          HttpServletResponse response) throws Exception {

    if (saveEmail != null) {
      Cookie cookie = new Cookie("email", email);
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("email", "no");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    User loginUser = userService.get(email, password);
    if (loginUser == null) {
      model.addAttribute("refresh", "2;url=form");
      throw new Exception("회원 정보가 일치하지 않습니다.");
    }

    // 중복로그인시, 이전 로그인사람대신 현재로그인 사람기준으로 바꿈
    if (!App.loginHandler.getSessionId(loginUser.getEmail()).isEmpty()) {
      App.loginHandler.removeUser(App.loginHandler.getSessionId(loginUser.getEmail()));
    }
    App.loginHandler.addUser(session.getId(),loginUser);
    System.out.println("------------------" + loginUser);
    session.setAttribute("loginUser", loginUser);
    return "redirect:/";
  }

  @GetMapping("logout")
  public String logout(HttpSession session, Model model) throws Exception {
    App.loginHandler.removeUser(session.getId());
    return "redirect:/";
  }
}