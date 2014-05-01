package pohkahkong.livewallpaper.collage;

import pohkahkong.livewallpaper.collage.CollageWallpaperService.CollageWallpaperEngine;
import android.graphics.Canvas;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class EightImagesCollage extends Collage {
	public EightImagesCollage(CollageWallpaperEngine wallpaperEngine, ImageDevice imageDevice, int width, int height, int statusBarHeight, 
								boolean hasBorder, int borderColour, Border borderThickness, float initialScaleFactor, boolean isLockTransformation) {
		super(wallpaperEngine, imageDevice, width, height, statusBarHeight, hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);				
		// TODO Auto-generated constructor stub
		imageDevice.loadEightImagesCollage(this, landscapeWidth-border*2, landscapeHeight-border*2);
	}

	@Override
	protected void initDimension() {
		// TODO Auto-generated method stub			
		// portrait
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
		if (isPortraitOrientation) {
			if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
			if (images[1]!=null) canvas.drawBitmap(images[1], border, statusBarHeight+landscapeHeight+border, paint);

			if (images[2]!=null) canvas.drawBitmap(images[2], landscapeWidth+border, statusBarHeight+border, paint);
			if (images[3]!=null) canvas.drawBitmap(images[3], landscapeWidth+border, statusBarHeight+landscapeHeight+border, paint);

			if (images[4]!=null) canvas.drawBitmap(images[4], border, statusBarHeight+landscapeHeight*2+border, paint);
			if (images[5]!=null) canvas.drawBitmap(images[5], border, statusBarHeight+landscapeHeight*3+border, paint);	

			if (images[6]!=null) canvas.drawBitmap(images[6], landscapeWidth+border, statusBarHeight+landscapeHeight*2+border, paint);
			if (images[7]!=null) canvas.drawBitmap(images[7], landscapeWidth+border, statusBarHeight+landscapeHeight*3+border, paint);
		} else {
			if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
			if (images[1]!=null) canvas.drawBitmap(images[1], landscapeWidth+border, statusBarHeight+border, paint);

			if (images[2]!=null) canvas.drawBitmap(images[2], landscapeWidth*2+border, statusBarHeight+border, paint);
			if (images[3]!=null) canvas.drawBitmap(images[3], landscapeWidth*3+border, statusBarHeight+border, paint);

			if (images[4]!=null) canvas.drawBitmap(images[4], border, statusBarHeight+landscapeHeight+border, paint);
			if (images[5]!=null) canvas.drawBitmap(images[5], landscapeWidth+border, statusBarHeight+landscapeHeight+border, paint);	

			if (images[6]!=null) canvas.drawBitmap(images[6], landscapeWidth*2+border, statusBarHeight+landscapeHeight+border, paint);
			if (images[7]!=null) canvas.drawBitmap(images[7], landscapeWidth*3+border, statusBarHeight+landscapeHeight+border, paint);
		}		
	}
}
