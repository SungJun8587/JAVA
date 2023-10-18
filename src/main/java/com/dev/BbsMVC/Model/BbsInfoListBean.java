package com.dev.BbsMVC.Model;

import java.util.ArrayList;
import java.util.List;

//******************************************************************************************
//
// Description : BbsInfo Class를 List에 저장하기 위한 Class 
// Reference :
//
//******************************************************************************************    
public class BbsInfoListBean
{
    private long BbsCount = 0;
	private long OutPrevStepNo = 0;
	private long OutNextStepNo = 0;

	private List<BbsInfoBean> BbsInfos;
	
	public BbsInfoListBean(){
		BbsInfos = new ArrayList<BbsInfoBean>();
    }

    public void add(BbsInfoBean BbsInfo){
		BbsInfos.add(BbsInfo);
    	BbsCount++;
    }
    
	public List<BbsInfoBean> getBbsInfoList() {
		return BbsInfos;
	}
	public void setBbsInfoList(List<BbsInfoBean> BbsInfos) {
		this.BbsInfos = BbsInfos;
	}

	public long getBbsCount() { return BbsCount; }
	public void setBbsCount(long BbsCount) {
		this.BbsCount = BbsCount;
	}

	public long getOutPrevStepNo() { return OutPrevStepNo; }
	public void setOutPrevStepNo(long OutPrevStepNo) {
		this.OutPrevStepNo = OutPrevStepNo;
	}

	public long getOutNextStepNo() { return OutNextStepNo; }
	public void setOutNextStepNo(long OutNextStepNo) {
		this.OutNextStepNo = OutNextStepNo;
	}
}
