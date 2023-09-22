package bitcamp.myapp.dao;

import bitcamp.myapp.vo.History;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HistoryDao {
  int insert(History history);
  List<History> findAll(int userNo);
}