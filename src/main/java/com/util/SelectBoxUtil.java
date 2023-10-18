package com.util;

//******************************************************************************************
//
// Description : SelectBox(선택 박스) 처리를 위한 유틸 클래스 
// Reference :
//
//******************************************************************************************    
public class SelectBoxUtil
{
    private String Value;      // 값
    private String Text;       // 내용
    private boolean Selected;  // 선택 유무

    public SelectBoxUtil(String Value, String Text, boolean Selected)
    {
        this.Value = Value;
        this.Text = Text;
        this.Selected = Selected;
    }

    public String getValue() 
    {
        return Value;
    }

    public void setValue(String Value)
    {
        this.Value = Value;
    }   

    public String getText() 
    {
        return Text;
    }

    public void setText(String Text)
    {
        this.Text = Text;
    }   

    public boolean getSelected() 
    {
        return Selected;
    }

    public void setSelected(boolean Selected)
    {
        this.Selected = Selected;

    }   
}