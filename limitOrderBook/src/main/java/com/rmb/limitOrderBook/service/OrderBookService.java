package com.rmb.limitOrderBook.service;

import com.rmb.limitOrderBook.model.OrderBook;

import java.util.List;

public interface OrderBookService {
   List<OrderBook> addOrder(OrderBook orderBook);
    List<OrderBook> updateOrder(OrderBook orderBook);
    List<OrderBook> deleteOrder(OrderBook orderBook);



}
