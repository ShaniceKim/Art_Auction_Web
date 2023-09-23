package bitcamp.myapp.service;

import bitcamp.myapp.dao.AnnouncementDao;
import bitcamp.myapp.vo.Announcement;
import bitcamp.myapp.vo.AnnouncementAttachedFile;
import bitcamp.myapp.vo.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class DefaultAnnouncementService implements AnnouncementService {

  AnnouncementDao announcementDao;

  public DefaultAnnouncementService(AnnouncementDao announcementDao) {
    this.announcementDao = announcementDao;
  }

  @Transactional // 이 메서드는 트랜잭션 상태에서 실행하라고 지정
  @Override
  public int add(Announcement announcement) throws Exception {
    int count = announcementDao.insert(announcement);
    if (announcement.getAnnouncementAttachedFiles().size() > 0) {
      announcementDao.insertFiles(announcement);
    }
    return count;
  }

  @Override
  public void list(Model model) throws Exception {
    try {
      int currentPage = 0;
      if (model.getAttribute("currentPage") == null) {
        currentPage = 1;
      } else {
        currentPage = (Integer) model.getAttribute("currentPage");
      }
      List<Announcement> list = announcementDao.findAll();
      int size = list.size();
      int pageSize = 10;
      int startPage = (currentPage - 1) * pageSize ;
      int endPage = Math.min(pageSize, size - startPage);
      List<Announcement> subList = list.stream().skip(startPage).limit(endPage).toList();

//      model.addAttribute("fixedList", this.fixedList());
      model.addAttribute("size", size);
      model.addAttribute("list", subList);
      model.addAttribute("currentPage", currentPage);
      model.addAttribute("pageSize", pageSize);

    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public List<Announcement> fixedList() throws Exception {
    return announcementDao.findFixedList();
  }

  @Override
  public Announcement get(int announcementNo) throws Exception {
    return announcementDao.findBy(announcementNo);
  }

  @Transactional
  @Override
  public int update(Announcement announcement) throws Exception {
    int count = announcementDao.update(announcement);
    if (count > 0 && announcement.getAnnouncementAttachedFiles().size() > 0) {
      announcementDao.insertFiles(announcement);
    }
    return count;
  }

  @Transactional
  @Override
  public int delete(int announcementNo) throws Exception {
    announcementDao.deleteFiles(announcementNo);
    return announcementDao.delete(announcementNo);
  }

  @Override
  public void setAnnouncementFixed(int announcementNo, int fixed) throws Exception {
    announcementDao.setAnnouncementFixed(announcementNo, fixed);
  }

  @Override
  public AnnouncementAttachedFile getAnnouncementAttachedFile(int fileNo) throws Exception {
    return announcementDao.findFileBy(fileNo);
  }

  @Override
  public int deleteAttachedFile(int fileNo) throws Exception {
    return announcementDao.deleteFile(fileNo);
  }
}