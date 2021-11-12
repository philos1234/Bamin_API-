package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.dto.GetOrderListRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService){
        this.orderService = orderService;

    }

    //해당 유저의 id로 주문 내역 조회
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetOrderListRes>> getOrderList(@PathVariable(name="userIdx") int userIdx){

        try{
           List<GetOrderListRes> ret =orderService.getOrderList(userIdx);
           return new BaseResponse<>(ret);
        }
        catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }



}
