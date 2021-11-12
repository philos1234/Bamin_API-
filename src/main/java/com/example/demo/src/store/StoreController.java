package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.store.dto.GetStoreInfoRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/store")
public class StoreController {

    private StoreService storeService;
    @Autowired
    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    @GetMapping("/{storeIdx}")
    public BaseResponse<GetStoreInfoRes> getStore(@PathVariable(name="storeIdx") int s_idx){

        try{
            GetStoreInfoRes ret =storeService.getStore(s_idx);
            return new BaseResponse<>(ret);

        }catch(BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }
}
