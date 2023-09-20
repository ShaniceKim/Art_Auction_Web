package bitcamp.myapp.vo;

import java.io.Serializable;

public class AttachedFile implements Serializable {
  private static final long serialVersionUID = 1L;

  int no;
  String originName;
  String filePath;
  int articleNo; // 이부분 최종부분에서 지워야 함

  @Override
  public String toString() {
    return "AttachedFile [no=" + no + ", originName=" + originName + ", filePath=" + filePath
            + ", articleNo=" + articleNo + "]";
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getOriginName() {
    return originName;
  }

  public void setOriginName(String originName) {
    this.originName = originName;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  // 최종부분에 지워야 하는 부분
  // 여기서부터

  public int getArticleNo() {
    return articleNo;
  }

  public void setArticleNo(int articleNo) {
    this.articleNo = articleNo;
  }
}