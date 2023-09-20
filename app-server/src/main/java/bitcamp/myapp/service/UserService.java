package bitcamp.myapp.service;

import bitcamp.myapp.vo.User;

import java.util.List;

// 비즈니스 로직을 수행하는 객체의 사용 규칙 정의
// 메서드 이름은 업무와 관련된 이름을 사용할 것.
//
public interface UserService {
  int add(User user) throws Exception;
  List<User> list() throws Exception;
  User get(int userNo) throws Exception;
  User get(String email, String password) throws Exception;
  int update(User user) throws Exception;
  int delete(int userNo) throws Exception;

  void updateUserPoints(String userNo, int point);

  void chargeUserPoints(String userNo, int point);
}
