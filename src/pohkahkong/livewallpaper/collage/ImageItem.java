package pohkahkong.livewallpaper.collage;

import android.graphics.Bitmap;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class ImageItem {
    private Bitmap thumbnail;	
    private String path;
    
    public ImageItem(Bitmap thumbnail, String path) {
    	this.thumbnail = thumbnail;
    	this.path = path;
    }
	
    public Bitmap getThumbnail() {
    	return thumbnail;
    }
    
    public String getPath() {
    	return path;
    }   
}
