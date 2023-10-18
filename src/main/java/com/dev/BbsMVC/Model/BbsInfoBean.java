package com.dev.BbsMVC.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//******************************************************************************************
//
// Description : 일반형 게시판 정보를 저장하기 위한 Class 
// Reference :
//
//******************************************************************************************    
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BbsInfoBean
{
    private long No;          	// 일련번호
    private String Subject;     // 제목
    private String Content;     // 내용
    private long MbIdx;         // 글쓴이 일련번호
    private String Name;        // 글쓴이 명
    private String IP;          // 글쓴이 IP
    private boolean HtmlFlag;   // HTML 여부(0/1 : 무/유)
    private int Hit;            // 조회수
    private int Recom;          // 추천수 
    private int UnRecom;        // 비추수
    private int CommentCnt;     // 댓글수 
    private String RegDate;     // 등록 일시
}