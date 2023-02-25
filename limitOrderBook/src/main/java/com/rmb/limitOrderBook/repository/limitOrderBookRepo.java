package com.rmb.limitOrderBook.repository;

import com.rmb.limitOrderBook.model.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface limitOrderBookRepo extends JpaRepository<OrderBook, Long> {
}
