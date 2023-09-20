//package bitcamp.myapp.dao;
//
//import bitcamp.myapp.vo.AttachedFile;
//import bitcamp.myapp.vo.AuctionAnnouncement;
//
//import java.util.List;
//
//public interface AuctionNoticeDao {
//  int insert(AuctionAnnouncement notice);
//  List<AuctionAnnouncement> findAll();
//  AuctionAnnouncement findBy(int noticeNo);
//  int update(AuctionAnnouncement notice);
//  int updateCount(int noticeNo);
//  int delete(int noticeNo);
//
//  int insertFiles(AuctionAnnouncement notice);
//  AttachedFile findFileBy(int fileNo);
//  int deleteFile(int fileNo);
//  int deleteFiles(int noticeNo);
//}