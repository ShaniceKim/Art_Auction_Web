package bitcamp.myapp.dao;

import bitcamp.myapp.vo.History;

import java.util.List;

public interface HistoryDao {
  int insert(History history);
  List<History> findAll(int articleNo);
  History findEnd(int articleNo);
}