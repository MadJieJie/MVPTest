package com.fengjie.mvptest.model.bean;

/**
 * @author Created by FengJie on 2016/11/7-16:07.
 * @brief  用户实体类
 * @attention
 */

public class User
{
	private String account;
	private String password;

	public String getAccount ()
	{
		return account;
	}

	public void setAccount ( String account )
	{
		this.account = account;
	}

	public String getPassword ()
	{
		return password;
	}

	public void setPassword ( String password )
	{
		this.password = password;
	}
}
