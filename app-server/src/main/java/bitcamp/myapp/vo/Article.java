package bitcamp.myapp.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

public class Article implements Serializable {
  private static final long serialVersionUID = 1L;

  private int articleNo;
  private User writer;
  private String title;
  private String content;
  private String artist;
  private int viewCount;
  private Date createdDate;
  private String photo;
  private Timestamp startDate;
  private Timestamp endDate;
  private Status status;
  private int curPrice;
  private int endPrice;
  private int bidCount;

  @Override
  public String toString() {
    return "Article{" +
            "articleNo=" + articleNo +
            ", writer=" + writer +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", artist='" + artist + '\'' +
            ", viewCount=" + viewCount +
            ", createdDate=" + createdDate +
            ", photo='" + photo + '\'' +
            ", startDate=" + startDate +
            ", endDate=" + endDate +
            ", status='" + status + '\'' +
            ", curPrice=" + curPrice +
            ", endPrice=" + endPrice +
            ", bidCount=" + bidCount +
            '}';
  }

  public int getArticleNo() {
    return articleNo;
  }

  public void setArticleNo(int articleNo) {
    this.articleNo = articleNo;
  }

  public User getWriter() {
    return writer;
  }

  public void setWriter(User writer) {
    this.writer = writer;
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

  public String getArtist() {
    return artist;
  }

  public void setArtist(String artist) {
    this.artist = artist;
  }

  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  public Date getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public Timestamp getStartDate() {
    return startDate;
  }

  public void setStartDate(Timestamp startDate) {
    this.startDate = startDate;
  }

  public Timestamp getEndDate() {
    return endDate;
  }

  public void setEndDate(Timestamp endDate) {
    this.endDate = endDate;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public int getCurPrice() {
    return curPrice;
  }

  public void setCurPrice(int curPrice) {
    this.curPrice = curPrice;
  }

  public int getEndPrice() {
    return endPrice;
  }

  public void setEndPrice(int endPrice) {
    this.endPrice = endPrice;
  }

  public int getBidCount() {
    return bidCount;
  }

  public void setBidCount(int bidCount) {
    this.bidCount = bidCount;
  }
}