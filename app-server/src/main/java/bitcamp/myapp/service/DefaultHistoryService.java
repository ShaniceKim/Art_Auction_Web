package bitcamp.myapp.service;

import bitcamp.myapp.dao.HistoryDao;
import bitcamp.myapp.vo.History;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultHistoryService implements HistoryService{


  HistoryDao historyDao;

  public DefaultHistoryService(HistoryDao historyDao) {
    this.historyDao = historyDao;
  }

  @Override
  public int add(History history) throws Exception {
    return historyDao.insert(history);
  }

  @Override
  public List<History> list(int userNo) throws Exception {
    return historyDao.findAll(userNo);
  }

}