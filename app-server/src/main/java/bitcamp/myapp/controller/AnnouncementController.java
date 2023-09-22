package bitcamp.myapp.controller;

import bitcamp.myapp.service.NcpObjectStorageService;
import bitcamp.myapp.service.AnnouncementService;
import bitcamp.myapp.vo.Announcement;
import bitcamp.myapp.vo.AnnouncementAttachedFile;
import bitcamp.myapp.vo.Authority;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController {

  @Autowired
  AnnouncementService announcementService;

  @Autowired
  NcpObjectStorageService ncpObjectStorageService;

  @GetMapping("form")
  public void form(@RequestParam("currentPage") int currentPage, Model model) {
    model.addAttribute("currentPage", currentPage);

  }

  @PostMapping("add")
  public String add(@RequestParam("currentPage") int currentPage, Announcement announcement,
                    MultipartFile[] files, HttpSession session, Model model) throws Exception {
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/auth/form";
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

    announcementService.add(announcement);
    return "redirect:/announcement/list?currentPage=" + currentPage;
  }

  @GetMapping("delete")
  public String delete(@RequestParam("currentPage") int currentPage, int no, HttpSession session) throws Exception {
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/auth/form";
    }

    Announcement a = announcementService.get(no);

    if (a == null || a.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
    } else {
      announcementService.delete(a.getNo());
      return "redirect:/announcement/list?currentPage=" + currentPage;
    }
  }

  @GetMapping("detail")
  public String detail(@RequestParam("currentPage") int currentPage, @RequestParam("no") int no, Model model) throws Exception {
    model.addAttribute("currentPage", currentPage);
    System.out.println(currentPage);
    Announcement announcement = announcementService.get(no);
    if (announcement != null) {
      model.addAttribute("announcement", announcement);
    }
    return "announcement/detail";
  }

  @GetMapping("list")
  public void list(@RequestParam("currentPage") int currentPage, Model model) throws Exception {
    model.addAttribute("currentPage", currentPage);
    announcementService.list(model);
  }

  @PostMapping("update")
  public String update(@RequestParam("currentPage") int currentPage, Announcement announcement,
                       MultipartFile[] files, HttpSession session) throws Exception {
    System.out.println(currentPage + "업데이트 호출됨");
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/auth/form";
    }
    Announcement a = announcementService.get(announcement.getNo());
    if (a == null || a.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("게시글이 존재하지 않거나 변경 권한이 없습니다.");
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
    announcement.setAnnouncementAttachedFiles(announcementAttachedFiles);

    announcementService.update(announcement);
    return "redirect:/announcement/list?currentPage=" + currentPage;
  }

  @GetMapping("fileDelete")
  public String fileDelete(@RequestParam("currentPage") int currentPage, @RequestParam("no") int no, HttpSession session, Model model) throws Exception {
    User loginUser = (User) session.getAttribute("loginUser");
    if (loginUser == null) {
      return "redirect:/auth/form";
    }
    Announcement announcement = null;
    AnnouncementAttachedFile announcementAttachedFile = announcementService.getAnnouncementAttachedFile(no);
    announcement = announcementService.get(announcementAttachedFile.getAnnouncementNo());
    System.out.println(no + "  no 호출!");
    if (announcement.getWriter().getNo() != loginUser.getNo()) {
      throw new Exception("게시글 변경 권한이 없습니다!");
    }
    if (announcementService.deleteAttachedFile(no) == 0) {
      throw new Exception("해당 번호의 첨부파일이 없습니다.");
    } else {
      return "redirect:/announcement/detail?currentPage=" + currentPage + "&no=" + announcement.getNo();
    }
  }

}