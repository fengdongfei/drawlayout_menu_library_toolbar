package com.saike.tzh.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.saike.tzh.account.ui.LoginActivity;
/**
 * 主要用于监听软键盘的收回和隐藏
 * 使用方式:  套在布局中所有控件的最外层
 */
public class KeyboardListenRelativeLayout extends RelativeLayout {
	
	private static final String TAG = KeyboardListenRelativeLayout.class.getSimpleName();
	
	public static final byte KEYBOARD_STATE_SHOW = -3;
	public static final byte KEYBOARD_STATE_HIDE = -2;
	public static final byte KEYBOARD_STATE_INIT = -1;
	private  Activity context;

	private boolean mHasInit = false;
	private boolean mHasKeyboard = false;
	private int mHeight;
	
	private IOnKeyboardStateChangedListener onKeyboardStateChangedListener;
	
	public KeyboardListenRelativeLayout(Context context) {
		super(context);
	}
	public KeyboardListenRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

	}
	
	public KeyboardListenRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	public void setOnKeyboardStateChangedListener(IOnKeyboardStateChangedListener onKeyboardStateChangedListener,Activity context) {
		this.onKeyboardStateChangedListener = onKeyboardStateChangedListener;
		this.context=context;
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(!mHasInit) {
			mHasInit = true;
			mHeight = b;
			if(onKeyboardStateChangedListener != null) {
				onKeyboardStateChangedListener.onKeyboardStateChanged(KEYBOARD_STATE_INIT);
			}
		} else {
			mHeight = mHeight < b ? b : mHeight;
		}
		
		if(mHasInit && mHeight > b) {
			mHasKeyboard = true;
			if(onKeyboardStateChangedListener != null) {
				onKeyboardStateChangedListener.onKeyboardStateChanged(KEYBOARD_STATE_SHOW);
			}
		}
		if(mHasInit && mHasKeyboard && mHeight == b) {
			mHasKeyboard = false;
			if(onKeyboardStateChangedListener != null) {
				onKeyboardStateChangedListener.onKeyboardStateChanged(KEYBOARD_STATE_HIDE);
			}
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (context.getCurrentFocus() != null) {
				if (context.getCurrentFocus().getWindowToken() != null) {
					 InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
				}
			}
		}
		return super.onTouchEvent(event);
	}
	public interface IOnKeyboardStateChangedListener {
		public void onKeyboardStateChanged(int state);
	}
}
