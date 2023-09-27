package bitcamp.myapp.controller;

import bitcamp.myapp.App;
import bitcamp.myapp.service.ExchangeService;
import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.Exchange;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/exchange")
public class ExchangeController {

  {
  }

  @Autowired
  ExchangeService exchangeService;

  @Autowired
  UserService userService;  // UserService 주입

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @GetMapping("form")
  public void form(HttpSession session, Model model) {
    User loginUser = App.loginHandler.getUser(session.getId());
    if (loginUser == null) {
      // 만약 loginUser가 null이면 로그인 페이지나 다른 페이지로 리다이렉트
      //return "redirect:/auth/form";
    } else {
      model.addAttribute("loginUser", loginUser);
    }
  }

  @PostMapping("add")
  public String add(Exchange exchange, HttpSession session) throws Exception {
    User loginUser = App.loginHandler.getUser(session.getId());
    if (loginUser == null) {
      return "redirect:/auth/form";
    }

    int requestedPoints = exchange.getExchangePoint();

    exchange.setUser(loginUser);
    exchange.setTitle(loginUser.getName() + "님 환전요청");
    exchange.setCreatedDate(new Date());
    exchangeService.add(exchange);

    // 포인트 차감하고 DB에 업데이트
    userService.updateUserPoints(Integer.toString(loginUser.getNo()), requestedPoints);

    // 업데이트된 loginUser 정보를 다시 데이터베이스에서 가져와서 세션을 업데이트
    User updatedUser = userService.get(loginUser.getNo());
    session.setAttribute("loginUser", updatedUser);

    return "redirect:/points/chargePoint";
  }

  @GetMapping("delete")
  public String delete(int no, HttpSession session) throws Exception {
    User loginUser = App.loginHandler.getUser(session.getId());
    if (loginUser == null) {
      return "redirect:/auth/form";
    }

    Exchange e = exchangeService.get(no);

    if (e == null || e.getUser().getNo() != loginUser.getNo()) {
      throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
    } else {
      exchangeService.delete(e.getNo());
      return "redirect:/exchange/list";
    }
  }

  @GetMapping("list")
  public void list(Model model, HttpSession session) throws Exception {
    User loginUser = App.loginHandler.getUser(session.getId());
    model.addAttribute("list", exchangeService.list());

    if (loginUser != null) {
      model.addAttribute("currentUserId", loginUser.getNo());
    }
  }

  @GetMapping("listUserExchanges")
  @ResponseBody  // API로 동작하게 만들기 위해 추가
  public ResponseEntity<Object> listUserExchanges(HttpSession session) throws Exception {
    User loginUser =App.loginHandler.getUser(session.getId());

    if (loginUser == null) {
      // 로그인하지 않은 사용자에게 응답
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put("status", "error");
      errorResponse.put("message", "User not logged in");
      return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    // 로그인한 사용자의 환전 요청만 가져옵니다.
    List<Exchange> userExchanges = exchangeService.listByUserNo(loginUser.getNo());
    return new ResponseEntity<>(userExchanges, HttpStatus.OK);
  }

  @GetMapping("detail/{no}")
  public String detail(@PathVariable int no, Model model, HttpSession session, RedirectAttributes redirectAttrs) throws Exception {
    Exchange exchange = exchangeService.get(no);
    User loginUser = App.loginHandler.getUser(session.getId());

    if (exchange == null || loginUser == null || exchange.getUser().getNo() != loginUser.getNo()) {
      redirectAttrs.addFlashAttribute("error", "unauthorized");  // flash attribute를 사용하여 잠깐 동안만 저장된 메시지를 넘깁니다.
      return "redirect:/exchange/list";  // 목록 페이지로 리다이렉트합니다.
    }

    String content = exchange.getContent();
    String[] parts = content.split(",");

    // 각각의 부분을 모델에 추가
    if (parts.length >= 3) {
      model.addAttribute("name", parts[0]);
      model.addAttribute("number", parts[1]);
      model.addAttribute("bank", parts[2]);
    }

    model.addAttribute("exchange", exchange);
    return "exchange/detail";
  }

  @PostMapping("update")
  public String update(Exchange exchange,
                       @RequestParam String name,
                       @RequestParam String number,
                       @RequestParam String bank,
                       @RequestParam int userNo,
                       HttpSession session) throws Exception {

    User loginUser = App.loginHandler.getUser(session.getId());
    if (loginUser == null) {
      return "redirect:/auth/form";
    }

    System.out.println("User No from request: " + userNo);

    // userNo 값을 사용하여 User 객체를 검색
    User user = userService.get(exchange.getUser().getNo());
    if (user == null) {
      throw new Exception("유효하지 않은 사용자 번호입니다.");
    }
    exchange.setUser(user);

    Exchange existingExchange = exchangeService.get(exchange.getNo());
    if (existingExchange == null || existingExchange.getUser().getNo() != loginUser.getNo()) {
      throw new Exception("게시글이 존재하지 않거나 변경 권한이 없습니다.");
    }

    // 로그 추가
    System.out.println("Requested Exchange No: " + exchange.getNo());
    System.out.println("Logged User No: " + loginUser.getNo());
    System.out.println("Exchange User No: " + existingExchange.getUser().getNo());

    // 받아온 파라미터를 `,`로 합친다.
    String combinedContent = String.join(",", name, number, bank);
    exchange.setContent(combinedContent);

    exchangeService.update(exchange);

    return "redirect:/exchange/list";
  }
}