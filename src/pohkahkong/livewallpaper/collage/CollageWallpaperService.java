package pohkahkong.livewallpaper.collage;

import java.util.Timer;
import java.util.TimerTask;

import pohkahkong.livewallpaper.collage.Collage.Border;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.widget.Toast;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class CollageWallpaperService extends WallpaperService {		
	@Override
	public Engine onCreateEngine() {
		// TODO Auto-generated method stub	
		return new CollageWallpaperEngine(this);					
	}
	
	public class CollageWallpaperEngine extends Engine implements SharedPreferences.OnSharedPreferenceChangeListener, 
	GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
		//private static final String TAG = "CollageWallpaperEngine";
		
		// general
		private final Handler handler = new Handler();
		private Context context;
		private ImageDevice imageDevice;
		private GestureDetector gestureDetector;
		private Collage newCollage;
		private Collage collage;		
		private boolean isInit = false;
		private boolean visible = true;
		private boolean isTransformationEnabled = false;
		private boolean hasImages = false;
		
		// dimensions
		protected float initialScaleFactor = 2.0f;
		private int width;
		private int height;		
		private int statusBarHeight;		
					
		// preferences
		private final String pathDefaultValue = Environment.getExternalStorageDirectory().getPath();
		private final String transitionSpeedDefaultValue = "60s"; 
		private final boolean hasStatusBarDefaultValue = true;
		private final boolean isLockTransformationDefaultValue = false;
		private final boolean scaleImageDefaultValue = false;
		private final boolean hasBorderDefaultValue = false;
		private final String borderColourDefaultValue = "Black";
		private final String borderThicknessDefaultValue = "Medium";
		private final String imageNumDefaultValue = "1,2,3,4,5";		
		private SharedPreferences pref;
		private String path;
		private String imageNumber;		
		private boolean scaleImage;
		private boolean hasBorder;
		private boolean isLockTransformation;
		private int borderColour;
		private Border borderThickness;
		private boolean[] isUsedImageNum;			
		
		// timer
		private final int drawSpeed = 100;
		private Timer timer;
		private int transitionSpeed;
		private int changedTransitionSpeed;
		private int timeCounter;						
		
		public CollageWallpaperEngine(Context context) {
			this.context = context;
			imageDevice = new ImageDevice(context);
			pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			pref.registerOnSharedPreferenceChangeListener(this);
			
			gestureDetector = new GestureDetector(this);
			gestureDetector.setOnDoubleTapListener(this);				
		}		
		
		// ****************************************** Preferences ****************************************** //		
		private void loadPath() {
			// general
			path = pref.getString("path", "");			
			if (path.equals("")) { // path is not set
				Editor editor = pref.edit();
				editor.putString("path", pathDefaultValue);
				editor.commit();
				path = pref.getString("path", "");
			}
			imageDevice.setPath(path);
		}
		
		private void loadTransitionSpeed() {			
			String str = pref.getString("transitionSpeed2", transitionSpeedDefaultValue);			 
			transitionSpeed = Integer.parseInt(str.substring(0, str.length()-1));
			timeCounter = transitionSpeed;
			changedTransitionSpeed = (int)(transitionSpeed*0.25f);
		}
		
		private void loadHasStatusBar() {
			boolean hasStatusBar = pref.getBoolean("hasStatusBar", hasStatusBarDefaultValue);	
			if (hasStatusBar)
				statusBarHeight = (int) (width/12.5);
			else
				statusBarHeight = 0;
			imageDevice.setDimension((int)(width*initialScaleFactor), (int)((height-statusBarHeight)*initialScaleFactor));
		}
		
		private void loadIsLockTransformation() {
			isLockTransformation = pref.getBoolean("isLockTransformation", isLockTransformationDefaultValue);	
			if (collage!=null)
				collage.isLockTransformation(isLockTransformation);			
		}		
		
		private void loadImageNumber() {
			// image
			imageNumber = pref.getString("imageNumber", "");		
			if (imageNumber.equals("")) { // imageNumber is not set
				Editor editor = pref.edit();
				editor.putString("imageNumber", imageNumDefaultValue);
				editor.commit();
				imageNumber = pref.getString("imageNumber", "");
			}	
			
			isUsedImageNum = new boolean[8];			
			for (int i=0; i<isUsedImageNum.length; i++)
				isUsedImageNum[i] = false;				
			String[] values = imageNumber.split(",");			
			
			for (int i=0; i<values.length; i++) {
				switch (Integer.parseInt(values[i])) {
					case 1: isUsedImageNum[0] = true;
							break;
					case 2: isUsedImageNum[1] = true;
							break;
					case 3: isUsedImageNum[2] = true;
							break;
					case 4: isUsedImageNum[3] = true;
							break;
					case 5: isUsedImageNum[4] = true;
							break;
					case 6: isUsedImageNum[5] = true;
							break;
					case 7: isUsedImageNum[6] = true;
							break;
					case 8: isUsedImageNum[7] = true;
							break;										
				}
			}			
		}
		
		private void loadScaleImage() {
			scaleImage = pref.getBoolean("scaleImage", scaleImageDefaultValue);
			imageDevice.setScaleImage(scaleImage);
		}
		
		private void loadHasBorder() {
			hasBorder = pref.getBoolean("hasBorder", hasBorderDefaultValue);							
		}
		
		private void loadBorderColour() {
			String str = pref.getString("borderColour", borderColourDefaultValue);
			if (str.equals("Black"))
				borderColour = Color.BLACK;
			else if (str.equals("Blue"))
				borderColour = Color.BLUE;
			else if (str.equals("Cyan"))
				borderColour = Color.CYAN;
			else if (str.equals("Dark Gray"))
				borderColour = Color.DKGRAY;
			else if (str.equals("Gray"))
				borderColour = Color.GRAY;
			else if (str.equals("Green"))
				borderColour = Color.GREEN;
			else if (str.equals("Light Gray"))
				borderColour = Color.LTGRAY;
			else if (str.equals("Magenta"))
				borderColour = Color.MAGENTA;
			else if (str.equals("Red"))
				borderColour = Color.RED;
			else if (str.equals("White"))
				borderColour = Color.WHITE;
			else 
				borderColour = Color.YELLOW;			
		}
		
		private void loadBorderThickness() {
			String str = pref.getString("borderThickness", borderThicknessDefaultValue);
			if (str.equals("Thin"))
				borderThickness = Border.THIN;
			else if (str.equals("Medium"))
				borderThickness = Border.MEDIUM;
			else
				borderThickness = Border.THICK;				
		}
		
		private void loadPreferences() {			
			loadPath();
			loadTransitionSpeed();
			loadHasStatusBar();
			loadIsLockTransformation();
			loadImageNumber();
			loadScaleImage();
			loadHasBorder();
			loadBorderColour();
			loadBorderThickness();
		}

		// ****************************************** Events ****************************************** //						
		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			this.width = width;
			this.height = height;						
			loadPreferences();
							
			hasImages = imageDevice.hasImages();
			getCollage();
			nextCollage();
			isInit = false;
		}		

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {			
			super.onSurfaceDestroyed(holder);
			visible = false;
			stop();
		}		
		
		@Override
		public void onVisibilityChanged(boolean visible) {
			this.visible = visible;
			if (visible)
				start();					
			else
				stop();			
		}				

		public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			// TODO Auto-generated method stub				
			if (key.equals("path")) 
				loadPath();
			else if (key.equals("transitionSpeed2"))
				loadTransitionSpeed();
			else if (key.equals("hasStatusBar"))
				loadHasStatusBar();
			else if (key.equals("isLockTransformation"))
				loadIsLockTransformation();			
			else if (key.equals("imageNumber"))
				loadImageNumber();
			else if (key.equals("scaleImage"))
				loadScaleImage();
			else if (key.equals("hasBorder"))
				loadHasBorder();
			else if (key.equals("borderColour"))
				loadBorderColour();
			else if (key.equals("borderThickness"))
				loadBorderThickness();			
		}				

		@Override
		public void onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub						
			super.onTouchEvent(event);			
			if (!gestureDetector.onTouchEvent(event) && isTransformationEnabled && collage!=null)
				collage.onTouch(event);			
		}

		public boolean onDoubleTap(MotionEvent e) {
			// TODO Auto-generated method stub						
			isTransformationEnabled = !isTransformationEnabled;
			if (collage!=null)
				collage.isTransformationEnabled(isTransformationEnabled);
			
			if (visible) {
				Toast msg;
				if (isTransformationEnabled)
					msg = Toast.makeText(context, "Unlocked", Toast.LENGTH_SHORT);
				else
					msg = Toast.makeText(context, "Locked", Toast.LENGTH_SHORT);
				msg.show();		
			}			
			return true;
		}		
		
		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			super.onDestroy();
			if (collage!=null)
				collage.release();
		}
		
		// ****************************************** Draw ****************************************** //				
		private final Runnable drawRunner = new Runnable() {
			public void run() {
				draw();
			}
		};			

		private void draw() {			
			SurfaceHolder holder = getSurfaceHolder();
			Canvas canvas = null;
			try {							
				canvas = holder.lockCanvas();				
				if (collage!=null)
					collage.draw(canvas);													
			} catch (Exception e) {				
			} finally {
				if (canvas != null)
					holder.unlockCanvasAndPost(canvas);
			}
			handler.removeCallbacks(drawRunner);
			if (visible)								
				handler.postDelayed(drawRunner, drawSpeed);					
		}				
		
		private void getCollage() {
			if (!hasImages)
				newCollage = new StartPageCollage(this, imageDevice, (int)(width*initialScaleFactor), (int)(height*initialScaleFactor), initialScaleFactor, isLockTransformation);			
			else {			
				int	random;			
				do {
					random = (int)(Math.random()*8.0);
				} while (!isUsedImageNum[random]);
				switch (random) {
					case 0: newCollage = new OneImageCollage(this, imageDevice, (int)(width*initialScaleFactor), (int)(height*initialScaleFactor), (int)(statusBarHeight*initialScaleFactor), 
															 hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
							break;
					case 1: newCollage = new TwoImagesCollage(this, imageDevice, (int)(width*initialScaleFactor), (int)(height*initialScaleFactor), (int)(statusBarHeight*initialScaleFactor), 
														  	  hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
							break;
					case 2: newCollage = new ThreeImagesCollage(this, imageDevice, (int)(width*initialScaleFactor), (int)(height*initialScaleFactor), (int)(statusBarHeight*initialScaleFactor), 
																hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
							break;
					case 3: newCollage = new FourImagesCollage(this, imageDevice, (int)(width*initialScaleFactor), (int)(height*initialScaleFactor), (int)(statusBarHeight*initialScaleFactor), 
														   	   hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
							break;
					case 4: newCollage = new FiveImagesCollage(this, imageDevice, (int)(width*initialScaleFactor), (int)(height*initialScaleFactor), (int)(statusBarHeight*initialScaleFactor), 
														   	   hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
							break;
					case 5: newCollage = new SixImagesCollage(this, imageDevice, (int)(width*initialScaleFactor), (int)(height*initialScaleFactor), (int)(statusBarHeight*initialScaleFactor), 
														  	  hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
							break;
					case 6: newCollage = new SevenImagesCollage(this, imageDevice, (int)(width*initialScaleFactor), (int)(height*initialScaleFactor), (int)(statusBarHeight*initialScaleFactor), 
																hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
							break;
					case 7: newCollage = new EightImagesCollage(this, imageDevice, (int)(width*initialScaleFactor), (int)(height*initialScaleFactor), (int)(statusBarHeight*initialScaleFactor), 
																hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
							break;
				}
				if (newCollage.isError()) {
					hasImages = false;
					getCollage();
					nextCollage();
				}
			}
		}
		
		public void nextCollage() {
			if (collage!=null)
				collage.release();
			if (newCollage!=null) {
				collage = newCollage;
				collage.fadeIn();
			}
		}
		
		// ****************************************** Timer ****************************************** //
		public void startTimer() {
			stopTimer();
			timer = new Timer();						
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					timerMethod();
				}
			}, 1000, 1000);			
		}

		public void stopTimer() {
			if (timer!=null)
				timer.cancel();
		}		

		private void timerMethod() {
			handler.post(timerTick);
		}

		private Runnable timerTick = new Runnable() {
			public void run() {				
				if (visible) {					
					if (!hasImages) { // no images
						hasImages = imageDevice.hasImages();
						if (hasImages) { // images just loaded
							collage.fadeOut();
							getCollage();
							timeCounter = transitionSpeed;
						}
					} else { // has images
						if (timeCounter==0 && collage!=null) {
							collage.fadeOut();
							getCollage();
							timeCounter = transitionSpeed;
						}
						timeCounter--;
					}
				}
				handler.removeCallbacks(timerTick);				
			}
		};		
		
		// ****************************************** Actions ****************************************** //
		private void start() {						
			if (isInit)
				timeCounter = changedTransitionSpeed;				
			else				
				isInit = true;			
			startTimer();
			handler.post(drawRunner);
		}
		
		private void stop() {
			stopTimer();
			handler.removeCallbacks(timerTick);
			handler.removeCallbacks(drawRunner);
			isTransformationEnabled = false;
			if (collage!=null)
				collage.isTransformationEnabled(isTransformationEnabled);
		}				
		
		// ****************************************** Not used events ****************************************** //		
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}

		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub			
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub			
		}

		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}		

		public boolean onDoubleTapEvent(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
	}
}

