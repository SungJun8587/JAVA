package com.dev.BbsMVC.Controller;

import java.util.ArrayList;
import java.util.List;

import com.dev.BbsMVC.Model.BbsInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dev.BbsMVC.Model.BbsInfoListBean;
import com.dev.BbsMVC.Svc.BbsProService;
import com.util.PagingUtil;
import com.util.SelectBoxUtil;

@Controller
public class BbsController
{
    @Autowired
    private BbsProService oBbsProService; 

    @RequestMapping("/bbs/list")
    public ModelAndView list(
            @RequestParam(value = "CurPage", required = false, defaultValue = "1") int nCurPage, 
            @RequestParam(value = "NextStepNo", required = false, defaultValue = "0") long lNextStepNo,
            @RequestParam(value = "SearchType", required = false, defaultValue = "") String strSearchType, 
            @RequestParam(value = "SearchString", required = false, defaultValue = "") String strSearchString
    )
    {
        int     nSearchTerm = 0;
        int     nPageSize = 0;
        int     nPageListSize = 0;
        long    lBbsTotCount = 0;
        long    lOutPrevStepNo = 0;
        long    lOutNextStepNo = 0;
        String  strPaging = "";

        BbsInfoListBean oBbsInfoList = null;

        if( nCurPage > 10 && lNextStepNo < 1 ) nCurPage = 1;

        nPageSize = 15;
        nPageListSize = 10;

        try
        {
            /*
            long BbsNo = 11999999;
            BbsInfoBean bbsInfoBean = oBbsProService.BbsInfoGet(BbsNo);
            long BbsCount = oBbsProService.GetBbsCount();

            String ProMethod = "ADD";
            long No = 0;
            String Subject = "테스트";
            String Content = "테스트 입니다.";
            long MbIdx = 100;
            String Name = "글쓴이 테스터";
            String IP = "127.0.0.1";
            boolean HtmlFlag = false;

            oBbsProService.BbsInfoProcess(ProMethod, No, Subject, Content, MbIdx, Name, IP, HtmlFlag);
            */

            oBbsInfoList = oBbsProService.BbsInfoPagingList(nCurPage, lNextStepNo, nSearchTerm, strSearchType, strSearchString, nPageSize);

            lBbsTotCount = oBbsInfoList.getBbsCount();
            lOutPrevStepNo = oBbsInfoList.getOutPrevStepNo();
            lOutNextStepNo = oBbsInfoList.getOutNextStepNo();
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
        }

        // ******************************************************************************************
        // List 페이징
        // 페이징을 위한 Class 초기화
        //  - nCurPage As int : 현재 페이지
        //  - lBbsTotCount As int : 전체 게시물수
        //  - nPageListSize As int : 나열할 페이지 번호 개수
        //  - nPageSize As int : 1페이지당 보여질 게시물 수
        PagingUtil oPage = new PagingUtil();

        oPage.Init(nCurPage, lBbsTotCount, nPageListSize, nPageSize);

        strPaging = oPage.GetPageList(lOutPrevStepNo, lOutNextStepNo);
        // ******************************************************************************************

        // ******************************************************************************************
        // 검색 타입 설정
        List<SelectBoxUtil> oBbsSearchTypeList = new ArrayList<SelectBoxUtil>();

        SelectBoxUtil oSelectBoxUtil1 = new SelectBoxUtil("f_Name", "글쓴이", strSearchType.equals("f_Name") ? true : false);
        oBbsSearchTypeList.add(oSelectBoxUtil1);

        SelectBoxUtil oSelectBoxUtil2 = new SelectBoxUtil("f_Subject", "제목", strSearchType.equals("f_Subject") ? true : false);
        oBbsSearchTypeList.add(oSelectBoxUtil2);

        SelectBoxUtil oSelectBoxUtil3 = new SelectBoxUtil("f_Content", "내용", strSearchType.equals("f_Content") ? true : false);
        oBbsSearchTypeList.add(oSelectBoxUtil3);
        // ******************************************************************************************

        ModelAndView oModelView = new ModelAndView();

        oModelView.setViewName("/bbs/list");

        if( lBbsTotCount > 0 ) oModelView.addObject("BbsInfoList", oBbsInfoList.getBbsInfoList());

        oModelView.addObject("PagingHtml", strPaging);
        oModelView.addObject("NextStepNo", lNextStepNo);
        oModelView.addObject("SearchOptions", oBbsSearchTypeList);
        oModelView.addObject("SearchString", strSearchString);

        return oModelView;
    }
}