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
