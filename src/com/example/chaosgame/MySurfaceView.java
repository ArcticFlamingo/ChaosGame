package com.example.chaosgame;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class MySurfaceView extends GLSurfaceView {

	private MyRenderer renderer;
	float prevY;
	int depth;
	float twist;
	int numTouches;
	
	public MySurfaceView(Context context) {
		super(context);
	}
	
	public MySurfaceView(Context context, MyRenderer renderer){
		super(context);
		this.renderer = renderer;
		this.setRenderer(renderer);
		this.setRenderMode(RENDERMODE_WHEN_DIRTY);
	}

	
	public boolean onTouchEvent(MotionEvent event){
		
		switch(event.getAction()){
		case MotionEvent.ACTION_MOVE:
			//if finger is dragged, change twist
			float y = event.getY();
			float dy = y-prevY;
			prevY = y;
			if(dy<0){ //down
			//decrease twist value	
				renderer.twist += 0.01f;
			}
			else if(dy>0){ //up
			//increase twist value
				renderer.twist -= 0.01f;
	
			}
			renderer.changeColor = false;
			this.requestRender();
			break;
			
		case MotionEvent.ACTION_DOWN:
			//if screen tapped, changes depth and color
		
				if(renderer.depth < 6){
					renderer.depth += 1;
				}
				else if (renderer.depth >= 6){
					renderer.depth = 0;
				}
			renderer.changeColor = true;	
			this.requestRender();
		}
		return true;
		
		
		}
	
}
