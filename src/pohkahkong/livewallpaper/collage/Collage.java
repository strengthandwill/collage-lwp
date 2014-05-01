package pohkahkong.livewallpaper.collage;

import pohkahkong.livewallpaper.collage.CollageWallpaperService.CollageWallpaperEngine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.FloatMath;
import android.view.MotionEvent;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public abstract class Collage {		
	//private static final String TAG = "Collage";
	public enum State {START, FADEIN, NORMAL, FADEOUT};
	public enum Border {THIN, MEDIUM, THICK};

	// general
	protected CollageWallpaperEngine wallpaperEngine;	
	protected Canvas canvas;
	protected ImageDevice imageDevice;
	protected State state = State.START;
	protected int type;
	protected boolean isPortraitOrientation;
	protected boolean isError;
	
	// dimensions
	protected float initialScaleRatio;
	protected int width;
	protected int height;
	protected int portraitWidth;
	protected int portraitHeight;	
	protected int landscapeWidth;
	protected int landscapeHeight;	

	// images
	protected Bitmap startPage;
	protected Bitmap[] images;
	
	// preferences
	//protected boolean hasImages = false;
	protected int statusBarHeight;
	protected boolean hasBorder;
	protected int border;		
	protected int borderColour;
	
	// transition
	protected final int alphaChange = 10;
	protected Paint paint;
	protected int alpha = 0;
	
	// transformation
	protected final int NONE = 0;
	protected final int DRAG = 1;
	protected final int ZOOM = 2;
	protected int mode = NONE;	
	protected Matrix matrix;
	protected Matrix savedMatrix;
	protected boolean isLockTransformation;
	protected boolean isTransformationEnabled = true;
	protected PointF start;
	protected PointF mid;
	protected float oldDist = 1f;

	public Collage(CollageWallpaperEngine wallpaperEngine, ImageDevice imageDevice, int width, int height, int statusBarHeight, 
	boolean hasBorder, int borderColour, Border borderThickness, float initialScaleFactor, boolean isLockTransformation) {
		this.wallpaperEngine = wallpaperEngine;
		this.imageDevice = imageDevice;
		this.width = width;
		this.height = height;
		this.hasBorder = hasBorder;
		this.borderColour = borderColour;
		this.height = height-statusBarHeight;
		this.statusBarHeight = statusBarHeight; 
		initialScaleRatio = 1.0f/initialScaleFactor;
		this.isLockTransformation = isLockTransformation;
		
		if (width<height)
			isPortraitOrientation = true;
		else
			isPortraitOrientation = false;
		imageDevice.setOrientation(isPortraitOrientation);
		if (hasBorder) {
			switch (borderThickness) {
			case THIN: 		border = width/50;
			break;
			case MEDIUM: 	border = width/40;
			break;
			case THICK: 	border = width/30;
			break;							
			}
		}
		else
			border = 0;
		initDimension();
		paint = new Paint();
		paint.setAlpha(alpha);			
		matrix = new Matrix();
		savedMatrix = new Matrix();
		start = new PointF();
		mid = new PointF();			
		matrix.postScale(initialScaleRatio, initialScaleRatio);
	}

	protected abstract void initDimension();
	
	public boolean isError() {
		return isError;
	}

	// ****************************************** setters ****************************************** //	
	public void loadStartPage(Bitmap startPage) {
		this.startPage = startPage;
	}

	public void loadImages(Bitmap[] images) {		
		this.images = images;
		if (images==null)
			isError = true;
		else
			isError = false;
	}
	
	public void isLockTransformation(boolean isLockTransformation) {
		this.isLockTransformation = isLockTransformation;
	}
	
	public void isTransformationEnabled(boolean isTransformationEnabled) {
		this.isTransformationEnabled = isTransformationEnabled;
	}

	// ****************************************** transitions ****************************************** //	
	public void fadeIn() {
		alpha = 0;
		state = State.FADEIN;
	}

	public void fadeOut() {
		wallpaperEngine.stopTimer();
		alpha = 255;
		state = State.FADEOUT;
	}

	// ****************************************** draw ****************************************** //	
	public void draw(Canvas canvas) {		
		switch (state) {
			case START:   alpha = 0;
					  	  break;
			case FADEIN:  alpha+=alphaChange;
					  	  if (alpha>=255) {					  		  
					  		  alpha = 255;
					  		  state = State.NORMAL;
					  		  wallpaperEngine.startTimer();
					  	  }
					  	  break;
			case NORMAL:  alpha = 255;
						  break;
			case FADEOUT: alpha-=alphaChange;
						  if (alpha<=0) {
							  alpha = 0;
							  wallpaperEngine.nextCollage();
						  }
						  break;						
		}		
		paint.setAlpha(alpha);				
		if (isTransformationEnabled)
			canvas.setMatrix(matrix);
		else {
			if (isLockTransformation)
				canvas.setMatrix(matrix);
			else {
				matrix = new Matrix();
				matrix.postScale(initialScaleRatio, initialScaleRatio);
				canvas.restore();
				canvas.setMatrix(matrix);
			}
		}		
		
		if (hasBorder)
			canvas.drawColor(borderColour);				
		else
			canvas.drawColor(Color.WHITE);								
	}

	// ****************************************** touch events ****************************************** //
	public boolean onTouch(MotionEvent event) {
		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			savedMatrix.set(matrix);
			start.set(event.getX(), event.getY());
			mode = DRAG;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			mode = NONE;
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				// ...
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x,
						event.getY() - start.y);
			}
			else if (mode == ZOOM) {
				float newDist = spacing(event);
				if (newDist > 10f) {
					matrix.set(savedMatrix);
					float scale = newDist / oldDist;
					matrix.postScale(scale, scale, mid.x, mid.y);
				}
			}
			break;
		}
		return true;
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}	
	
	// ****************************************** release actions ****************************************** //
	public void release() {
		imageDevice.recycle(images);
	}	
}
