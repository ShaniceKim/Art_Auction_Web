package bitcamp.myapp.service;

import bitcamp.myapp.vo.History;

import java.util.List;

public interface HistoryService {
  int add(History history) throws Exception;
  List<History> list(int userNo) throws Exception;
}