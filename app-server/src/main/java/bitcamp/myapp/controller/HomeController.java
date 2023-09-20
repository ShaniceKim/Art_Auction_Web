package bitcamp.myapp.controller;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.service.MemberService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.Authority;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  {
    System.out.println("HomeController 생성됨!");
  }

  @Autowired
  UserService userService;

  @GetMapping("/")
  public String home() throws Exception {
//    User user = new User();
//    user.setAuthority(Authority.USER);
//    user.setEmail("test1@test.com");
//    user.setPassword("1234");
//    user.setName("abc");
//    user.setPhone("010-3333-3333");
//    user.setZonecode(54321);
//    user.setAddress("주소는 ...?");
//    user.setDetailAddr("상세주소는 바로~~~");
//    userService.add(user);

    return "index";
  }
}