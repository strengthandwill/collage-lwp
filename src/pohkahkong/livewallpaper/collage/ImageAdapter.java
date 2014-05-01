package pohkahkong.livewallpaper.collage;

import java.io.File;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class ImageAdapter extends BaseAdapter {
	private ImageItem[] items;
	private LayoutInflater inflater;
		
	public ImageAdapter(Context context, ImageItem[] items) {
		this.items = items;		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}	

	public int getCount() {
		// TODO Auto-generated method stub
		return items.length;		
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub			
		if (convertView == null)
			convertView = inflater.inflate(R.layout.item, null); 					
		ImageView imageView = (ImageView) convertView.findViewById(R.id.imageIV);
		TextView labelTV = (TextView) convertView.findViewById(R.id.labelTV);
		Bitmap image = items[position].getThumbnail();
		String path = items[position].getPath();
		imageView.setImageBitmap(image);
		String folderName = path.substring(path.lastIndexOf("/")+1, path.length());
		if (folderName.length()>9)
			folderName = folderName.substring(0, 7) + "..";		
		labelTV.setText(folderName + " (" + getImageCount(path) + ")");
		return convertView;
	}	
	
	private int getImageCount(String path) {
		int imageCount = 0;
		File directory = new File(path);
		File[] files = directory.listFiles();
		for (int i=0; i<files.length; i++) {
				if ((files[i].getName().length()>4 && files[i].getName().substring(files[i].getName().length()-4).equalsIgnoreCase(".jpg")) ||
					(files[i].getName().length()>5 && files[i].getName().substring(files[i].getName().length()-5).equalsIgnoreCase(".jpeg")))
					imageCount++;								
		}		
		return imageCount;
	}
}
