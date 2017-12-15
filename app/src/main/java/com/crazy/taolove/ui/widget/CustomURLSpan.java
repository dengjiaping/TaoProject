package com.crazy.taolove.ui.widget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

import com.crazy.taolove.CSApplication;
import com.crazy.taolove.R;
import com.crazy.taolove.activity.RewardActivity;
import com.crazy.taolove.utils.ToastUtil;

/**
 * 作者：wangyb
 * 时间：2017/3/25 18:48
 * 描述：
 */
public class CustomURLSpan extends URLSpan {

	public CustomURLSpan(String url) {
		super(url);
	}

	@Override
	public void updateDrawState(TextPaint ds) {
		ds.setUnderlineText(true);
		ds.setColor(CSApplication.getInstance().getResources().getColor(R.color.colorPrimary));
	}



	@Override
	public void onClick(View widget) {
	}
}
