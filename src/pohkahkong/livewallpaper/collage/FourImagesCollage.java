package pohkahkong.livewallpaper.collage;

import pohkahkong.livewallpaper.collage.CollageWallpaperService.CollageWallpaperEngine;
import android.graphics.Canvas;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class FourImagesCollage extends Collage {	
	public FourImagesCollage(CollageWallpaperEngine wallpaperEngine, ImageDevice imageDevice, int width, int height, int statusBarHeight, 
								boolean hasBorder, int borderColour, Border borderThickness, float initialScaleFactor, boolean isLockTransformation) {
		super(wallpaperEngine, imageDevice, width, height, statusBarHeight, hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);
		// TODO Auto-generated constructor stub		
		imageDevice.loadFourImagesCollage(this, portraitWidth-border*2, portraitHeight-border*2);
	}

	@Override
	protected void initDimension() {
		// TODO Auto-generated method stub
		portraitWidth = width/2;
		portraitHeight = height/2;
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		if (images[0]!=null) canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);
		if (images[1]!=null) canvas.drawBitmap(images[1], portraitWidth+border, statusBarHeight+border, paint);
		if (images[2]!=null) canvas.drawBitmap(images[2], border, statusBarHeight+portraitHeight+border, paint);
		if (images[3]!=null) canvas.drawBitmap(images[3], portraitWidth+border, statusBarHeight+portraitHeight+border, paint);		
	}
}

