package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.store.dto.GetStoreInfoRes;

import com.example.demo.src.store.dto.GetStoreScoreRes;
import com.fasterxml.jackson.databind.util.JSONPObject;
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



    @GetMapping("")
    public BaseResponse<List<GetStoreInfoRes>> getStoreList(@RequestParam(name="last_idx")int last_idx ){

        try {
            List<GetStoreInfoRes> ret = storeService.getStoreList(last_idx);
            return new BaseResponse<>(ret);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @GetMapping("/score/{storeIdx}")
    public BaseResponse<GetStoreScoreRes> getStoreScore(@PathVariable(name="storeIdx") int storeIdx){

        try {

            return new BaseResponse<>(storeService.getStoreScore(storeIdx));

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());

        }
    }


    @GetMapping("/score")
    public BaseResponse<List<GetStoreScoreRes>> getStoreScoreList(){

        try {

            return new BaseResponse<>(storeService.getStoreScoreList());

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());

        }
    }



}
