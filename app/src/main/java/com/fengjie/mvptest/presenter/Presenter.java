package com.fengjie.mvptest.presenter;

import android.os.Handler;
import android.view.View;

import com.fengjie.mvptest.flag.Flag;
import com.fengjie.mvptest.model.bean.User;
import com.fengjie.mvptest.model.deal.Biz;
import com.fengjie.mvptest.model.deal.IToView;
import com.fengjie.mvptest.view.IUserLoginView;

/**
 * @author Created by FengJie on 2016/11/7-16:07.
 * @brief   负责完成View与Model间的交互
 * @attention
 */

public class Presenter implements IPresenter
{
	private IUserLoginView mIUserLoginView;     //view interface
	private Biz mBiz;                 //model class
	private Handler mHandler = new Handler();   //begin thread

	public Presenter ( IUserLoginView IUserLoginView )
	{
		mIUserLoginView = IUserLoginView;
		mBiz = new Biz();              //instance Biz class
	}

	@Override
	public void loginDeal ()
	{
		mIUserLoginView.setProgressBarVisibility(View.VISIBLE);     //show the bar.
		mBiz.login(mIUserLoginView.getAccount(), mIUserLoginView.getPassword(), new IToView(){   /**achieve interface*/
			/**login succeed**/
			@Override
			public void loginSuccess ( final User user )
			{
				mHandler.post(new Runnable()
				{
					@Override
					public void run ()
					{
						mIUserLoginView.showToastInfo(Flag.ADD_SUCCEED);    //give flag
						mIUserLoginView.setProgressBarVisibility(View.GONE);                      //hide ProgressBar
					}
				});
			}
			/**login failed**/
			@Override
			public void loginFaild ()
			{
				mHandler.post(new Runnable()
				{
					@Override
					public void run ()
					{
						mIUserLoginView.showToastInfo(Flag.ADD_FAILED);
						mIUserLoginView.setProgressBarVisibility(View.GONE);
					}
				});
			}
		});
	}


}
