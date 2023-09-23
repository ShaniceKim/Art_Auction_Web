package bitcamp.myapp.controller;


import bitcamp.myapp.service.AnnouncementService;
import bitcamp.myapp.service.HistoryService;
import bitcamp.myapp.vo.Article;
import bitcamp.myapp.vo.History;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    HistoryService historyService;


    @PostMapping("bidList")
    public String bitList(
            @RequestParam("currentPage") int currentPage,
            //MultipartFile photofile, // 파일 업로드 파라미터로 지정
            HttpSession session) throws Exception {

        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:/auth/form";
        }

        historyService.list(loginUser.getNo());
        return "redirect:list?status=expected&currentPage=" + currentPage;
    }

    // 현재 마이페이지 API 코드
    @GetMapping("api/bidList")
    @ResponseBody
    public ResponseEntity<Object> bidListAPI(HttpSession session) throws Exception {
        User loginUser = (User) session.getAttribute("loginUser");

        if (loginUser == null) {
            // 로그인하지 않은 사용자에게 응답
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "error");
            errorResponse.put("message", "User not logged in");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }

        // 로그인한 사용자의 히스토리 목록을 가져오기
        List<History> userHistories = historyService.list(loginUser.getNo());
        return new ResponseEntity<>(userHistories, HttpStatus.OK);
    }

}