package com.example.chaosgame;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.opengl.GLSurfaceView.Renderer;

public class MyRenderer implements Renderer {
	Sierpinski triangle;
	public int depth;
	public float twist;
	public Boolean changeColor;
	
	public MyRenderer(int depth, float twist){
		this.depth = depth;
		this.twist = twist;
		changeColor = false;
	}
	
	
	@Override
	public void onDrawFrame(GL10 gl) {
		triangle = new Sierpinski(depth, twist, changeColor);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();
		triangle.draw(gl);

	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//if user changes orientation of their phone this will use best possible area
		if(height == 0){
			height = 1; 
				}
				int delta;
				if(width <= height){
					delta = (height-width)/2;	
					gl.glViewport(0, delta, width, width); 
				} else{
					delta = (width-height) /2;
					gl.glViewport(delta, 0, height, height); 
				}
				gl.glMatrixMode(GL10.GL_PROJECTION);		
				gl.glLoadIdentity();
				gl.glOrthof(-1.0f, 1.0f, -1.0f, 1.0f, -0.5f, 0.5f);
				gl.glMatrixMode(GL10.GL_MODELVIEW);

	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
		gl.glShadeModel(GL10.GL_SMOOTH);
		gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);					
		gl.glEnable(GL10.GL_DEPTH_TEST);		
		gl.glDepthFunc(GL10.GL_LEQUAL);
	}

}
