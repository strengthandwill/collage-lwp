package pohkahkong.livewallpaper.collage;

import pohkahkong.livewallpaper.collage.CollageWallpaperService.CollageWallpaperEngine;
import android.graphics.Canvas;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class OneImageCollage extends Collage {	
	public OneImageCollage(CollageWallpaperEngine wallpaperEngine, ImageDevice imageDevice, int width, int height, int statusBarHeight, 
							boolean hasBorder, int borderColour, Border borderThickness, float initialScaleFactor, boolean isLockTransformation) {
		super(wallpaperEngine, imageDevice, width, height, statusBarHeight, hasBorder, borderColour, borderThickness, initialScaleFactor, isLockTransformation);		
		// TODO Auto-generated constructor stub	
		imageDevice.loadOneImageCollage(this, portraitWidth-border*2, portraitHeight-border*2);
	}
	
	@Override
	protected void initDimension() {
		// TODO Auto-generated method stub		
		portraitWidth = width;
		portraitHeight = height;
	}

	@Override
	public void draw(Canvas canvas) {		
		// TODO Auto-generated method stub
		super.draw(canvas);
		// portrait
		if (images[0]!=null)
			canvas.drawBitmap(images[0], border, statusBarHeight+border, paint);		
	}
}
