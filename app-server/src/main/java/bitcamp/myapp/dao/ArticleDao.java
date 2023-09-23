package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Article;
import bitcamp.myapp.vo.Status;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArticleDao {
  int insert(Article article);

  List<Article> findAll(Status status);

  List<Article> findByArtist(String artist);

  List<Article> findAuctionArticlesByDate(String date);

  Article findBy(int articleNo);

  int update(Article article);

  int delete(int articleNo);

  int updateViewCount(int articleNo);

  int updateArticleBidNum(int articleNo);



  int bid(Article article);

  int buy(Article article);

  void updateArticleStatus(@Param("articleNo") int articleNo);

  void updateArticleBidPoint(@Param("articleNo") int articleNo, @Param("bidAmount") int bidAmount);
}