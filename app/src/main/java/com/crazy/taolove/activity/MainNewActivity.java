package com.crazy.taolove.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crazy.taolove.R;
import com.crazy.taolove.activity.base.BaseActivity;
import com.crazy.taolove.db.ConversationSqlManager;
import com.crazy.taolove.fragment.ContactsFragment;
import com.crazy.taolove.fragment.FoundNewFragment;
import com.crazy.taolove.fragment.MessageFragment;
import com.crazy.taolove.fragment.MyPersonalFragment;
import com.crazy.taolove.helper.SDKCoreHelper;
import com.crazy.taolove.listener.MessageUnReadListener;
import com.crazy.taolove.manager.AppManager;
import com.umeng.analytics.MobclickAgent;
import com.yuntongxun.ecsdk.ECInitParams;

public class MainNewActivity extends BaseActivity implements MessageUnReadListener.OnMessageUnReadListener{

	private FragmentTabHost mTabHost;
	private int mCurrentTab;

	private static final int REQUEST_PERMISSION = 0;
	private final int REQUEST_LOCATION_PERMISSION = 1000;
	private final int REQUEST_PERMISSION_SETTING = 10001;

	private boolean isSecondAccess = false;
	private boolean isSecondRead = false;

	public final static String CURRENT_TAB = "current_tab";

	private static final TableConfig[] tableConfig = new TableConfig[] {
			new TableConfig(R.string.tab_message, MessageFragment.class,
					R.drawable.tab_message_selector),
			new TableConfig(R.string.tab_contacts, ContactsFragment.class,
					R.drawable.tab_contacts_selector),
			new TableConfig(R.string.tab_found, FoundNewFragment.class,
					R.drawable.tab_secret_friends_selector),
			new TableConfig(R.string.tab_personal, MyPersonalFragment.class,
					R.drawable.tab_more_selector) };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		setupViews();
		setupEvent();
		SDKCoreHelper.init(this, ECInitParams.LoginMode.FORCE_LOGIN);
		updateConversationUnRead();

		AppManager.requestLocationPermission(this);
		requestPermission();
	}

	/**
	 * 请求读写文件夹的权限
	 */
	private void requestPermission() {
		PackageManager pkgManager = getPackageManager();
		// 读写 sd card 权限非常重要, android6.0默认禁止的, 建议初始化之前就弹窗让用户赋予该权限
		boolean sdCardWritePermission =
				pkgManager.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
		if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission) {
			//请求权限
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},
					REQUEST_PERMISSION);
		}

		boolean readPhoneState =
				pkgManager.checkPermission(Manifest.permission.READ_PHONE_STATE, getPackageName()) == PackageManager.PERMISSION_GRANTED;
		if (Build.VERSION.SDK_INT >= 23 && !sdCardWritePermission) {
			//请求权限
			ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.READ_PHONE_STATE},
					REQUEST_PERMISSION);
		}

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);// 必须要调用这句(信鸽推送)
		mCurrentTab = getIntent().getIntExtra(CURRENT_TAB, 0);
		mTabHost.setCurrentTab(mCurrentTab);
	}

	/**
	 * 设置视图
	 */
	private void setupViews() {
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		for (int i = 0; i < tableConfig.length; i++) {
			mTabHost.addTab(
					mTabHost.newTabSpec(getString(tableConfig[i].titleId))
							.setIndicator(getIndicator(i)),
					tableConfig[i].targetClass, null);
		}
		if (Build.VERSION.SDK_INT >= 11) {
			mTabHost.getTabWidget().setShowDividers(
					LinearLayout.SHOW_DIVIDER_NONE);// 设置不显示分割线
		}
		mTabHost.setCurrentTab(mCurrentTab);

	}


	private void setupEvent(){
		MessageUnReadListener.getInstance().setMessageUnReadListener(this);
	}


	private View getIndicator(int index) {
		View view = View.inflate(this, R.layout.tab_indicator_view, null);
		TextView tv = (TextView) view.findViewById(R.id.tab_item);
		ImageView tab_icon = (ImageView) view.findViewById(R.id.tab_icon);
		tab_icon.setImageResource(tableConfig[index].tabImage);
		tv.setText(tableConfig[index].titleId);
		return view;

	}

	/**
	 * 底部导航配置
	 */
	private static class TableConfig {
		final int titleId;
		final Class<?> targetClass;
		final int tabImage;

		TableConfig(int titleId, Class<?> targetClass, int tabImage) {
			this.titleId = titleId;
			this.targetClass = targetClass;
			this.tabImage = tabImage;
		}
	}

	@Override
	public void notifyUnReadChanged(int type) {
		updateConversationUnRead();
	}

	/**
	 * 更新会话未读消息总数
	 */
	private void updateConversationUnRead() {
		View view;
		view = mTabHost.getTabWidget().getChildTabViewAt(0);
		TextView unread_message_num = (TextView) view
				.findViewById(R.id.unread_message_num);

		int total = ConversationSqlManager.getInstance(this)
				.getAnalyticsUnReadConversation();
		unread_message_num.setVisibility(View.GONE);
		if (total > 0) {
			if (total >= 100) {
				unread_message_num.setText(String.valueOf("99+"));
			} else {
				unread_message_num.setText(String.valueOf(total));
			}
			unread_message_num.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
		if (requestCode == REQUEST_PERMISSION) {
			// 拒绝授权
			if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
				// 勾选了不再提示
				if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
//					showOpenLocationDialog();
				} else {
					if (!isSecondRead) {
						showReadPhoneStateDialog();
					}
				}
			}
		} else if (requestCode == REQUEST_LOCATION_PERMISSION) {
			// 拒绝授权
			if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
				// 勾选了不再提示
				if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) &&
						!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
					showOpenLocationDialog();
				} else {
					if (!isSecondAccess) {
						showAccessLocationDialog();
					}
				}
			} else {
			}
		} else {
			super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		}
	}

	private void showOpenLocationDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.open_location);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				Uri uri = Uri.fromParts("package", getPackageName(), null);
				intent.setData(uri);
				startActivityForResult(intent, REQUEST_PERMISSION_SETTING);

			}
		});
		builder.show();
	}


	private void showAccessLocationDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.access_location);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				isSecondAccess = true;
				if (Build.VERSION.SDK_INT >= 23) {
					ActivityCompat.requestPermissions(MainNewActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
							REQUEST_LOCATION_PERMISSION);
				}

			}
		});
		builder.show();
	}

	private void showReadPhoneStateDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.get_read_phone_state);
		builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				isSecondRead = true;
				if (Build.VERSION.SDK_INT >= 23) {
					ActivityCompat.requestPermissions(MainNewActivity.this, new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
							REQUEST_LOCATION_PERMISSION);
				}

			}
		});
		builder.show();
	}


	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart(this.getClass().getName());
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd(this.getClass().getName());
		MobclickAgent.onPause(this);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		/*if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - clickTime) > 2000) {
				ToastUtil.showMessage(R.string.exit_tips);
				clickTime = System.currentTimeMillis();
			} else {
				exitApp();
			}
			return true;
		}*/
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			moveTaskToBack(false);
			mTabHost.setCurrentTab(0);
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_PERMISSION_SETTING) {
		}
	}
}
