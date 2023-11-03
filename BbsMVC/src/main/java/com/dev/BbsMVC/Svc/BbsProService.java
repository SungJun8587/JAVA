package com.dev.BbsMVC.Svc;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.BbsMVC.Dao.BbsMapper;
import com.dev.BbsMVC.Model.BbsInfoBean;
import com.dev.BbsMVC.Model.BbsInfoListBean;

@Service
public class BbsProService 
{
    @Autowired
    private BbsMapper BbsMapper;

    public long GetBbsCount() throws Exception
    {
        long lCount = 0;

        lCount = BbsMapper.GetBbsCount();

        return lCount;
    }

    public BbsInfoBean BbsInfoGet(long No) throws Exception
    {
        BbsInfoBean BbsInfo = null;

        BbsInfo = BbsMapper.BbsGet(No);

        return BbsInfo;
    }

    public BbsInfoListBean BbsInfoPagingList(int nCurPage, long lNextStepNo, int nSearchTerm, String strSearchType, String strSearchString, int nPageSize) throws Exception
    {
        long lOutPrevStepNo = 0;
        long lOutNextStepNo = 0;
        long lOutTotBbsCount = 0;

        HashMap<String, Object> oMap = new HashMap<String, Object>();

        oMap.put("CurPage", nCurPage);
        oMap.put("NextStepNo", lNextStepNo);
        oMap.put("SearchTerm", nSearchTerm);
        oMap.put("SearchType", strSearchType);
        oMap.put("SearchString", strSearchString);
        oMap.put("PageSize", nPageSize);

        List<BbsInfoBean> oBbsInfoList = BbsMapper.BbsPagingList(oMap);

        BbsInfoListBean BbsInfos = new BbsInfoListBean();

        if( oMap.get("OutPrevStepNo") != null ) lOutPrevStepNo = (long)oMap.get("OutPrevStepNo");
        if( oMap.get("OutNextStepNo") != null ) lOutNextStepNo = (long)oMap.get("OutNextStepNo");
        if( oMap.get("OutTotBbsCount") != null ) lOutTotBbsCount = (long)oMap.get("OutTotBbsCount");

        BbsInfos.setBbsInfoList(oBbsInfoList);
        BbsInfos.setOutPrevStepNo(lOutPrevStepNo);
        BbsInfos.setOutNextStepNo(lOutNextStepNo);
        BbsInfos.setBbsCount(lOutTotBbsCount);

        return BbsInfos;
    }

    public boolean BbsInfoProcess(String ProMethod, long No, String Subject, String Content, long MbIdx, String Name, String IP, boolean HtmlFlag) throws Exception
    {
        int nRetVal = 0;
        long lOutNo = 0;
        HashMap<String, Object> oMap = new HashMap<String, Object>();

        oMap.put("ProMethod", ProMethod);
        oMap.put("No", No);
        oMap.put("Subject", Subject);
        oMap.put("Content", Content);
        oMap.put("MbIdx", MbIdx);
        oMap.put("Name", Name);
        oMap.put("IP", IP);
        oMap.put("HtmlFlag", HtmlFlag);

        BbsMapper.BbsProcess(oMap);

        if( oMap.get("OutNo") != null ) lOutNo = (long)oMap.get("OutNo");
        if( oMap.get("RetVal") != null ) nRetVal = (int)oMap.get("RetVal");

        if( nRetVal != 0 ) return false;

        return true;
    }
}