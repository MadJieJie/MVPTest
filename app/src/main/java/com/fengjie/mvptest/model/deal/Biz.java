package com.fengjie.mvptest.model.deal;

import com.fengjie.mvptest.model.bean.User;

/**
 * @author Created by FengJie on 2016/11/7-16:11.
 * @brief  对于实体数据和业务逻辑进行处理
 * @attention
 */

public class Biz
{

	public void login ( final String account, final String password, final IToView IToView )
	{
		new Thread()    //开启子线程模拟耗时操作
		{
			@Override
			public void run ()
			{       //open a Thread
				try
				{
					Thread.sleep(2000);     //delay 2 second
				} catch( Exception e )
				{
					e.printStackTrace();        //print Exception
				}

				//Simulate login succeed  situation.
				if ( "123".equals(account) && "123".equals(password) )
				{
					User user = new User();     //instant of class
					user.setAccount(account);
					user.setPassword(password);
					IToView.loginSuccess(user);    //use interface method.
				} else
				{
					IToView.loginFaild();   //use interface method.
				}

			}
		}.start();
	}

}
