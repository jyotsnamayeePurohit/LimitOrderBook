package com.rmb.limitOrderBook;

import com.rmb.limitOrderBook.exception.ResouceNotFoundException;
import com.rmb.limitOrderBook.model.OrderBook;
import com.rmb.limitOrderBook.model.OrderExecutionRecord;
import com.rmb.limitOrderBook.model.OrderType;
import com.rmb.limitOrderBook.repository.limitOrderExecutionRepo;
import com.rmb.limitOrderBook.service.OrderBookService;
import com.rmb.limitOrderBook.service.OrderExecutionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.annotation.Rollback;

import java.sql.Timestamp;
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

	@Autowired
	private limitOrderExecutionRepo orderExecutionRepo;
	@Autowired
	OrderExecutionService executionService;


	@Test
	@Order(1)
	@Rollback(value=false)
	public void testAddLimitOrder() {
		Timestamp timestamp=new Timestamp(System.currentTimeMillis());
		OrderBook orderBook = new OrderBook();
		orderBook.setId(1L);
		orderBook.setQuantity(40);
		orderBook.setPrice(9);
		orderBook.setCreatedTime(timestamp);
		orderBook.setOrderType(OrderType.BUY);
		OrderExecutionRecord executionRecord=new OrderExecutionRecord();
		executionRecord.setId(1L);
		executionRecord.setBidOrderPrice(orderBook.getPrice());
		executionRecord.setQuantity(orderBook.getQuantity());
		executionRecord.setOrderType(orderBook.getOrderType());
		executionRecord.setCreatedTime(timestamp);
		OrderBook orderBook1 = new OrderBook();
		orderBook1.setId(2L);
		orderBook1.setQuantity(20);
		orderBook1.setPrice(9);
		orderBook1.setCreatedTime(timestamp);
		orderBook1.setOrderType(OrderType.BUY);
		OrderExecutionRecord executionRecord1=new OrderExecutionRecord();
		executionRecord1.setId(2L);
		executionRecord1.setBidOrderPrice(orderBook1.getPrice());
		executionRecord1.setQuantity(orderBook1.getQuantity());
		executionRecord1.setOrderType(orderBook1.getOrderType());
		executionRecord1.setCreatedTime(timestamp);
		OrderBook orderBook2 = new OrderBook();
		orderBook2.setId(3L);
		orderBook2.setQuantity(30);
		orderBook2.setPrice(8);
		orderBook2.setCreatedTime(timestamp);
		//orderBook2.setPriority(3);
		orderBook2.setOrderType(OrderType.BUY);
		OrderExecutionRecord executionRecord2=new OrderExecutionRecord();
		executionRecord2.setId(3L);
		executionRecord2.setBidOrderPrice(orderBook2.getPrice());
		executionRecord2.setQuantity(orderBook2.getQuantity());
		executionRecord2.setOrderType(orderBook2.getOrderType());
		executionRecord2.setCreatedTime(timestamp);

		OrderBook orderBook3= new OrderBook();
		orderBook3.setId(4L);
		orderBook3.setQuantity(20);
		orderBook3.setPrice(8);
		orderBook3.setCreatedTime(timestamp);
		orderBook3.setOrderType(OrderType.BUY);
		OrderExecutionRecord executionRecord3=new OrderExecutionRecord();
		executionRecord3.setId(4L);
		executionRecord3.setBidOrderPrice(orderBook3.getPrice());
		executionRecord3.setQuantity(orderBook3.getQuantity());
		executionRecord3.setOrderType(orderBook3.getOrderType());
		executionRecord3.setCreatedTime(timestamp);

		limitOrderBookRepo.save(orderBook);
		limitOrderBookRepo.save(orderBook1);
		limitOrderBookRepo.save(orderBook2);
		limitOrderBookRepo.save(orderBook3);
		orderExecutionRepo.save(executionRecord);
		orderExecutionRepo.save(executionRecord1);
		orderExecutionRepo.save(executionRecord2);
		orderExecutionRepo.save(executionRecord3);
		List<OrderBook> o=limitOrderBookRepo.findAll();

		Assertions.assertThat(o.size()).isGreaterThan(0);

	}


	@Test
	@Order(2)
	@Rollback(value=false)
	public void testUpdateLimitOrder() {
		OrderBook od =limitOrderBookRepo.findById(1L).get();
		//OrderBook od=o.get(1);
		od.setPrice(10);
		//OrderBook pr = limitOrderBookRepo.findAll().stream().max(Comparator.comparing(OrderBook::getCreatedTime)).orElseThrow(NoSuchElementException::new);
		//od.setPriority(pr.getPriority()+1);
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

	@Test
	@Order(4)
	@Rollback(value=false)
	public void testExecutedLimitOrder() {
		List<OrderExecutionRecord> result=executionService.getAllExecutedOrder(9,55,"BUY");
		Assertions.assertThat(result.size()).isGreaterThan(0);

	}

}
