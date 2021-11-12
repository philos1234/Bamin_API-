package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.order.dto.GetOrderListRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class OrderService {

    private OrderDao orderDao;

    @Autowired
    public OrderService(OrderDao orderDao){
        this.orderDao = orderDao;
    }


    public List<GetOrderListRes> getOrderList(int userIdx) throws BaseException {

        try{
            List<GetOrderListRes> ret = orderDao.getOrder(userIdx);
            return ret;

        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }



    }
}
