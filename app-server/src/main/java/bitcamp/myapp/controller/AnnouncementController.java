package bitcamp.myapp.controller;

import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.service.AnnouncementService;
import bitcamp.myapp.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

  @Autowired
  AnnouncementService announcementService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @GetMapping("form")
  public void form(
          @RequestParam("currentPage") int currentPage,
          Model model,
          HttpSession session
  ) throws Exception {
    model.addAttribute("currentPage", currentPage);

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null || loginUser.getAuthority() != Authority.ADMIN) {
      throw new Exception("로그인이 되어있지 않거나 권한이 없습니다.");
    }
  }

  @ResponseBody
  @PostMapping("add")
  public ResponseEntity<Object> addAnnouncement(
          @RequestParam("currentPage") int currentPage,
          Announcement requestData,
          MultipartFile[] files,
          HttpSession session,
          Model model
  ) throws Exception {
    model.addAttribute("currentPage", currentPage);

    User loginUser = (User) session.getAttribute("loginUser");
    if(loginUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NoLogin");
    }
    if (loginUser.getAuthority() != Authority.ADMIN) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NoAdmin");
    }

    ArrayList<AnnouncementAttachedFile> announcementAttachedFiles = new ArrayList<>();
    for (MultipartFile part : files) {
      if (part.getSize() > 0) {
        String uploadFileUrl = ncpObjectStorageService.uploadFile(
                "bitgallery", "announcement/", part);
        AnnouncementAttachedFile announcementAttachedFile = new AnnouncementAttachedFile();
        announcementAttachedFile.setFilePath(uploadFileUrl);
        announcementAttachedFiles.add(announcementAttachedFile);
      }
    }
    requestData.setWriter(loginUser);
    requestData.setAnnouncementAttachedFiles(announcementAttachedFiles);

    if (announcementService.add(requestData) == 0) {
      return ResponseEntity.ok(false);
    }
    return ResponseEntity.ok(true);
  }

  @GetMapping("delete")
  public String delete(@RequestParam("currentPage") int currentPage, int no, HttpSession session) throws Exception {
    Announcement announcement = announcementService.get(no);
    User loginUser = (User) session.getAttribute("loginUser");

    if (loginUser == null || loginUser.getAuthority() != Authority.ADMIN) {
      throw new Exception("로그인이 되어있지 않거나 권한이 없습니다.");
    } else if (announcement == null) {
      throw new Exception("해당 번호의 게시물이 없습니다.");
    } else {
      announcementService.delete(announcement.getNo());
      return "redirect:/announcement/list?currentPage=" + currentPage;
    }
  }

  @GetMapping("detail")
  public String detail(@RequestParam("isEdit") boolean isEdit, @RequestParam("currentPage") int currentPage,
                       @RequestParam("no") int no, Model model, HttpSession session) throws Exception {

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      model.addAttribute("authority", "User");
    } else {
      model.addAttribute("authority", loginUser.getAuthority());
    }

    model.addAttribute("isEdit", isEdit);
//    System.out.println("isEdit =  " + isEdit);
    model.addAttribute("currentPage", currentPage);

    Announcement announcement = announcementService.get(no);
    if (announcement != null) {
      model.addAttribute("announcement", announcement);
    }
    return "announcement/detail";
  }

  @GetMapping("list")
  public void list(@RequestParam("currentPage") int currentPage, Model model, HttpSession session) throws Exception {
    model.addAttribute("currentPage", currentPage);

    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      model.addAttribute("authority", "User");
    } else {
      model.addAttribute("authority", loginUser.getAuthority());
    }

    model.addAttribute("test", 1);
    announcementService.list(model);
  }

  @ResponseBody
  @PostMapping("update")
  public ResponseEntity<Object> update(
          @RequestParam("currentPage") int currentPage,
          Announcement announcement,
          MultipartFile[] files,
          HttpSession session,
          Model model
  ) throws Exception {
//    System.out.println(currentPage + "업데이트 호출됨");
//    System.out.println("fixed값 확인 : " + announcement.getFixed());
    Announcement a = announcementService.get(announcement.getNo());
    model.addAttribute("currentPage", currentPage);

    User loginUser = (User) session.getAttribute("loginUser");
    if(loginUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NoLogin");
    }
    if (loginUser.getAuthority() != Authority.ADMIN) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("NoAdmin");
    }

    ArrayList<AnnouncementAttachedFile> announcementAttachedFiles = new ArrayList<>();
    for (MultipartFile part : files) {
      if (part.getSize() > 0) {
        String uploadFileUrl = ncpObjectStorageService.uploadFile(
                "bitgallery", "announcement/", part);
        AnnouncementAttachedFile announcementAttachedFile = new AnnouncementAttachedFile();
        announcementAttachedFile.setFilePath(uploadFileUrl);
        announcementAttachedFiles.add(announcementAttachedFile);
      }
    }
    announcement.setWriter(loginUser);
    announcement.setAnnouncementAttachedFiles(announcementAttachedFiles);

    if (announcementService.update(announcement) == 0) {
      return ResponseEntity.ok(false);
    }
    return ResponseEntity.ok(true);
  }

  @GetMapping("fileDelete")
  public String fileDelete(@RequestParam("isEdit") boolean isEdit, @RequestParam("currentPage") int currentPage,
                           @RequestParam("no") int no, HttpSession session, Model model) throws Exception {
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null || loginUser.getAuthority() != Authority.ADMIN) {
      throw new Exception("로그인이 되어있지 않거나 권한이 없습니다.");
    }

    model.addAttribute("isEdit", isEdit);
//    System.out.println("AnnouncementController.fileDelete.isEdit =  " + isEdit);

    Announcement announcement = null;
    AnnouncementAttachedFile announcementAttachedFile = announcementService.getAnnouncementAttachedFile(no);
    announcement = announcementService.get(announcementAttachedFile.getAnnouncementNo());
//    System.out.println(no + "  no 호출!");

    if (announcementService.deleteAttachedFile(no) == 0) {
      throw new Exception("해당 번호의 첨부파일이 없습니다.");
    } else {
      return "redirect:/announcement/detail?currentPage=" + currentPage + "&no=" + announcement.getNo() + "&isEdit=" + isEdit;
    }
  }

}