package com.fengjie.mvptest.model.deal;


import com.fengjie.mvptest.model.bean.User;

/**
 * @author Created by FengJie on 2016/11/7-16:10.
 * @brief   预留接口给View
 * @attention
 */

public interface IToView
{
	public void loginSuccess( User user );  //登入成功方法
	public void loginFaild();   //登入失败方法
}
