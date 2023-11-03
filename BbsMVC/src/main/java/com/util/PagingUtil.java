package com.util;

public class PagingUtil
{
    private int m_nCurPage;         	// 현재 페이지
    private int m_nCurPageList;     	// 현재 페이지 목록
    private long m_lStartArticle;    	// 출발 목록의 게시물 위치
    private long m_lEndArticle;      	// 끝 목록의 게시물 위치
    private int m_nStartPage;       	// 페이지 목록의 시작 페이지
    private int m_nEndPage;        		// 페이지 목록의 종료 페이지
    private long m_lArticleCount;    	// 전체 게시물 합계
    private int m_nPageCount;      		// 페이지 합계
    private int m_nPageListCount;  		// 페이지 목록 합계
    private int m_nPagePerArticle;  	// 페이지당 보여질 게시물수
    private int m_nPageListSize;    	// 보여질 페이지 번호수

    // 페이지를 계산하는 생성자 
    public PagingUtil()
    {
        m_nCurPage = 0;
        m_nCurPageList = 0;
        m_lStartArticle = 0;
        m_lEndArticle = 0;
        m_nStartPage = 0;
        m_nEndPage = 0;
        m_lArticleCount = 0;
        m_nPageCount = 0;
        m_nPageListCount = 0;
        m_nPagePerArticle = 0;
        m_nPageListSize = 0;
    }

    // ******************************************************************************************
    //
    // Description : 페이징 초기화 함수
    // Parameters
    // 	- [in] int nCurPage : 현재 페이지
    //  - [in] int nTotalArticle : 전체 게시물 수
    //  - [in] int nPageListSize : 보여질 페이지 번호수
    //  - [in] int nPagePerArticle : 페이지당 보여질 게시물수
    // Return Type : void
    // Reference :
    //
    // ******************************************************************************************                
    public void Init(int nCurPage, long nTotalArticle, int nPageListSize, int nPagePerArticle)
    {
        // 입력된 전체 열의 수를 통해 전체 페이지 수를 계산한다 
        m_nPageCount = (int) Math.ceil((double) nTotalArticle / (double) nPagePerArticle);

        if( m_nPageCount < 1 ) m_nPageCount = 1;
        if( nCurPage < 1 ) nCurPage = 1;

        // 현재 페이지가 전체 페이지수보다 클경우 전체 페이지수로 강제로 조정한다 
        if( nCurPage > m_nPageCount )

        nCurPage = m_nPageCount;

        m_nCurPage = nCurPage;
        m_lArticleCount = nTotalArticle;
        m_nPageListSize = nPageListSize;
        m_nPagePerArticle = nPagePerArticle;

        m_nPageListCount = ((m_nPageCount - 1) / m_nPageListSize) + 1;
        m_nCurPageList = ((m_nCurPage - 1) / m_nPageListSize) + 1;
        m_nStartPage = ((m_nCurPageList - 1) * m_nPageListSize) + 1;

        if( m_nStartPage == 0 ) m_nStartPage = 1;

        m_nEndPage = m_nStartPage + m_nPageListSize;

        if( m_nEndPage > m_nPageCount ) m_nEndPage = m_nPageCount + 1;

        m_lStartArticle = (long)((m_nCurPage - 1) * m_nPagePerArticle) + 1;
        m_lEndArticle = m_lStartArticle + m_nPagePerArticle;

        if( m_lEndArticle > m_lArticleCount) m_lEndArticle = m_lArticleCount;
    }

    public void Debug()
    {
        System.out.println("Total Page : " + this.m_nPageCount + " / Start Page : " + this.m_nStartPage + " / End Page : " + this.m_nEndPage);
        System.out.println("Total Row : " + this.m_lArticleCount + " / Start Article : " + this.m_lStartArticle + " / End Article : " + this.m_lEndArticle);
    }

    // 전체 페이지 수를 알아온다 
    public int getPageCount()
    {
        return m_nPageCount;
    }

    // 시작페이지값을 가져온다 
    public int getStartPage()
    {
        return m_nStartPage;
    }

    // 마지막 페이지값을 가져온다 
    public int getEndPage()
    {
        return m_nEndPage;
    }

    public int PrevPage()
    {
        if( m_nCurPageList > 1 ) return((m_nCurPageList - 2) * m_nPageListSize) + 1;

        return 0;
    }

    public int NextPage()
    {
        if( m_nCurPageList < m_nPageListCount ) return(m_nCurPageList * m_nPageListSize) + 1;

        return 0;
    }

    // ******************************************************************************************
    //
    // Description : 페이징 HTML 문자열
    // Parameters
    // 	- [in] String strPageUrl : 클릭시 이동할 URL
    //  - [in] String strImgServer : 이미지 서버 Host명
    // Return Type : String
    // Reference :
    //
    // ******************************************************************************************         
    public String Paging(String strPageUrl, String strImgServer)
    {
        String strPaging = "";

        if( strPageUrl.substring(strPageUrl.length() - 1, strPageUrl.length()) != "?" ) strPageUrl = strPageUrl + "&";

        if( m_nCurPage < 2 )
        {
            strPaging = "<a href=\"javascript:alert('첫목록 입니다.')\" onfocus=\"blur();\"><img src=\"" + strImgServer + "/img/news/btn_navpage_01.gif\" alt=\"첫목록\"  /></a>";
            strPaging = strPaging + "<a href=\"javascript:alert('첫목록 입니다.')\" onfocus=\"blur();\"><img src=\"" + strImgServer + "/img/news/btn_navpage_02.gif\" alt=\"이전목록\"  /></a>";
        }
        else
        {
            strPaging = "<a href='" + strPageUrl + "curpage=1' onfocus=\"blur();\"><img src=\"" + strImgServer + "/img/news/btn_navpage_01.gif\" alt=\"첫목록\"  /></a>";
            strPaging = strPaging + "<a href='" + strPageUrl + "curpage=" + (m_nCurPage - 1) + "' onfocus=\"blur();\"><img src=\"" + strImgServer + "/img/news/btn_navpage_02.gif\" alt=\"이전목록\"  /><lt;/a>";
        }

        strPaging = strPaging + "<span class=\"tb_navlist\">";

        for( int i = m_nStartPage; i < m_nEndPage; i++ )
        {
            if( i % m_nPageListSize == 1 )
            {
                if( i == m_nCurPage )
                    strPaging = strPaging + "<a href='" + strPageUrl + "curpage=" + i + "'><font style='color:#A31F00'>" + i + "</font></a>";
                else strPaging = strPaging + "<a href='" + strPageUrl + "curpage=" + i + "'>" + i + "</a>";
            }
            else
            {
                if( i == m_nCurPage )
                    strPaging = strPaging + "<a href='" + strPageUrl + "curpage=" + i + "'><font style='color:#A31F00'>" + i + "</font></a>";
                else strPaging = strPaging + "<a href='" + strPageUrl + "curpage=" + i + "'>" + i + "</a>";
            }
        }

        strPaging = strPaging + "</span>";

        if( NextPage() > 0 )
        {
            if( m_nPageCount - m_nStartPage < m_nPageListSize )
                strPaging = strPaging + "<a href=\"javascript:alert('끝목록 입니다.')\" onfocus=\"blur();\"><img src=\"" + strImgServer + "/img/news/btn_navpage_03.gif\" alt=\"다음목록\"  /></a>";
            else strPaging = strPaging + "<a href='" + strPageUrl + "curpage=" + NextPage() + "' onfocus=\"blur();\"><img src=\"" + strImgServer + "/img/news/btn_navpage_03.gif\" alt=\"다음목록\"  /></a>";
        }
        else strPaging = strPaging + "<a href=\"javascript:alert('끝목록 입니다.')\" onfocus=\"blur();\"><img src=\"" + strImgServer + "/img/news/btn_navpage_03.gif\" alt=\"다음목록\"  /></a>";

        return strPaging;
    }

    // ******************************************************************************************
    //
    // Date :
    // Description : 클러스터드 인덱스가 걸려 있는 [글번호] 필드 이용하면서 사용자 편의성도 고려한 페이징
    // Parameters
    // - [in] int nPrevStepNo : 이전버튼 최상위글 일련번호
    // - [in] int nNextStepNo : 이후버튼 최상위글 일련번호
    // Return Type : String
    // Reference :
    //
    // ******************************************************************************************
    public String GetPageList(long nPrevStepNo, long nNextStepNo)
    {
        String strPaging = "";

        if( PrevPage() < 1 )
        {
            strPaging = strPaging + "<li class=\"disabled\" title=\"이전\" alt=\"이전\"><a href=\"javascript:alert('첫목록 입니다.')\">&laquo;</a></li>";
        }
        else
        {
            strPaging = strPaging + "<li title=\"이전\" alt=\"이전\"><a href=\"javascript:GoPage('" + PrevPage() + "', '" + nPrevStepNo + "')\">&laquo;</a></li>";
        }

        for( int i = m_nStartPage; i < m_nEndPage; i++ )
        {
            if( i % m_nPageListSize == 1 )
            {
                if( i == m_nCurPage )
                    strPaging = strPaging + "<li class=\"active\"><a href=\"javascript:GoPage('" + i + "')\">" + i + "</a></li>";
                else
                    strPaging = strPaging + "<li><a href=\"javascript:GoPage('" + i + "')\">" + i + "</a></li>";
            }
            else
            {
                if( i == m_nCurPage )
                    strPaging = strPaging + "<li class=\"active\"><a href=\"javascript:GoPage('" + i + "')\">" + i + "</a></li>";
                else
                    strPaging = strPaging + "<li><a href=\"javascript:GoPage('" + i + "')\">" + i + "</a></li>";
            }
        }

        if( NextPage() > 0 )
        {
            if( m_nPageCount - m_nStartPage < m_nPageListSize )
            {
                strPaging = strPaging + "<li class=\"disabled\" title=\"다음\" alt=\"다음\"><a href=\"javascript:alert('끝목록 입니다.')\">&raquo;</a></li>";
            }
            else
            {
                strPaging = strPaging + "<li title=\"다음\" alt=\"다음\"><a href=\"javascript:GoPage('" + NextPage() + "', '" + nNextStepNo + "')\">&raquo;</a></li>";
            }
        }
        else
        {
            strPaging = strPaging + "<li class=\"disabled\" title=\"다음\" alt=\"다음\"><a href=\"javascript:alert('끝목록 입니다.')\">&raquo;</a></li>";
        }

        return strPaging;
    }
}

