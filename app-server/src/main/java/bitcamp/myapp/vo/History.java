package bitcamp.myapp.vo;

import java.sql.Timestamp;

public class History {
    private int historyNo;
    private User bidder; // 입찰자
    private Article article;
    private int price;
    private Timestamp bidDate;
    private int winBid;

    @Override
    public String toString() {
        return "History{" +
                "historyNo=" + historyNo +
                ", bidder=" + bidder +
                ", article=" + article +
                ", price=" + price +
                ", bidDate=" + bidDate +
                ", winBid=" + winBid +
                '}';
    }

    public History() {}

    public History(User bidder, Article article, int price) {
        this.bidder = bidder;
        this.article = article;
        this.price = price;
    }

    public int getHistoryNo() {
        return historyNo;
    }

    public void setHistoryNo(int historyNo) {
        this.historyNo = historyNo;
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

    public int getWinBid() {
        return winBid;
    }

    public void setWinBid(int winBid) {
        this.winBid = winBid;
    }
}