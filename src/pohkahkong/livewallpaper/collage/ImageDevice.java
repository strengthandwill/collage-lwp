package pohkahkong.livewallpaper.collage;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class ImageDevice {
	// general
	private Context context;
	private File directory;
	private boolean isPortraitOrientation; 
	private ArrayList<String> unusedImages;
	private boolean hasImages = false;
	private boolean isReset = true;
	private boolean scaleImage;
	
	// dimensions
	private int width;
	private int height;
	
	public ImageDevice(Context context) {		
		this.context = context;				
	}
	
	public void setOrientation(boolean isPortraitOrientation) {
		this.isPortraitOrientation = isPortraitOrientation;
	}
	
	public boolean hasImages() {
		reset();
		return hasImages;
	}
	
	// ****************************************** setters ****************************************** //
	public void setDimension(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void setPath(String path) {
		directory = new File(path);
		reset();
	}
	
	public void setScaleImage(boolean scaleImage) {
		this.scaleImage = scaleImage;
	}
	
	// ****************************************** load images ****************************************** //
	public void reset() {
		unusedImages = new ArrayList<String>();
		isReset = true;

		try {
			File[] unusedImagesArray = directory.listFiles();								
			for (int i=0; i<unusedImagesArray.length; i++) {
				if (unusedImagesArray[i].isFile() && 
					(unusedImagesArray[i].getName().substring(unusedImagesArray[i].getName().length()-4).equalsIgnoreCase(".jpg") ||
					unusedImagesArray[i].getName().substring(unusedImagesArray[i].getName().length()-4).equalsIgnoreCase(".jpeg")))
					unusedImages.add(unusedImagesArray[i].getName());
			}
			if (!unusedImages.isEmpty())
				hasImages = true;
			else
				hasImages = false;
		} catch (Exception e) {
			hasImages = false;
		}		
	}
	
	private String getRandomImage() {
		Iterator<String> iterator =  unusedImages.iterator();
		int random = (int)(Math.random()*unusedImages.size());
		
		String filename = null;
		for (int i=0; i<=random; i++)
			filename = iterator.next();	
		unusedImages.remove(filename);
		return filename;
	}	
	
	private boolean isPortrait(Bitmap bmp) {
		if (bmp.getWidth()<bmp.getHeight())
			return true;
		else
			return false;
	}
	
	private Bitmap cropImage(Bitmap bmp, int width, int height) {
		double widthFactor = (double)bmp.getWidth()/width;
		double heightFactor = (double)bmp.getHeight()/height;						
		if (widthFactor==heightFactor)
			bmp = Bitmap.createScaledBitmap(bmp, width, height, false);	
		else if (widthFactor>heightFactor) {
			bmp = Bitmap.createScaledBitmap(bmp, (int)(bmp.getWidth()/widthFactor), height, false);			
			bmp = Bitmap.createBitmap(bmp, (bmp.getWidth()-width)/2, 0, width, height);								
		} else {
			bmp = Bitmap.createScaledBitmap(bmp, width, (int)(bmp.getHeight()/widthFactor), false);
			bmp = Bitmap.createBitmap(bmp, 0, (bmp.getHeight()-height)/2, width, height);
		}
		return bmp;
	}
	
	public Bitmap[] getImages(	int portrait, int landscape, int portraitWidth, int portraitHeight,
			int landscapeWidth, int landscapeHeight) {		
		if (unusedImages.isEmpty())
			return null;
		else {		
			int portraitCounter = portrait;
			int landscapeCounter = landscape;		
			int portraitIndex = 0;
			int landscapeIndex = portrait;
			Bitmap[] bmps = new Bitmap[portrait + landscape];

			ArrayList<String> readImages = new ArrayList<String>();			
			while (!unusedImages.isEmpty() && (portraitCounter>0 || landscapeCounter>0)) {
				Bitmap bmp = null;
				String filename = getRandomImage();
				try {
					bmp = BitmapFactory.decodeFile(new File(directory, filename).getPath());					
					if (bmp!=null) {
						Bitmap scaledBmp = null;
						if ((isPortraitOrientation&&isPortrait(bmp)) || (!isPortraitOrientation&&!isPortrait(bmp))) {
							if (portraitCounter>0) {								
								if (!scaleImage)
									scaledBmp = cropImage(bmp, portraitWidth, portraitHeight);
								else
									scaledBmp = Bitmap.createScaledBitmap(bmp, portraitWidth, portraitHeight, false);						
								bmps[portraitIndex] = scaledBmp;
								portraitIndex++;
								portraitCounter--;					
							} else
								readImages.add(filename);
						} else {
							if (landscapeCounter>0) {
								if (!scaleImage)
									scaledBmp = cropImage(bmp, landscapeWidth, landscapeHeight);
								else
									scaledBmp = Bitmap.createScaledBitmap(bmp, landscapeWidth, landscapeHeight, false);						
								bmps[landscapeIndex] = scaledBmp;
								landscapeIndex++;
								landscapeCounter--;
							} else
								readImages.add(filename);
						}
						recycle(bmp);
					}
				} catch (Exception e) {
					return null;
				}				
			}		

			if (unusedImages.isEmpty()) {
				if (isReset) {
					reset();
					return bmps;
				} else {				
					reset();				
					return getImages(portrait, landscape, portraitWidth, portraitHeight, landscapeWidth, landscapeHeight);
				}
			} else {					
				// add back read but unused images
				Iterator<String> iterator =  readImages.iterator();
				while (iterator.hasNext())
					unusedImages.add(iterator.next());
				isReset = false;
				return bmps;
			}
		}
	}	
	
	// ****************************************** load collage ****************************************** //	
	public Bitmap[] loadStartPage() {
		Bitmap[] images = new Bitmap[1];
		Bitmap bmp = null;
		if (isPortraitOrientation)
			bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.startpage1);
		else
			bmp = BitmapFactory.decodeResource(context.getResources(), R.drawable.startpage2);
		images[0] = Bitmap.createScaledBitmap(bmp, width, height, false);
		recycle(bmp);
		return images;
	}
	
	public void loadStartPageCollage(Collage collage, int width, int height) {
		Bitmap[] images = loadStartPage();					
		collage.loadImages(images);		
	}
	
	public void loadOneImageCollage(Collage collage, int portraitWidth, int portraitHeight) {
		Bitmap[] images = getImages(1, 0, portraitWidth, portraitHeight, 0, 0);
		collage.loadImages(images);		
	}	
	
	public void loadTwoImagesCollage(Collage collage, int landscapeWidth, int landscapeHeight) {
		Bitmap[] images = getImages(0, 2, 0, 0, landscapeWidth, landscapeHeight);
		collage.loadImages(images);			
	}
	
	public void loadThreeImagesCollage(Collage collage, int portraitWidth, int portraitHeight, int landscapeWidth, int landscapeHeight) {
		Bitmap[] images = getImages(2, 1, portraitWidth, portraitHeight, landscapeWidth, landscapeHeight);
		collage.loadImages(images);
	}

	public void loadFourImagesCollage(Collage collage, int portraitWidth, int portraitHeight) {		
		Bitmap[] images = getImages(4, 0, portraitWidth, portraitHeight, 0, 0);
		collage.loadImages(images);
	}
	
	public void loadFiveImagesCollage(Collage collage, int portraitWidth, int portraitHeight, int landscapeWidth, int landscapeHeight) {
		Bitmap[] images = getImages(3, 2, portraitWidth, portraitHeight, landscapeWidth, landscapeHeight);
		collage.loadImages(images);		
	}	
	
	public void loadSixImagesCollage(Collage collage, int portraitWidth, int portraitHeight, int landscapeWidth, int landscapeHeight) {
		Bitmap[] images = getImages(2, 4,  portraitWidth, portraitHeight, landscapeWidth, landscapeHeight);
		collage.loadImages(images);
	}
	
	public void loadSevenImagesCollage(Collage collage, int portraitWidth, int portraitHeight, int landscapeWidth, int landscapeHeight) {
		Bitmap[] images = getImages(1, 6,  portraitWidth, portraitHeight, landscapeWidth, landscapeHeight);
		collage.loadImages(images);
	}	
	
	public void loadEightImagesCollage(Collage collage, int landscapeWidth, int landscapeHeight) {
		Bitmap[] images = getImages(0, 8,  0, 0, landscapeWidth, landscapeHeight);
		collage.loadImages(images);
	}
		
	// ****************************************** release actions ****************************************** //
	public void recycle(Bitmap bmp) {
		if (bmp!=null) {
			bmp.recycle();
			bmp = null;
		}		
	}
	
	public void recycle(Bitmap[] bmps) {
		if (bmps!=null) {
			for (int i=0; i<bmps.length; i++)
				recycle(bmps[i]);			
		}
	}
}