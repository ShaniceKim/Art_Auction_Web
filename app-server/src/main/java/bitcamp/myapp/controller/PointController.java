package bitcamp.myapp.controller;

import bitcamp.myapp.dto.RequestPaymentDTO;
import bitcamp.myapp.service.ArticleService;
import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.Article;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/points")
public class PointController {

    private final UserService userService;
    private final ArticleService articleService;

    public PointController(UserService userService, ArticleService articleService) {
        this.userService = userService;
        this.articleService = articleService;
    }

    @GetMapping("chargePoint") //
    public String chargePointPage() {
        return "points/chargePoint";
    }


    @PostMapping("chargePoint") //충전
    public ResponseEntity<Map<String, Object>> chargePoint(
            @RequestParam("userNo") String userNo,
            @RequestParam("bidAmount") int bidAmount, HttpSession session) throws Exception {


        userService.chargeUserPoints(userNo, bidAmount);

        // 세션 정보 업데이트
        User updatedUser = userService.get(Integer.parseInt(userNo)); // 업데이트된 회원 정보 가져오기
        session.setAttribute("loginUser", updatedUser); // 세션 업데이트

        // 응답 데이터 생성 및 반환
        Map<String, Object> response = new HashMap<>();
        response.put("userNo", userNo);
        response.put("bidAmount", bidAmount);

        return ResponseEntity.ok(response);
    }

    @PostMapping("bidPoint") //입찰
    public ResponseEntity<Map<String, Object>> bidPoint(
            @RequestParam("userNo") String userNo,
            @RequestParam("articleNo") int articleNo,
            @RequestParam("bidAmount") int bidAmount, HttpSession session) throws Exception {

        // 세션 정보 업데이트
        User updatedUser = userService.get(Integer.parseInt(userNo)); // 업데이트된 회원 정보 가져오기
        session.setAttribute("loginUser", updatedUser); // 세션 업데이트

        // articleNo를 사용하여 article 정보 가져오기
        Article article = articleService.get((articleNo));
        session.setAttribute("article", article);

        int currentPrice = article.getCurPrice();

        articleService.updateArticleBidPoint(articleNo, bidAmount);
        articleService.updateArticleBidNum((articleNo));
        userService.updateUserPoints(userNo, bidAmount);

        session.setAttribute("loginUser", updatedUser); // 세션 업데이트
        session.setAttribute("article", article); // 세션 업데이트

        // 응답 데이터 생성 및 반환
        Map<String, Object> response = new HashMap<>();
        response.put("userNo", userNo);
        response.put("bidAmount", bidAmount);

        return ResponseEntity.ok(response);
    }


    @ResponseBody
    @PostMapping("purchasePoint")
    public boolean purchasePoint(@RequestBody RequestPaymentDTO dto, HttpSession session) throws Exception {

        // articleNo를 사용하여 article 정보 가져오기
        Article article = articleService.get(Integer.parseInt(dto.getArticleNo()));
        session.setAttribute("article", article);

        // 세션 정보 업데이트
        User updatedUser = userService.get(Integer.parseInt(dto.getUserNo())); // 업데이트된 회원 정보 가져오기
        session.setAttribute("loginUser", updatedUser); // 세션 업데이트


        articleService.updateArticleStatus(Integer.parseInt(dto.getArticleNo()));
        articleService.updateArticleBidNum(Integer.parseInt(dto.getArticleNo()));
        userService.updateUserPoints(dto.getUserNo(), dto.getBidAmount());

        return true;
    }

    @PostMapping("checkPoint") //결제검증
    public ResponseEntity<Map<String, Object>> checkPoint(
            @RequestParam("userNo") String userNo,
            @RequestParam("articleNo") int articleNo,
            @RequestParam("bidAmount") int bidAmount, HttpSession session) throws Exception {

        // 세션 정보 업데이트
        User updatedUser = userService.get(Integer.parseInt(userNo)); // 업데이트된 회원 정보 가져오기
        session.setAttribute("loginUser", updatedUser); // 세션 업데이트

        // articleNo를 사용하여 article 정보 가져오기
        Article article = articleService.get((articleNo));
        session.setAttribute("article", article);

        // 해당 article의 현재 가격 가져오기
        int currentPrice = article.getCurPrice();

        // 응답을 위한 Map 생성
        Map<String, Object> response = new HashMap<>();

        if (currentPrice > bidAmount) {
            response.put("isValidBid", false);
        } else {
            response.put("isValidBid", true);
        }

        return ResponseEntity.ok(response);
    }
}