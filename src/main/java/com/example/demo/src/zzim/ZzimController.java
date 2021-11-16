package com.example.demo.src.zzim;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.store.StoreService;
import com.example.demo.src.store.dto.GetStoreInfoRes;
import com.example.demo.src.zzim.dto.GetManyZzimStoreRes;
import com.example.demo.src.zzim.dto.GetZzimStoreIdRes;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/zzim")
public class ZzimController {

    private ZzimService zzimService;
    private StoreService storeService;
    private JwtService jwtService;
    @Autowired
    public ZzimController(ZzimService zzimService, StoreService storeService, JwtService jwtService){
        this.jwtService = jwtService;
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


    @PatchMapping("/{userIdx}/{storeId}")
    public BaseResponse<String> delZzim(@PathVariable(name="userIdx") int u_idx, @PathVariable(name="storeId") int s_idx){

        try{

            if(u_idx != jwtService.getUserIdx())throw new BaseException(BaseResponseStatus.INVALID_JWT); //토큰 유효성확인
            zzimService.delZzim(u_idx,s_idx);
            return new BaseResponse<>("찜 삭제 완료!");

        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }


    @GetMapping("/best")
    public BaseResponse<List<GetManyZzimStoreRes>> GetmanyZzimStore(){

        try{
           List<GetManyZzimStoreRes> getManyZzimStoreRes = zzimService.getManyZzimStore();
           return new BaseResponse<>(getManyZzimStoreRes);
        }catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
