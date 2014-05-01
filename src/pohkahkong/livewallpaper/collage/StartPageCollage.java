package pohkahkong.livewallpaper.collage;

import pohkahkong.livewallpaper.collage.CollageWallpaperService.CollageWallpaperEngine;
import android.graphics.Canvas;
import android.graphics.Color;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class StartPageCollage extends Collage {		
	public StartPageCollage(CollageWallpaperEngine wallpaperEngine, ImageDevice imageDevice, int width, int height, 
							float initialScaleFactor, boolean isLockTransformation) {		
		super(wallpaperEngine, imageDevice, width, height, 0, false, Color.WHITE, Border.MEDIUM, initialScaleFactor, isLockTransformation);		
		// TODO Auto-generated constructor stub	
		imageDevice.loadStartPageCollage(this, width, height);
	}
	
	@Override
	protected void initDimension() {
		// TODO Auto-generated method stub		
	}	

	@Override
	public void draw(Canvas canvas) {		
		// TODO Auto-generated method stub
		super.draw(canvas);
		if (images[0]!=null)
			canvas.drawBitmap(images[0], 0, 0, paint);		
	}

}
