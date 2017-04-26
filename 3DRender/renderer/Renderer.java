package code.renderer;

import java.awt.Color;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.omg.CORBA.PRIVATE_MEMBER;



public class Renderer extends GUI {
			//the 3D model
			private Scene scene;
			private Scene centralisedScene;
			//the viewing angle for rotation
			private float xRotation = 0f, yRotation = 0f;
			private float rotatAngle = 0.2f;	
			//the viewing position for translation
			private Vector3D viewerPosition = new Vector3D(0f, 0f, 0f);
			private float translationDistance = 2.0f;
			//constants for zoomig
			private float currentScale = 1.0f;
			private static final float ZOOMING_FACTOR = 1.5f;
			private static final float ZOOMING_MIN = 0.5f, ZOOMING_MAX = 6.0f;
			//For rotation 
			private boolean isRotation = true;
			private Point dragStart;
	@Override
	protected void onLoad(File file) {
		// TODO fill this in.

		/*
		 * This method should parse the given file into a Scene object, which
		 * you store and use to render an image.
		 */
		xRotation = yRotation = 0f;
		viewerPosition = new Vector3D(0f, 0f, 0f);
		currentScale = 1.0f;
		isRotation = true;
		centralisedScene = null;
		List<Polygon> polygons = new ArrayList<>();
		Vector3D lightPosition;
		BufferedReader bufferReader;
		try {
			bufferReader = new BufferedReader(new FileReader(file));
			String  line = bufferReader.readLine();
			if (line == null) {
				System.out.println("The file is empty");
				bufferReader.close();
				return;
			}
			String[] values = line.split(" ");
			//parse the light source
			float x =Float.parseFloat(values[0]);
			float y =Float.parseFloat(values[1]);
			float z =Float.parseFloat(values[2]);
			lightPosition = new Vector3D(x, y, z);
			
			line =bufferReader.readLine();
			
			//parse polygons one by one
			while(line != null){
				values = line.split(" ");
				//coordinators of polygon
				float aX = Float.parseFloat(values[0]);
				float aY = Float.parseFloat(values[1]);
				float aZ = Float.parseFloat(values[2]);
				float bX = Float.parseFloat(values[3]);
				float bY = Float.parseFloat(values[4]);
				float bZ = Float.parseFloat(values[5]);
				float cX = Float.parseFloat(values[6]);
				float cY = Float.parseFloat(values[7]);
				float cZ = Float.parseFloat(values[8]);
				float[] points = new float[]{aX,aY,aZ,bX,bY,bZ,cX,cY,cZ};
				
				int surfaceRed = Integer.parseInt(values[9]);
				int surfaceGreen = Integer.parseInt(values[10]);
				int surfaceBlue = Integer.parseInt(values[11]);
				int[] color = new int[]{surfaceRed,surfaceGreen,surfaceBlue};
				polygons.add(new Polygon(points,color));
				line = bufferReader.readLine();			
			}
			bufferReader.close();
			this.scene = new Scene(polygons, lightPosition);			
			
		} catch (FileNotFoundException e) {
			System.err.println("FileNotFoundException");
		}catch (IOException e) {
			System.err.println("IOeException");
		}
		
	}
	
    private void rotationX(float amount) {
		xRotation += amount;
	}
    private void rotationY(float amount) {
		yRotation += amount;
	}
    private void moveDown(float amount) {
		viewerPosition = viewerPosition.plus(new Vector3D(0f,amount, 0f));
	}
    private void moveRight() {
		
	}
    
    
	/*
	 * This method should be used to rotate the user's viewpoint.
	 */
	@Override
	protected void onKeyPress(KeyEvent ev) {
		// TODO fill this in.
		char c = ev.getKeyChar();
		//Roatation
		if (ev.getKeyCode() == KeyEvent.VK_UP) {
			rotateX
		}
		
	}

	@Override
	protected BufferedImage render() {
		// TODO fill this in.

		/*
		 * This method should put together the pieces of your renderer, as
		 * described in the lecture. This will involve calling each of the
		 * static method stubs in the Pipeline class, which you also need to
		 * fill in.
		 */
		return null;
	}

	/**
	 * Converts a 2D array of Colors to a BufferedImage. Assumes that bitmap is
	 * indexed by column then row and has imageHeight rows and imageWidth
	 * columns. Note that image.setRGB requires x (col) and y (row) are given in
	 * that order.
	 */
	private BufferedImage convertBitmapToImage(Color[][] bitmap) {
		BufferedImage image = new BufferedImage(CANVAS_WIDTH, CANVAS_HEIGHT, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < CANVAS_WIDTH; x++) {
			for (int y = 0; y < CANVAS_HEIGHT; y++) {
				image.setRGB(x, y, bitmap[x][y].getRGB());
			}
		}
		return image;
	}

	public static void main(String[] args) {
		new Renderer();
	}

	@Override
	protected void onScroll(MouseWheelEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void switchMoveRotation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onDefalut() {
		// TODO Auto-generated method stub
		
	}
}

// code for comp261 assignments
