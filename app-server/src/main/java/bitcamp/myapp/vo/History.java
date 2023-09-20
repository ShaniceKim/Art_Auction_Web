package bitcamp.myapp.vo;

import java.sql.Timestamp;

public class History {
  private int no;
  private User bidder; // 입찰자
  private Article article;
  private int price;
  private Timestamp bidDate;

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public User getBidder() {
    return bidder;
  }

  public void setBidder(User bidder) {
    this.bidder = bidder;
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public Timestamp getBidDate() {
    return bidDate;
  }

  public void setBidDate(Timestamp bidDate) {
    this.bidDate = bidDate;
  }
}