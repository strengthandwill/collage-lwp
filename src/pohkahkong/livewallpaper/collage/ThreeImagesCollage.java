package pohkahkong.livewallpaper.collage;

import pohkahkong.livewallpaper.collage.CollageWallpaperService.CollageWallpaperEngine;
import android.graphics.Canvas;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class ThreeImagesCollage extends Collage {	
	public ThreeImagesCollage(CollageWallpaperEngine wallpaperEngine, ImageDevice imageDevice, int width, int height, int statusBarHeight, 
								boolean hasBorder, int borderColour, Border borderThickness, float initialScaleFactor, boolean isLockTransformation) {
		super(wallpaperEngine, imageDevice, width, height, statusBarHeight, hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
		// TOO Auto-generated constructor stub		
		imageDevice.loadThreeImagesCollage(this, portraitWidth-border*2, portraitHeight-border*2, landscapeWidth-border*2, landscapeHeight-border*2);
		type = (int)(Math.random()*2.0);
	}
	
	@Override
	protected void initDimension() {
		// TODO Auto-generated method stub		
		// portrait
		portraitWidth = width/2;
		portraitHeight = height/2;
		
		// landscape
		if (isPortraitOrientation) {
			landscapeWidth = width;
			landscapeHeight = height/2;
		} else {
			landscapeWidth = width/2;
			landscapeHeight = height;
		}
	}
	
	@Override
	public void draw(Canvas canvas) {		
		super.draw(canvas);
		// TODO Auto-generated method stub
		switch (type) {
			case 0: top(canvas);
					break;
			case 1: bottom(canvas);
					break;
		}
	}
	
	private void top(Canvas canvas) {		
		// portrait
		if (isPortraitOrientation) {
			if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+portraitHeight+border, paint);
			if (images[1]!=null) canvas.drawBitmap(images[1], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
		} else {
			if (images[0]!=null) canvas.drawBitmap(images[0], portraitWidth+border, statusBarHeight+border, paint);
			if (images[1]!=null) canvas.drawBitmap(images[1], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
		}
		
		// landscape
		if (images[2]!=null) canvas.drawBitmap(images[2], border, statusBarHeight+border, paint);		
	}
	
	private void bottom(Canvas canvas) {
		if (isPortraitOrientation) {
			// portrait
			if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
			if (images[1]!=null) canvas.drawBitmap(images[1], portraitWidth+border, statusBarHeight+border, paint);
		
			// landscape
			if (images[2]!=null) canvas.drawBitmap(images[2], border, statusBarHeight+landscapeHeight+border , paint);
		} else {
			// portrait
			if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
			if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+portraitHeight+border, paint);
		
			// landscape
			if (images[2]!=null) canvas.drawBitmap(images[2], border+landscapeWidth, statusBarHeight+border , paint);			
		}
	}	
}
