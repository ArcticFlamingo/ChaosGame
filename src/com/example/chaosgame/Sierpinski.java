package com.example.chaosgame;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Sierpinski {
	private int level; //depth of recursion
					   //level 0= 1 triangle, level 1 = 3 triangles
	private float vertexCoordinates[];
	private FloatBuffer vertexBuffer;
	private int vSize; 
	private int vCount;  
	Vertex[] initial; 
	int place = 0;
	float twistValue;
	Boolean changeColor;
	
	public Sierpinski(int depth, float twist, Boolean changeColor){
		this.changeColor = changeColor;
		twistValue = twist;
		vSize = 3;
		level = depth;
		vCount = 3;
		for (int i=0;i<level; i++){
			vCount*=3;
		}
		initial = new Vertex[3];
		initial[0]=new Vertex(-0.8f,-0.6f, 0.0f);
		initial[1]= new Vertex(0.8f,-0.6f, 0.0f);
		initial[2]= new Vertex(0.0f, 0.6f, 0.0f);
		
	
		
		vertexCoordinates = new float[vCount*3];
		Triangle triangle = new Triangle(initial[0], initial[1], initial[2], level);
		place = 0;
		triangle.write(vertexCoordinates);
		//allocate a buffer with space for vCount vertexCoordinates 
		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertexCoordinates.length*4);
		byteBuf.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuf.asFloatBuffer();
		vertexBuffer.put(vertexCoordinates);
	}
	
	
	public Vertex midpoint(Vertex v, Vertex w){
		return new Vertex((v.x+w.x)/2.0f, (v.y+w.y)/2.0f, (v.z+w.z)/2.0f);
	}
	
	
	public void draw(GL10 gl){
		if(changeColor){
		float color1 = (float)Math.random();
		float color2 = (float)Math.random();
		float color3 = (float)Math.random();
		
		gl.glColor4f(color1, color2, color3, 1.0f);
		}else{
		gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
		}
		
		gl.glFrontFace(GL10.GL_CCW);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertexBuffer.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, vSize*4, vertexBuffer);
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vCount);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);		
	}

	
	
class Vertex{
		float x;
		float y;
		float z;
		
		
		public Vertex(float x, float y, float z) {
			super();
			this.x = x;
			this.y = y;
			this.z = z;
			
			//twist
			float d = (float) Math.sqrt((this.x*this.x + this.y*this.y));
			this.x = (float) (Math.cos(twistValue*d)*this.x - Math.sin(twistValue*d)*this.y);
	        this.y = (float) (Math.sin(twistValue*d)*this.x + Math.cos(twistValue*d)*this.y);
	        
		}
		
		void write(float v[]){
			v[place++]=x;
			v[place++]=y;
			v[place++]=z;
		}


	}


class Triangle{
		int level;
		Triangle child0=null;
		Triangle child1=null;
		Triangle child2=null;
		Vertex a,b,c;
		
		public Triangle(Vertex a, Vertex b, Vertex c, int level){
			this.a=a;
			this.b=b;
			this.c=c;
			this.level = level;
			if(level>0)
				divide();
		}
		
		
		void divide(){
			Vertex ab = midpoint(a,b);
			Vertex bc = midpoint(b,c);
			Vertex ca = midpoint(c,a);
			child0 = new Triangle(a,ab,ca, level-1);
			child1 = new Triangle(ab,b,bc, level-1);
			child2 = new Triangle(bc,c,ca, level-1);
		}
		
			/**
			 * prints the 3 vertex Coordinates(x,y,z) to the array
			 */		
		void write(float v[]){
			if(level==0){
				a.write(v);
				b.write(v);
				c.write(v);
			}else{
				child0.write(v);
				child1.write(v);
				child2.write(v);				
			}
		}
	}

}
