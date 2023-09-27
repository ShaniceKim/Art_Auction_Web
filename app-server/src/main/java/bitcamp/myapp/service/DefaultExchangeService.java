package bitcamp.myapp.service;

import bitcamp.myapp.dao.ExchangeDao;
import bitcamp.myapp.vo.Exchange;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DefaultExchangeService implements ExchangeService{

  ExchangeDao exchangeDao;

  public DefaultExchangeService(ExchangeDao exchangeDao) {
    this.exchangeDao = exchangeDao;
  }

  @Transactional // 이 메서드는 트랜잭션 상태에서 실행하라고 지정
  @Override
  public int add(Exchange exchange) throws Exception {
    return exchangeDao.insert(exchange);
  }

  @Override
  public List<Exchange> list() throws Exception {
    return exchangeDao.findAll();
  }

  @Override
  public List<Exchange> listByUserNo(int userNo) throws Exception {
    return exchangeDao.findByUserNo(userNo);
  }

  @Override
  public Exchange get(int exchangeNo) throws Exception {
    return exchangeDao.findBy(exchangeNo);
  }

  @Transactional
  @Override
  public int update(Exchange exchange) throws Exception {
    return exchangeDao.update(exchange);
  }

  @Transactional
  @Override
  public int delete(int exchangeNo) throws Exception {
    return exchangeDao.delete(exchangeNo);
  }
}