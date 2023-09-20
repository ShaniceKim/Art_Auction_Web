package bitcamp.myapp.vo;

import java.io.Serializable;

public class AttachedFile implements Serializable {
  private static final long serialVersionUID = 1L;

  private int no;
  private int fileNo;
  private String filePath;
  int boardNo; // 이부분 최종부분에서 지워야 함

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public int getFileNo() {
    return fileNo;
  }

  public void setFileNo(int fileNo) {
    this.fileNo = fileNo;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

  // 최종부분에 지워야 하는 부분
  // 여기서부터
  public int getBoardNo() {
    return boardNo;
  }

  public void setBoardNo(int boardNo) {
    this.boardNo = boardNo;
  }
  // 여기까지
}