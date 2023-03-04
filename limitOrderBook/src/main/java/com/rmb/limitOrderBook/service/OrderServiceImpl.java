package com.rmb.limitOrderBook.service;

import com.rmb.limitOrderBook.exception.ResouceNotFoundException;
import com.rmb.limitOrderBook.model.OrderBook;
import com.rmb.limitOrderBook.model.OrderExecutionRecord;
import com.rmb.limitOrderBook.model.OrderType;
import com.rmb.limitOrderBook.repository.limitOrderBookRepo;
import com.rmb.limitOrderBook.repository.limitOrderExecutionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
@Transactional
public class OrderServiceImpl  implements OrderBookService {

    @Autowired
    private limitOrderBookRepo limitOrderBookRepo;

    @Autowired
    private com.rmb.limitOrderBook.repository.limitOrderExecutionRepo orderExecutionRepo;


    //int priority = 0;

    @Override
    public List<OrderBook> addOrder(OrderBook orderBook) {
        OrderExecutionRecord executionRecord=new OrderExecutionRecord();
        executionRecord.setBidOrderPrice(orderBook.getPrice());
        executionRecord.setQuantity(orderBook.getQuantity());
        executionRecord.setOrderType(orderBook.getOrderType());
                limitOrderBookRepo.save(orderBook);
        orderExecutionRepo.save(executionRecord);
        List<OrderBook> sortedList=limitOrderBookRepo.findAll().stream().sorted(Comparator.comparing(OrderBook::getPrice).reversed().thenComparing(OrderBook::getCreatedTime)).collect(Collectors.toList());

        return sortedList;
    }

    @Override
    public List<OrderBook> updateOrder(OrderBook orderBook) {
        Timestamp timestamp=new Timestamp(System.currentTimeMillis());
        Instant instanceNow3 = timestamp.toInstant();
        Date date = new Date(timestamp.getTime());
        Long id=orderBook.getId();

        Optional<OrderBook> updateOrderDetails= this.limitOrderBookRepo.findById(orderBook.getId());
        Optional<OrderExecutionRecord> updateOrderExecutedDetails= this.orderExecutionRepo.findById(orderBook.getId());

        OrderBook orderBookUpdatedfinal = null;
        OrderExecutionRecord executionRecord=null;

        if (updateOrderDetails.isPresent()) {
           orderBookUpdatedfinal = updateOrderDetails.get();
            orderBookUpdatedfinal.setId(orderBook.getId());
            orderBookUpdatedfinal.setPrice(orderBook.getPrice());
            orderBookUpdatedfinal.setQuantity(orderBook.getQuantity());
            orderBookUpdatedfinal.setCreatedTime(timestamp);
            limitOrderBookRepo.saveAndFlush(orderBookUpdatedfinal);

            executionRecord = updateOrderExecutedDetails.get();
            executionRecord.setId(orderBook.getId());
            executionRecord.setBidOrderPrice(orderBook.getPrice());
            executionRecord.setQuantity(orderBook.getQuantity());
            executionRecord.setCreatedTime(timestamp);
            orderExecutionRepo.saveAndFlush(executionRecord);

        }
        else {
            throw  new ResouceNotFoundException("orderBookRecord not Found");
        }

        List<OrderBook> sortedmodifiedList=limitOrderBookRepo.findAll().stream().sorted(Comparator.comparing(OrderBook::getPrice).reversed().thenComparing(OrderBook::getCreatedTime)).collect(Collectors.toList());



        return sortedmodifiedList;
    }

    @Override
    public List<OrderBook> deleteOrder(OrderBook orderBook) {
        Optional<OrderBook> deleteOrderDetails = this.limitOrderBookRepo.findById(orderBook.getId());
        if (deleteOrderDetails.isPresent()) {
            this.limitOrderBookRepo.delete(deleteOrderDetails.get());
        }
        else {
            throw  new ResouceNotFoundException("orderBookRecord not Found");
        }

        List<OrderBook> sorteddeletedList=limitOrderBookRepo.findAll().stream()
                .sorted(Comparator.comparing(OrderBook::getPrice).reversed()).collect(Collectors.toList());
        return sorteddeletedList;


    }
}

