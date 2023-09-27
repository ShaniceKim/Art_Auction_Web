package bitcamp.myapp.service;

import bitcamp.myapp.vo.Exchange;

import java.util.List;

public interface ExchangeService {
  int add(Exchange exchange) throws Exception;
  List<Exchange> list() throws Exception;
  List<Exchange> listByUserNo(int userNo) throws Exception;
  Exchange get(int exchangeNo) throws Exception;
  int update(Exchange exchange) throws Exception;
  int delete(int exchangeNo) throws Exception;
}