package bitcamp.myapp.service;

import bitcamp.myapp.vo.Announcement;
import bitcamp.myapp.vo.AnnouncementAttachedFile;
import org.springframework.ui.Model;

import java.util.List;

public interface AnnouncementService {
  int add(Announcement announcement) throws Exception;
  void list(Model model) throws Exception;
  List<Announcement> fixedList() throws Exception;
  Announcement get(int announcementNo) throws Exception;
  int update(Announcement announcement) throws Exception;
  int delete(int announcementNo) throws Exception;
  void setAnnouncementFixed(int announcementNo, int fixed) throws Exception;

  AnnouncementAttachedFile getAnnouncementAttachedFile(int fileNo) throws Exception;
  int deleteAttachedFile(int fileNo) throws Exception;
}

