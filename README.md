#**1.MVP介绍**

哥看了很多关于介绍MVP构架博客,都觉得新手不容易理解,SO,决定写一篇MVP的博客.

##什么是MVP?

![这里写图片描述](http://img.blog.csdn.net/20161125151533825)

**Model**:**数据**.负责数据的处理和输出.
**VIew**:**视图**.就是UI界面,即Activity.负责返回UI的信息和展示.
**Presenter**:**主持者**.接收View的逻辑业务和Model的数据,并处理它们之间的信息,并管理View的状态.
**IView**:View的接口给予Presenter调用进行UI逻辑的处理与View&Model之间的信息交互.
**IPresenter**:Presenter的接口,给予View调用,当UI的业务来临时,View通过调用Presenter接口完成展示(处理好的逻辑和数据等).
众所周知写程序都想要高内聚低耦合,在Android中使用MVC构架,Model与View之间的交互导致了模块之间过度的耦合,维护和debug比较困难.从上面的图可以看到MVP很好地隔离了Model和View.View与Model之间无耦合性.这是MVP最大的优点.

#2.Example.
我们通过一个简单的用户登入界面进行解析MVP框架.
![这里写图片描述](http://img.blog.csdn.net/20161125155346618)
##(一)Model
###(1)User-实体类
首先写好实体类:两个数据 **账号** 和 **密码**.
```
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
```
##(2)ITOVIew-预留给View的接口
我们指定当Model验证密码后,便需要显示登入成功要将实体类返回到Presenter和在View展现登入失败,登入失败也展现,所以我们先要预留一个接口给View

```
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

```
##(3)Biz-事务(数据处理)类
```
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

```
##(二)View
##(1)IUserLoginView-View的接口
View的接口主要是确定事务.
首先确定View的事务:负责返回用户和密码的数据,显示和隐藏进度条.

```
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

```
###(2)UserLoginActivity
实现定义的接口,供于Precenter调用

```
package com.fengjie.mvptest.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fengjie.mvptest.R;
import com.fengjie.mvptest.flag.Flag;
import com.fengjie.mvptest.presenter.IPresenter;
import com.fengjie.mvptest.presenter.Presenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserLoginActivity extends AppCompatActivity implements IUserLoginView
{
	@BindView ( R.id.id_et_username )
	protected EditText et_username;
	@BindView ( R.id.id_et_password )
	protected EditText et_password;
	@BindView ( R.id.id_pb_loading )
	protected ProgressBar pgb_loading;

	private IPresenter mPresenter = new Presenter(this);

	@Override
	protected void onCreate ( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ButterKnife.bind(this);
	}

	/**
	 * OnClick可以直接绑定点击事件,不必先获取控件
	 * @param view
	 */
	@OnClick(R.id.id_btn_login)
	protected void OnClick(View view) {
		mPresenter.loginDeal();
	}

	@Override
	public String getAccount ()
	{
		return et_username.getText().toString();
	}

	@Override
	public String getPassword ()
	{
		return et_password.getText().toString();
	}

	@Override
	public void setProgressBarVisibility (final int VIEW_FINAL_VALUE)
	{
		pgb_loading.setVisibility(VIEW_FINAL_VALUE);
	}

	@Override
	public void showToastInfo ( Flag flag)
	{
		switch ( flag )
		{
			case ADD_SUCCEED:
				Toast.makeText(this, et_username.getText().toString() + " login success", Toast.LENGTH_SHORT).show();
				break;
			case ADD_FAILED:
				Toast.makeText(this, et_username.getText().toString() + " login failed", Toast.LENGTH_SHORT).show();
				break;
			default:return;
		}

	}



}

```
##(三)Presenter

###(1)IPresenter-Presenter接口
确定View与Model之间的交互事件:就是用户登入处理.

```
package com.fengjie.mvptest.presenter;

/**
 * @author Created by FengJie on 2016/11/25-13:10.
 * @brief
 * @attention
 */

public interface IPresenter
{
	public void loginDeal ();
}

```
###(2)Presenter
**处理过程**

 - 调用View接口->显示进度条
 - 调用Biz->实现将View返回的数据进行处理,同时通过预留的接口间接对View的下一步UI逻辑进行了处理.
逻辑太清晰了~~~~
也就是说Activity的一个UI业务来临时,只需要通过调用Presenter接口对应的一个方法就能实现处理了!!!这也使得我们的Activity不臃肿.
```
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

```
#总结
![这里写图片描述](http://img.blog.csdn.net/20161125151533825)

View与Presenter关系非常紧密, 相互调用, 并且一一对应. Presenter支持单元测试, View被抽象成若干显示接口, 供Presenter调用, 当View的业务来临只需要调用Presenter便能进行处理,View处理Android逻辑, Presenter处理UI逻辑. View和Presenter的接口信息, 放置在一个逻辑清晰的接口类(合同)中. 
简单说View只要返回UI逻辑业务,Model只要处理好数据业务,Presenter只要处理好两者之间的交互并进行展现!

#**如果有帮助,请赞我的GiuHub和博客**
![这里写图片描述](http://img.blog.csdn.net/20161125165108838)

[源码下载:https://github.com/MadJieJie/MVPTest.git](https://github.com/MadJieJie/MVPTest.git)