package com.example.demo.src.zzim;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.store.StoreService;
import com.example.demo.src.store.dto.GetStoreInfoRes;
import com.example.demo.src.zzim.dto.GetZzimReq;
import com.example.demo.src.zzim.dto.GetZzimStoreIdRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/zzim")
public class ZzimController {

    private ZzimService zzimService;
    private StoreService storeService;
    @Autowired
    public ZzimController(ZzimService zzimService, StoreService storeService){
        this.zzimService = zzimService;
        this.storeService = storeService;
    }

    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetStoreInfoRes>> getZzim(@PathVariable(name="userIdx") int u_idx){

        try{
            List<GetZzimStoreIdRes> ret =  zzimService.getZzimStoreId(u_idx);
            List<GetStoreInfoRes> storeInfo = new ArrayList<>();
            for(GetZzimStoreIdRes s : ret){
                GetStoreInfoRes t =storeService.getStore(s.getStoreId());
                storeInfo.add(t);
            }

            return new BaseResponse<>(storeInfo);

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());

        }
    }

    //스토어 찜하기
    @GetMapping("/{userIdx}/{storeId}")
    public BaseResponse<String> zzimStore(@PathVariable(name="userIdx") int u_idx,@PathVariable(name="storeId") int s_idx){
        try{
            zzimService.createZzim(u_idx,s_idx);

            return new BaseResponse<>("찜하기 완료!");
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }

    }


}
