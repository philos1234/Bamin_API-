package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.order.dto.GetOrderListRes;
import com.example.demo.src.order.dto.PostOrderFoodReq;
import com.example.demo.src.order.dto.PostOrderFoodRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private JwtService jwtService;

    @Autowired
    public OrderController(OrderService orderService, JwtService jwtService){
        this.orderService = orderService;
        this.jwtService = jwtService;

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


    //유저 토큰으로 조회
    @GetMapping("")
    public BaseResponse<List<GetOrderListRes>> getOrderListByToken(){
        try{

            int userIdx =  jwtService.getUserIdx();
            List<GetOrderListRes> ret = orderService.getOrderList(userIdx);
            return new BaseResponse<>(ret);

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }



    //주문하기

    @PostMapping("")
    public BaseResponse<PostOrderFoodRes> createOrder(@RequestBody PostOrderFoodReq postOrderFoodReq){
        try{

            int userIdx = jwtService.getUserIdx();
            int orderIdx = orderService.createOrder(postOrderFoodReq,userIdx);

            return new BaseResponse<>(new PostOrderFoodRes(orderIdx));


        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    //주문취소

    @DeleteMapping("")
    public BaseResponse<String> deleteOrder(@RequestParam(name="orderIdx") int orderIdx){

        try{
            //토큰 유효성 검사
            jwtService.checkToken();
            orderService.deleteOrder(orderIdx);

            return new BaseResponse<>("주문 취소 완료!");

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }



}
