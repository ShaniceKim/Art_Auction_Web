package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Announcement;
import bitcamp.myapp.vo.AnnouncementAttachedFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnnouncementDao {
  int insert(Announcement announcement);
  List<Announcement> findAll();
  Announcement findBy(int no);
  int update(Announcement announcement);
  int delete(int no);

  int insertFiles(Announcement announcement);
  AnnouncementAttachedFile findFileBy(int no);
  int deleteFile(int fileNo);
  int deleteFiles(int announcementNo);

  int setAnnouncementFixed(@Param("announcementNo") int announcementNo, @Param("fixed") int fixed);
}