package com.example.chaosgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private MyRenderer mGLRenderer;
	private MySurfaceView mGLView;
	private int mDepth = 0;
	private float mTwist = 0.0f;
	public Boolean isRunning = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGLRenderer = new MyRenderer(mDepth, mTwist);
		mGLView = new MySurfaceView(this, mGLRenderer);
		//mGLView.setRenderer(mGLRenderer);
		setContentView(mGLView);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	@Override
	protected void onPause() {
	  	super.onPause();
	  	mGLView.onPause();
	 }

	 @Override
	 protected void onResume() {
	  	super.onResume();
	  	mGLView.onResume();
	  }

}
