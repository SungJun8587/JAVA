package com.dev.BbsMVC.Dao;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.dev.BbsMVC.Model.BbsInfoBean;

@Mapper
public interface BbsMapper 
{
    long GetBbsCount();
    BbsInfoBean BbsGet(long No);
    List<BbsInfoBean> BbsPagingList(HashMap<String, Object> oMap);
    void BbsProcess(HashMap<String, Object> oMap);
}