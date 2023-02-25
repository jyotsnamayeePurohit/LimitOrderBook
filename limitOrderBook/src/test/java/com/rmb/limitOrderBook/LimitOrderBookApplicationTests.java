package com.rmb.limitOrderBook;

import com.rmb.limitOrderBook.exception.ResouceNotFoundException;
import com.rmb.limitOrderBook.model.OrderBook;
import com.rmb.limitOrderBook.model.OrderType;
import com.rmb.limitOrderBook.service.OrderBookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LimitOrderBookApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private com.rmb.limitOrderBook.repository.limitOrderBookRepo limitOrderBookRepo;
	@Autowired
	OrderBookService orderBookService;


	@Test
	@Order(1)
	@Rollback(value=false)
	public void testAddLimitOrder() {
		OrderBook orderBook = new OrderBook();
		orderBook.setId(1L);
		orderBook.setQuantity(57);
		orderBook.setPrice(6);
		orderBook.setPriority(1);
		orderBook.setOrderType(OrderType.BUY);
		OrderBook orderBook1 = new OrderBook();
		orderBook1.setId(2L);
		orderBook1.setQuantity(56);
		orderBook1.setPrice(9);
		orderBook1.setPriority(2);
		orderBook1.setOrderType(OrderType.SELL);
		OrderBook orderBook2 = new OrderBook();
		orderBook2.setId(3L);
		orderBook2.setQuantity(89);
		orderBook2.setPrice(7);
		orderBook2.setPriority(3);
		orderBook2.setOrderType(OrderType.BUY);
		limitOrderBookRepo.save(orderBook);
		limitOrderBookRepo.save(orderBook1);
		limitOrderBookRepo.save(orderBook2);
		List<OrderBook> o=limitOrderBookRepo.findAll();

		Assertions.assertThat(o.size()).isGreaterThan(0);

	}


	@Test
	@Order(2)
	@Rollback(value=false)
	public void testUpdateLimitOrder() {
		OrderBook od =limitOrderBookRepo.findById(1L).get();
		//OrderBook od=o.get(1);
		od.setPrice(8);
		OrderBook pr = limitOrderBookRepo.findAll().stream().max(Comparator.comparing(OrderBook::getPriority)).orElseThrow(NoSuchElementException::new);
		od.setPriority(pr.getPriority()+1);
		limitOrderBookRepo.save(od);
		List<OrderBook> ob=limitOrderBookRepo.findAll();
		Assertions.assertThat(ob.size()).isGreaterThan(0);

	}


	@Test
	@Order(3)
	@Rollback(value=false)
	public void testDeleteLimitOrder() {
		int size=limitOrderBookRepo.findAll().size();
		OrderBook od=null;
		if(size>0){
			od=limitOrderBookRepo.findById(1L).get();
			limitOrderBookRepo.delete(od);
		}
		List<OrderBook> p=limitOrderBookRepo.findAll();

	}
}
