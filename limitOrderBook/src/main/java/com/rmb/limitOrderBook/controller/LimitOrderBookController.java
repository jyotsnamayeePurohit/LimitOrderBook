package com.rmb.limitOrderBook.controller;

import com.rmb.limitOrderBook.model.OrderBook;
import com.rmb.limitOrderBook.model.OrderExecutionRecord;
import com.rmb.limitOrderBook.service.OrderBookService;
import com.rmb.limitOrderBook.service.OrderExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderBook")
public class LimitOrderBookController {

    @Autowired
    private OrderBookService orderBookService;

   @Autowired
    private OrderExecutionService orderexecutionService;

    @PostMapping("/add")
    public ResponseEntity<List<OrderBook>> addOrder(@RequestBody OrderBook orderBook){
        //return ResponseEntity.ok().body(orderBookService.addOrder(orderBook));
        return new ResponseEntity<List<OrderBook>>(orderBookService.addOrder(orderBook),HttpStatus.CREATED);
    }

    @PutMapping ("/update")
    public ResponseEntity<List<OrderBook>> updateOrder(@RequestBody OrderBook orderBook){
        return new ResponseEntity<List<OrderBook>>(orderBookService.updateOrder(orderBook),HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<OrderBook>> deleteOrder(@RequestBody OrderBook orderBook){
        return ResponseEntity.ok().body(orderBookService.deleteOrder(orderBook));
    }

@GetMapping("/executionedRecords")

public ResponseEntity<List<OrderExecutionRecord>> getExecutedOrderList(@RequestParam(value="price") Integer price, @RequestParam(value="amount") Integer amount,@RequestParam(value="orderType") String orderType){
    return ResponseEntity.ok().body(orderexecutionService.getAllExecutedOrder(price,amount,orderType));
}


}
