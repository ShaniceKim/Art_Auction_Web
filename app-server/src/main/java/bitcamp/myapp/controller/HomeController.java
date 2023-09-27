package bitcamp.myapp.controller;

import bitcamp.myapp.App;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

  @Autowired
  UserService userService;

  @GetMapping("/")
  public String home(HttpSession session) throws Exception {
    User loginUser = App.loginHandler.getUser(session.getId());
    if (loginUser == null) {
      session.setAttribute("loginUser",null);
    } else {
      session.setAttribute("loginUser",loginUser);
    }

    return "index";
  }
}