package bitcamp.myapp.service;

import bitcamp.myapp.dao.UserDao;
import bitcamp.myapp.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultUserService implements UserService {
  {
    System.out.println("DefaultUserService 생성됨!");
  }

  UserDao userDao;

  public DefaultUserService(UserDao userDao) {
    this.userDao = userDao;
  }

  @Transactional
  @Override
  public int add(User member) throws Exception {
    return userDao.insert(member);
  }

  @Override
  public List<User> list() throws Exception {
    return userDao.findAll();
  }

  @Override
  public User get(int memberNo) throws Exception {
    return userDao.findBy(memberNo);
  }

  @Override
  public User get(String email, String password) throws Exception {
    return userDao.findByEmailAndPassword(email, password);
  }

  @Transactional
  @Override
  public int update(User member) throws Exception {
    return userDao.update(member);
  }

  @Transactional
  @Override
  public int delete(int memberNo) throws Exception {
    return userDao.delete(memberNo);
  }

  @Override
  public void updateUserPoints(String userNo, int points) {
    userDao.updatePoints(userNo, points);
  }

  @Override
  public void chargeUserPoints(String userNo, int points) {
    userDao.chargePoints(userNo, points);
  }
}