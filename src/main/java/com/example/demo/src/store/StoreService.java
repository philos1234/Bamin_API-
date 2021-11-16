package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.store.dto.GetStoreInfoRes;
import com.example.demo.src.store.dto.GetStoreScoreRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private StoreDao storeDao;
    @Autowired
    public StoreService(StoreDao storeDao){
        this.storeDao = storeDao;
    }


    public GetStoreInfoRes getStore(int store_idx) throws BaseException {

        try{
            GetStoreInfoRes ret =  storeDao.getStore(store_idx);
            return ret;

        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }

    public List<GetStoreInfoRes> getStoreList(int last_idx) throws BaseException{

        try{
            return storeDao.getStoreList(last_idx);

        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public GetStoreScoreRes getStoreScore(int storeIdx) throws BaseException {

        try{

            return storeDao.getStoreScore(storeIdx);
        }catch (Exception exception){
            throw  new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }




    public List<GetStoreScoreRes> getStoreScoreList() throws BaseException {

        try{
            return storeDao.getStoreScoreList();
        }catch (Exception exception){
            throw  new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }
}
