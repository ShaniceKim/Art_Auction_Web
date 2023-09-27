package bitcamp.myapp.vo;

import org.springframework.web.multipart.MultipartFile;

public class AddAnnouncementRequest {
  private int currentPage;
  private String title;
  private String content;
  private MultipartFile[] files;
  private int fixed;

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
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

  public MultipartFile[] getFiles() {
    return files;
  }

  public void setFiles(MultipartFile[] files) {
    this.files = files;
  }

  public int getFixed() {
    return fixed;
  }

  public void setFixed(int fixed) {
    this.fixed = fixed;
  }
}
