package bitcamp.myapp.controller;

import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


@Controller
@RequestMapping("/user")
public class UserController {
  {
    System.out.println("UserController 생성됨!");
  }


  @Autowired
  UserService userService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @GetMapping("form")
  public void form() {
  }

  @PostMapping("add")
  public String add(User user) throws Exception {
    System.out.println(user.toString());
    userService.add(user);
    return "redirect:list";
  }

  @GetMapping("delete")
  public String delete(int no) throws Exception {
    if (userService.delete(no) == 0) {
      throw new Exception("해당 번호의 회원이 없습니다.");
    } else {
      return "redirect:list";
    }
  }

  @GetMapping("{no}")
  public String detail(@PathVariable int no, Model model) throws Exception {
    model.addAttribute("user", userService.get(no));
    return "user/detail";
  }

  @GetMapping("list")
  public void list(Model model) throws Exception {
    model.addAttribute("list", userService.list());
  }

  @PostMapping("update")
  public String update(User user, MultipartFile photofile) throws Exception {
//    if (photofile.getSize() > 0) {
//      String uploadFileUrl = ncpObjectStorageService.uploadFile(
//              "bitcamp-nc7-bucket-118", "user/", photofile);
//      member.setPhoto(uploadFileUrl);
//    }

    if (userService.update(user) == 0) {
      throw new Exception("회원이 없습니다.");
    } else {
      return "redirect:list";
    }
  }
}