package bitcamp.myapp.dao;

import bitcamp.myapp.vo.Exchange;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExchangeDao {
  int insert(Exchange exchange);
  List<Exchange> findAll();
  List<Exchange> findByUserNo(int userNo) throws Exception;
  Exchange findBy(int no);
  int update(Exchange exchange);
  int delete(int no);
}