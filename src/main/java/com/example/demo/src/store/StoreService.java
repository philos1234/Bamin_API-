package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.store.dto.GetStoreInfoRes;
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
}
