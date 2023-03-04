package com.rmb.limitOrderBook.repository;

import com.rmb.limitOrderBook.model.OrderBook;
import com.rmb.limitOrderBook.model.OrderExecutionRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface limitOrderExecutionRepo extends JpaRepository<OrderExecutionRecord, Long> {
}
