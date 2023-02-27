package com.rmb.limitOrderBook.controller;

import com.rmb.limitOrderBook.model.OrderBook;
import com.rmb.limitOrderBook.service.OrderBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LimitOrderBookController {

    @Autowired
    private OrderBookService orderBookService;

    @PostMapping("/orderBook/add")
    public ResponseEntity<List<OrderBook>> addOrder(@RequestBody OrderBook orderBook){
        //return ResponseEntity.ok().body(orderBookService.addOrder(orderBook));
        return new ResponseEntity<List<OrderBook>>(orderBookService.addOrder(orderBook),HttpStatus.CREATED);
    }

    @PutMapping ("/orderBook/update")
    public ResponseEntity<List<OrderBook>> updateOrder(@RequestBody OrderBook orderBook){
        return new ResponseEntity<List<OrderBook>>(orderBookService.updateOrder(orderBook),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/orderBook/delete")
    public ResponseEntity<List<OrderBook>> deleteOrder(@RequestBody OrderBook orderBook){
        return ResponseEntity.ok().body(orderBookService.deleteOrder(orderBook));
    }



}
