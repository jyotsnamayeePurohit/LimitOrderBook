package com.rmb.limitOrderBook.service;

import com.rmb.limitOrderBook.exception.ResouceNotFoundException;
import com.rmb.limitOrderBook.model.OrderBook;
import com.rmb.limitOrderBook.model.OrderType;
import com.rmb.limitOrderBook.repository.limitOrderBookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
@Service
@Transactional
public class OrderServiceImpl  implements OrderBookService {

    @Autowired
    private limitOrderBookRepo limitOrderBookRepo;

    int priority = 0;

    @Override
    public List<OrderBook> addOrder(OrderBook orderBook) {

        orderBook.setPriority(++priority);
      /*  if(orderBook.getOrderType().equals("BUY")){
            orderBook.setOrderType(OrderType.BUY);
        }
        else{
            orderBook.setOrderType(OrderType.SELL);
            orderBook.setOrderType(OrderType.SELL);
        }*/

        OrderBook resultOrderBook = limitOrderBookRepo.save(orderBook);
        List<OrderBook> sortedList=limitOrderBookRepo.findAll().stream()
                .sorted(Comparator.comparing(OrderBook::getPrice).reversed()).collect(Collectors.toList());
        return sortedList;
    }

    @Override
    public List<OrderBook> updateOrder(OrderBook orderBook) {
        Optional<OrderBook> updateOrderDetails = this.limitOrderBookRepo.findById(orderBook.getId());
        OrderBook pr = limitOrderBookRepo.findAll().stream().max(Comparator.comparing(OrderBook::getPriority)).orElseThrow(NoSuchElementException::new);
        OrderBook orderBookUpdatedfinal = null;
        if (updateOrderDetails.isPresent()) {
            orderBookUpdatedfinal = updateOrderDetails.get();
            orderBookUpdatedfinal.setId(orderBook.getId());
            orderBookUpdatedfinal.setPrice(orderBook.getPrice());
            orderBookUpdatedfinal.setQuantity(orderBook.getQuantity());
            orderBookUpdatedfinal.setPriority(pr.getPriority() + 1);
            limitOrderBookRepo.saveAndFlush(orderBookUpdatedfinal);

        }
        else {
            throw  new ResouceNotFoundException("orderBookRecord not Found");
        }

        List<OrderBook> sortedmodifiedList=limitOrderBookRepo.findAll().stream()
                .sorted(Comparator.comparing(OrderBook::getPrice).reversed()).collect(Collectors.toList());

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

