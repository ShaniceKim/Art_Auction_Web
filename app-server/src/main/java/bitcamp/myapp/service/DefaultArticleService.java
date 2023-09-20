package bitcamp.myapp.service;

import bitcamp.myapp.dao.ArticleDao;
import bitcamp.myapp.vo.Article;
import bitcamp.myapp.vo.Status;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultArticleService implements ArticleService {

  ArticleDao articleDao;

  public DefaultArticleService(ArticleDao articleDao) {
    this.articleDao = articleDao;
  }

  @Transactional
  @Override
  public int add(Article article) throws Exception {
    return articleDao.insert(article);
  }

  @Override
  public List<Article> list(Status status) throws Exception {
    return articleDao.findAll(status);
  }

  @Override
  public List<Article> findAuctionArticlesByDate(String date) throws Exception {
    return null;
  }

//  @Override
//  public List<Article> findAuctionArticlesByDate(String date) throws Exception {
//    return ArticleDao.findAuctionArticlesByDate(date);
//  }

  @Override
  public Article get(int articleNo) throws Exception {
    return articleDao.findBy(articleNo);
  }

  @Transactional
  @Override
  public int update(Article article) throws Exception {
    return articleDao.update(article);
  }

  @Transactional
  @Override
  public int delete(int articleNo) throws Exception {
    return articleDao.delete(articleNo);
  }

  @Override
  public int increaseViewCount(int articleNo) throws Exception {
    return articleDao.updateViewCount(articleNo);
  }

  @Transactional
  @Override
  public int bid(int currentPrice, int bidCount) {
    return articleDao.bid(currentPrice, bidCount);
  }
}