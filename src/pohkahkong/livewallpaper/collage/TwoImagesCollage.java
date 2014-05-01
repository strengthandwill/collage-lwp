package pohkahkong.livewallpaper.collage;

import pohkahkong.livewallpaper.collage.CollageWallpaperService.CollageWallpaperEngine;
import android.graphics.Canvas;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class TwoImagesCollage extends Collage {
	public TwoImagesCollage(CollageWallpaperEngine wallpaperEngine, ImageDevice imageDevice, int width, int height, int statusBarHeight, 
							boolean hasBorder, int borderColour, Border borderThickness, float initialScaleFactor, boolean isLockTransformation) {
		super(wallpaperEngine, imageDevice, width, height, statusBarHeight, hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
		// TODO Auto-generated constructor stub
		imageDevice.loadTwoImagesCollage(this, landscapeWidth-border*2, landscapeHeight-border*2);
	}
	
	@Override
	protected void initDimension() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		super.draw(canvas);
		// portrait	
		if (isPortraitOrientation) {
			if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
			if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+landscapeHeight+border, paint);
		} else {
			if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
			if (images[1]!=null) canvas.drawBitmap(images[1], border+landscapeWidth, statusBarHeight+border, paint);
		}		
	}

}
