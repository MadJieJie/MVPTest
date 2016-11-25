package com.fengjie.mvptest.view;

import com.fengjie.mvptest.flag.Flag;

/**
 * @author Created by FengJie on 2016/11/7-16:10.
 * @brief View数据交互-获得用户名和密码 显示和隐藏进度条
 * @attention
 */
public interface IUserLoginView
{
	public String getAccount ();

	public String getPassword ();

	public void setProgressBarVisibility ( final int VIEW_FLAG );

	public void showToastInfo ( Flag flag);

}
