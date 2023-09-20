package bitcamp.myapp.vo;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
  private static final long serialVersionUID = 1L;


  private int no;
  private Authority authority;
  private String email;
  private String password;
  private String name;
  private String phone;
  private Integer zonecode; // 카카오 우편번호
  private String address;
  private String detailAddr;
  private Date joinDate;
  private String profilePhoto;
  private Integer point;

  public User(int no) {
    this.no = no;
  }

  public void setZonecode(Integer zonecode) {this.zonecode = zonecode;}

  public void setDetailAddr(String detailAddr) {this.detailAddr = detailAddr;}

  public String getDetailAddr() {return detailAddr;}

  public void setNo(int no) {this.no = no;}

  public void setAuthority(Authority authority) {
    this.authority = authority;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setZonecode(int zonecode) {
    this.zonecode = zonecode;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setJoinDate(Date joinDate) {
    this.joinDate = joinDate;
  }

  public void setProfilePhoto(String profile_photo) {
    this.profilePhoto = profile_photo;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }

  public int getNo() {
    return no;
  }

  public Authority getAuthority() {
    return authority;
  }

  public String getEmail() {
    return email;
  }

  public String getPassword() {
    return password;
  }

  public String getName() {
    return name;
  }

  public String getPhone() {
    return phone;
  }

  public Integer getZonecode() {
    return zonecode;
  }

  public String getAddress() {
    return address;
  }

  public Date getJoinDate() {
    return joinDate;
  }

  public String getProfilePhoto() {
    return profilePhoto;
  }

  public Integer getPoint() {
    return point;
  }



  @Override
  public String toString() {
    return "User{" +
            "no=" + no +
            ", authority=" + authority +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", name='" + name + '\'' +
            ", phone='" + phone + '\'' +
            ", zonecode=" + zonecode +
            ", address='" + address + '\'' +
            ", joinDate=" + joinDate +
            ", profile_photo='" + profilePhoto + '\'' +
            ", point=" + point +
            '}';
  }
}