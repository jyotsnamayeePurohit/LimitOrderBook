package com.rmb.limitOrderBook.service;

import com.rmb.limitOrderBook.model.OrderExecutionRecord;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderExecutionService {
    List<OrderExecutionRecord> getAllExecutedOrder(Integer price,Integer amount,String orderTyp);
}
