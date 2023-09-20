package bitcamp.myapp.service;

import bitcamp.myapp.dao.AnnouncementDao;
import bitcamp.myapp.vo.Announcement;
import bitcamp.myapp.vo.AnnouncementAttachedFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
  public List<Announcement> list() throws Exception {
    return announcementDao.findAll();
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