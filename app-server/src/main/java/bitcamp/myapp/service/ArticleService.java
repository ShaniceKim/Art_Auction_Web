package bitcamp.myapp.service;

import bitcamp.myapp.vo.Article;
import bitcamp.myapp.vo.Status;
import org.springframework.ui.Model;

import java.util.List;

public interface ArticleService {
  int add(Article article) throws Exception;

  void list(Status status, Model model) throws Exception;

  void search(String artist, Model model) throws Exception;

  List<Article> findAuctionArticlesByDate(String date) throws Exception;

  Article get(int articleNo) throws Exception;

  int update(Article article) throws Exception;

  int delete(int articleNo) throws Exception;

  int increaseViewCount(int articleNo) throws Exception;

  int bid(Article article);

  int buy(Article article);
}