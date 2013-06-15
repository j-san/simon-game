package com.jsan.simongame;


import com.jsan.simongame.R;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class ColorFragment extends View implements OnTouchListener {
	private int color;
	
	public interface PushListener {
		public void onPush(View v);
	}
	
	private PushListener onPushListener;
	
	public ColorFragment(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void onDraw(Canvas canvas) { 
		setOnTouchListener(this);
	}
	 
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.color_fragment, container, false);
//		v.setOnTouchListener(this);
//
//		return v;
//	}

	public void setColor(int rColor) {
		color = rColor;
		Log.i("app","color " + color);
		setBackgroundColor(color);
	}

	@Override
	public boolean onTouch(View v, MotionEvent evt) {
		if(evt.getAction() == MotionEvent.ACTION_DOWN) {
			on();
		}
		if(evt.getAction() == MotionEvent.ACTION_UP) {
			off();
			if(onPushListener != null) {
				onPushListener.onPush(this);
			}
		}
		return true;
	}

	public void on() {
		setBackgroundColor(getResources().getColor(R.color.white));
	}

	public void off() {
		setBackgroundColor(color);
	}

	public PushListener getPushListener() {
		return onPushListener;
	}

	public void setPushListener(PushListener l) {
		this.onPushListener = l;
	}
}
