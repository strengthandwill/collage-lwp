package pohkahkong.livewallpaper.collage;

import pohkahkong.livewallpaper.collage.CollageWallpaperService.CollageWallpaperEngine;
import android.graphics.Canvas;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class SevenImagesCollage extends Collage {
	public SevenImagesCollage(CollageWallpaperEngine wallpaperEngine, ImageDevice imageDevice, int width, int height, int statusBarHeight, 
								boolean hasBorder, int borderColour, Border borderThickness, float initialScaleFactor, boolean isLockTransformation) {
		super(wallpaperEngine, imageDevice, width, height, statusBarHeight, hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);				
		// TODO Auto-generated constructor stub
		imageDevice.loadSevenImagesCollage(this, portraitWidth-border*2, portraitHeight-border*2, landscapeWidth-border*2, landscapeHeight-border*2);
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
			case 0: topLeftTopRightBottomLeft(canvas);
					break;
			case 1: topLeftTopRightBottomRight(canvas);
					break;
			case 2: topLeftBottomLeftBottomRight(canvas);
					break;
			case 3: topRightBottomLeftBottomRight(canvas);
					break;
		}		
	}
	
	private void topLeftTopRightBottomLeft(Canvas canvas) {
		// portrait		
		if (images[0]!=null) canvas.drawBitmap(images[0], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
				
		// landscape	
		if (isPortraitOrientation) {
			if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+border, paint);
			if (images[2]!=null) canvas.drawBitmap(images[2], border, statusBarHeight+landscapeHeight+border, paint);
		
			if (images[3]!=null) canvas.drawBitmap(images[3], landscapeWidth+border, statusBarHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], landscapeWidth+border, statusBarHeight+landscapeHeight+border, paint);
		
			if (images[5]!=null) canvas.drawBitmap(images[5], border, statusBarHeight+portraitHeight+border, paint);
			if (images[6]!=null) canvas.drawBitmap(images[6], border, statusBarHeight+portraitHeight+landscapeHeight+border, paint);
		} else {
			if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+border, paint);
			if (images[2]!=null) canvas.drawBitmap(images[2], landscapeWidth+border, statusBarHeight+border, paint);
		
			if (images[3]!=null) canvas.drawBitmap(images[3], portraitWidth+border, statusBarHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], portraitWidth+landscapeWidth+border, statusBarHeight+border, paint);
		
			if (images[5]!=null) canvas.drawBitmap(images[5], border, statusBarHeight+portraitHeight+border, paint);
			if (images[6]!=null) canvas.drawBitmap(images[6], landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);			
		}
	}	
	
	private void topLeftTopRightBottomRight(Canvas canvas) {
		// portrait		
		if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+portraitHeight+border, paint);
		
		// landscape
		if (isPortraitOrientation) {
			if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+border, paint);
			if (images[2]!=null) canvas.drawBitmap(images[2], border, statusBarHeight+landscapeHeight+border, paint);
		
			if (images[3]!=null) canvas.drawBitmap(images[3], landscapeWidth+border, statusBarHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], landscapeWidth+border, statusBarHeight+landscapeHeight+border, paint);	
	
			if (images[5]!=null) canvas.drawBitmap(images[5], landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);
			if (images[6]!=null) canvas.drawBitmap(images[6], landscapeWidth+border, statusBarHeight+portraitHeight+landscapeHeight+border, paint);
		} else {
			if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+border, paint);
			if (images[2]!=null) canvas.drawBitmap(images[2], landscapeWidth+border, statusBarHeight+border, paint);
		
			if (images[3]!=null) canvas.drawBitmap(images[3], portraitWidth+border, statusBarHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], portraitWidth+landscapeWidth+border, statusBarHeight+border, paint);	
	
			if (images[5]!=null) canvas.drawBitmap(images[5], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
			if (images[6]!=null) canvas.drawBitmap(images[6], portraitWidth+landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);			
		}
	}
	
	private void topLeftBottomLeftBottomRight(Canvas canvas) {
		// portrait
		if (images[0]!=null) canvas.drawBitmap(images[0], portraitWidth+border, statusBarHeight+border, paint);
		
		// landscape
		if (isPortraitOrientation) {
			if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+border, paint);
			if (images[2]!=null) canvas.drawBitmap(images[2], border, statusBarHeight+landscapeHeight+border, paint);
	
			if (images[3]!=null) canvas.drawBitmap(images[3], border, statusBarHeight+portraitHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], border, statusBarHeight+portraitHeight+landscapeHeight+border, paint);
		
			if (images[5]!=null) canvas.drawBitmap(images[5], landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);
			if (images[6]!=null) canvas.drawBitmap(images[6], landscapeWidth+border, statusBarHeight+portraitHeight +landscapeHeight+border, paint);
		} else {
			if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+border, paint);
			if (images[2]!=null) canvas.drawBitmap(images[2], landscapeWidth+border, statusBarHeight+border, paint);
	
			if (images[3]!=null) canvas.drawBitmap(images[3], border, statusBarHeight+portraitHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);
		
			if (images[5]!=null) canvas.drawBitmap(images[5], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
			if (images[6]!=null) canvas.drawBitmap(images[6], portraitWidth+landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);			
		}
	}		
		
	private void topRightBottomLeftBottomRight(Canvas canvas) {
		// portrait
		if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
		
		// landscape
		if (isPortraitOrientation) {
			if (images[1]!=null) canvas.drawBitmap(images[1], landscapeWidth+border, statusBarHeight+border, paint);
			if (images[2]!=null) canvas.drawBitmap(images[2], landscapeWidth+border, statusBarHeight+landscapeHeight+border, paint);
	
			if (images[3]!=null) canvas.drawBitmap(images[3], border, statusBarHeight+portraitHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], border, statusBarHeight+portraitHeight+landscapeHeight+border, paint);
		
			if (images[5]!=null) canvas.drawBitmap(images[5], landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);
			if (images[6]!=null) canvas.drawBitmap(images[6], landscapeWidth+border, statusBarHeight+portraitHeight+landscapeHeight+border, paint);
		} else {
			if (images[1]!=null) canvas.drawBitmap(images[1], portraitWidth+border, statusBarHeight+border, paint);
			if (images[2]!=null) canvas.drawBitmap(images[2], portraitWidth+landscapeWidth+border, statusBarHeight+border, paint);
	
			if (images[3]!=null) canvas.drawBitmap(images[3], border, statusBarHeight+portraitHeight+border, paint);
			if (images[4]!=null) canvas.drawBitmap(images[4], border+landscapeWidth, statusBarHeight+portraitHeight+border, paint);
		
			if (images[5]!=null) canvas.drawBitmap(images[5], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);
			if (images[6]!=null) canvas.drawBitmap(images[6], portraitWidth+landscapeWidth+border, statusBarHeight+portraitHeight+border, paint);			
		}
	}
}
