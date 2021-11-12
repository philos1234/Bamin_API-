package com.example.demo.src.zzim;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.zzim.dto.GetZzimStoreIdRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZzimService {


    private ZzimDao zzimDao;
    @Autowired
    public ZzimService(ZzimDao zzimDao){
        this.zzimDao = zzimDao;
    }


    public List<GetZzimStoreIdRes> getZzimStoreId(int u_idx)throws BaseException {
        try{

            List<GetZzimStoreIdRes> ret =  zzimDao.getStoreId(u_idx);

            return ret;

        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }

    public void createZzim(int u_idx, int s_idx) throws BaseException{
        try{
            int ret = zzimDao.createZzim(u_idx,s_idx);
            if(ret == 0)throw new BaseException(BaseResponseStatus.ZZIM_CREATE_FAIL);

        }catch (Exception exception){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }

    }
}
