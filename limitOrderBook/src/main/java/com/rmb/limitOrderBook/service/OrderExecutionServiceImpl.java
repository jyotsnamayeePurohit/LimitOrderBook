package com.rmb.limitOrderBook.service;

import com.rmb.limitOrderBook.exception.InvalidOrderException;
import com.rmb.limitOrderBook.model.OrderBook;
import com.rmb.limitOrderBook.model.OrderExecutionRecord;

import com.rmb.limitOrderBook.model.OrderStatusType;
import com.rmb.limitOrderBook.repository.limitOrderExecutionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
@Transactional
public class OrderExecutionServiceImpl implements  OrderExecutionService{
    @Autowired
    private  limitOrderExecutionRepo limitOrderExecutionRepo;
    
   
    @Override
    public List<OrderExecutionRecord> getAllExecutedOrder( Integer price, Integer amount,String orderType) {
        OrderExecutionRecord orderExecutionRecord = null;
       List<OrderExecutionRecord> result =limitOrderExecutionRepo.findAll().stream()
               .filter(s->s.getBidOrderPrice()!=0 ||s.getBidOrderPrice().equals(price))
               .filter(p->p.getOrderType()!=null || p.getOrderType().equals(orderType))
               .collect(Collectors.toList());

        // check the price
        if (result.isEmpty()) {
            throw new InvalidOrderException("NO Data present on   this: " + price);
        } else {

// use old for loop cause we need take final variable  for lambda expression
            for(int i=0;i<result.size();i++){
                Integer balanceAmount=result.get(i).getQuantity();
                OrderExecutionRecord orderExecutedRecord =result.get(i);
                if(amount>=balanceAmount){
                    amount=amount-balanceAmount;
                    orderExecutedRecord.setOrderStatusType(OrderStatusType.FULL);
                    orderExecutedRecord.setQuantity(0);
                    limitOrderExecutionRepo.saveAndFlush(orderExecutedRecord);
                }
                else{
                    amount=balanceAmount-amount;
                    orderExecutedRecord.setOrderStatusType(OrderStatusType.PARTIAL);
                    orderExecutedRecord.setQuantity(amount);
                    limitOrderExecutionRepo.saveAndFlush(orderExecutedRecord);

                }


            }

        }
        List<OrderExecutionRecord> sorteddList=limitOrderExecutionRepo.findAll().stream()
                .sorted(Comparator.comparing(OrderExecutionRecord::getBidOrderPrice).reversed()).collect(Collectors.toList());
        return sorteddList;
    }
}
