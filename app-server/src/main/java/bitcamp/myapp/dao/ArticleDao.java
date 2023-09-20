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

  List<Article> findAuctionArticlesByDate(String date);

  Article findBy(int articleNo);

  int update(Article article);

  int delete(int articleNo);

  int updateViewCount(int articleNo);

  int bid(@Param("current_price") int currentPrice, @Param("bid_count") int bidCount);
}