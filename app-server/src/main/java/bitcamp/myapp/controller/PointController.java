package bitcamp.myapp.controller;

import bitcamp.myapp.service.UserService;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PointController {

    @Autowired
    UserService userService;

    @GetMapping("/chargePoint")
    public String chargePointPage() {
        return "points/chargePoint";
    }

    @PostMapping("/updatepoint")
    public ResponseEntity<Map<String, Object>> updatePoint(
            @RequestParam("userNo") String userNo,
            @RequestParam("bidAmount") int bidAmount, HttpSession session) throws Exception {


        userService.updateUserPoints(userNo, bidAmount);

        // 세션 정보 업데이트
        User updatedUser = userService.get(Integer.parseInt(userNo)); // 업데이트된 회원 정보 가져오기
        session.setAttribute("loginUser", updatedUser); // 세션 업데이트

        // 응답 데이터 생성 및 반환
        Map<String, Object> response = new HashMap<>();
        response.put("userNo", userNo);
        response.put("bidAmount", bidAmount);

        return ResponseEntity.ok(response);
    }



    @PostMapping("/chargepoint")
    public ResponseEntity<Map<String, Object>> chargepoint(
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



}


