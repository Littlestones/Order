package org.csii.yeeframe.extend;

import org.csii.yeeframe.R;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class YeeDialog extends Dialog implements android.view.View.OnClickListener {

	public TextView content;
	public Button confirm;
	public Button cancel;

	private CharSequence msg;
	private CharSequence btnMsg;
	private int type = Constants.DIALOG_CONFRIM;
	Context context;

	/**
	 * 
	 * @param context
	 *            包含确定 取消普通对话框
	 */
	public YeeDialog(Context context) {
		super(context, R.style.Translucent_NoTitle);
		this.context = context;
	}

	/**
	 * 
	 * @param context
	 * @param theme
	 *            theme风格对话框
	 */
	public YeeDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	/**
	 * 
	 * @param context
	 * @param msg
	 *            包含确定 取消 显示smg消息对话框
	 * 
	 */
	public YeeDialog(Context context, CharSequence msg) {
		super(context, R.style.Translucent_NoTitle);
		this.context = context;
		this.msg = msg;
	}

	/**
	 * 
	 * @param context
	 * @param msg
	 * @param type
	 *            type= 1 只有一个确定按钮 点击消失对话框
	 */
	public YeeDialog(Context context, CharSequence msg, int type) {
		super(context, R.style.Translucent_NoTitle);
		this.context = context;
		this.msg = msg;
		this.type = type;
	}

	/**
	 * 
	 * @param context
	 * @param msg
	 * @param type
	 *            type= 1 只有一个带btnMsg文字的按钮 点击消失对话框
	 */
	public YeeDialog(Context context, CharSequence msg, CharSequence btnMsg, int type) {
		super(context, R.style.Translucent_NoTitle);
		this.context = context;
		this.msg = msg;
		this.type = type;
		this.btnMsg = btnMsg;
	}

	/**
	 * 
	 * @param context
	 * @param msg
	 * @param type
	 *            带两个按钮 显示msg 确定按钮显示btnMsg
	 */
	public YeeDialog(Context context, CharSequence msg, CharSequence btnMsg) {
		super(context, R.style.Translucent_NoTitle);
		this.context = context;
		this.msg = msg;
		this.btnMsg = btnMsg;
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_default);
		bindViews();
        if (msg != null) {
			content.setText(msg);
		}
		if (btnMsg != null) {
			confirm.setText(btnMsg);
		}
    }

	public void bindViews() {
		confirm = (Button) findViewById(R.id.dialog_confirm);
		cancel = (Button) findViewById(R.id.dialog_cancel);
		content = (TextView) findViewById(R.id.dialog_text_exit);
		if (Constants.DIALOG_TIP == type) {
			cancel.setVisibility(View.GONE);
		}

		confirm.setOnClickListener(this);
		cancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int viewId = v.getId();
		if(viewId == R.id.dialog_cancel || viewId == R.id.dialog_confirm) {
			this.dismiss();
		}
	}
}