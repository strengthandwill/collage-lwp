package pohkahkong.livewallpaper.collage;

import pohkahkong.livewallpaper.collage.CollageWallpaperService.CollageWallpaperEngine;
import android.graphics.Canvas;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class FiveImagesCollage extends Collage {
	public FiveImagesCollage(CollageWallpaperEngine wallpaperEngine, ImageDevice imageDevice, int width, int height, int statusBarHeight, 
								boolean hasBorder, int borderColour, Border borderThickness, float initialScaleFactor, boolean isLockTransformation) {
		super(wallpaperEngine, imageDevice, width, height, statusBarHeight, hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);				
		// TODO Auto-generated constructor stub
		imageDevice.loadFiveImagesCollage(this, portraitWidth-border*2, portraitHeight-border*2, landscapeWidth-border*2, landscapeHeight-border*2);
		type = (int)(Math.random()*4.0);
	}

	@Override
	protected void initDimension() {
		// TODO Auto-generated method stub
		// portrait
		portraitWidth = width/2;
		portraitHeight = height/2;
		
		// landscape
		if (isPortraitOrientation) {
			landscapeWidth = width/2; 
			landscapeHeight = height/4;
		} else {
			landscapeWidth = width/4; 
			landscapeHeight = height/2;
		}
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub		
		super.draw(canvas);
		switch (type) {
			case 0: topLeft(canvas);
					break;
			case 1: topRight(canvas);
					break;
			case 2: bottomLeft(canvas);
					break;
			case 3: bottomRight(canvas);
					break;
		}		
	}
	
	private void topLeft(Canvas canvas) {
		// portrait
		if (images[0]!=null) canvas.drawBitmap(images[0], portraitWidth+border, statusBarHeight+border, paint);
		if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+portraitHeight+border, paint);
		if (images[2]!=null) canvas.drawBitmap(images[2], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
		
		// landscape
		if (isPortraitOrientation) {
			if (images[3]!=null) canvas.drawBitmap(images[3], border, statusBarHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], border, statusBarHeight+landscapeHeight+border, paint);
		} else {		
			if (images[3]!=null) canvas.drawBitmap(images[3], statusBarHeight+border, border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], statusBarHeight+landscapeWidth+border, border, paint);
		}
	}
	
	private void topRight(Canvas canvas) {
		// portrait
		if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
		if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+portraitHeight+border, paint);
		if (images[2]!=null) canvas.drawBitmap(images[2], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
				
		// landscape
		if (isPortraitOrientation) {
			if (images[3]!=null) canvas.drawBitmap(images[3], landscapeWidth+border, statusBarHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], landscapeWidth+border, statusBarHeight+landscapeHeight+border, paint);
		} else {
			if (images[3]!=null) canvas.drawBitmap(images[3], portraitWidth+border, statusBarHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], portraitWidth+landscapeWidth+border, statusBarHeight+border, paint);			
		}
	}		

	private void bottomLeft(Canvas canvas) {
		// portrait
		if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
		if (images[1]!=null) canvas.drawBitmap(images[1], portraitWidth+border, statusBarHeight+border, paint);
		if (images[2]!=null) canvas.drawBitmap(images[2], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
		
		// landscape
		if (isPortraitOrientation) {
			if (images[3]!=null) canvas.drawBitmap(images[3], border, statusBarHeight+portraitHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], border, statusBarHeight+portraitHeight+landscapeHeight+border, paint);
		} else {
			if (images[3]!=null) canvas.drawBitmap(images[3], border, statusBarHeight+portraitHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);			
		}
	}
	
	private void bottomRight(Canvas canvas) {
		// portrait
		if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
		if (images[1]!=null) canvas.drawBitmap(images[1], portraitWidth+border, statusBarHeight+border, paint);
		if (images[2]!=null) canvas.drawBitmap(images[2], border, statusBarHeight+portraitHeight+border, paint);
		
		// landscape
		if (isPortraitOrientation) {
			if (images[3]!=null) canvas.drawBitmap(images[3], landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], landscapeWidth+border, statusBarHeight+portraitHeight+landscapeHeight+border, paint);
		} else {
			if (images[3]!=null) canvas.drawBitmap(images[3], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], portraitWidth+landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);			
		}
	}	
}
