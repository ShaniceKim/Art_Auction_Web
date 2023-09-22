package bitcamp.myapp.vo;

import java.io.Serializable;
import java.util.Date;

public class Exchange implements Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private User user;
  private String title;
  private String content;
  private Date createdDate;
  private int exchangePoint;

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getExchangePoint() {
    return exchangePoint;
  }

  public void setExchangePoint(int exchangePoint) {
    this.exchangePoint = exchangePoint;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
}