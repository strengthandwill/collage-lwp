package pohkahkong.livewallpaper.collage;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class PathDialog extends Dialog implements OnItemClickListener, OnClickListener {
	// general
	private Preferences preferencesActivity;
    private GridView galleryGV;
    private Button selectBtn;
    private TextView folderTV;
    private Preference preference;
    private ImageItem[] itemsArray;
	private int count;
    
    // preferences
	private SharedPreferences preferences;
	private String externalSdPath;
	private String key = "path";
    private String path;


	public PathDialog(Preferences preferencesActivity, Preference preference) {
		super(preferencesActivity, android.R.style.Theme_Light);
		this.preferencesActivity = preferencesActivity;
		this.preference = preference;
		setContentView(R.layout.path);		
		setTitle("Choose folder");
		
		preferences = PreferenceManager.getDefaultSharedPreferences(preferencesActivity);
		externalSdPath = Environment.getExternalStorageDirectory().getPath();
		path = preferences.getString(key, externalSdPath);	
		preference.setSummary(path);		
		
		galleryGV = (GridView) findViewById(R.id.galleryGV);
		selectBtn = (Button)  findViewById(R.id.selectBtn);
		folderTV = (TextView)  findViewById(R.id.folderTV);		
		
		initGridView();
		galleryGV.setOnItemClickListener(this);
		selectBtn.setOnClickListener(this);
	}
	
	private void initGridView() {
		ArrayList<ImageItem> items = new ArrayList<ImageItem>();
		try {
			final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
			final String orderBy = MediaStore.Images.Media._ID;
			Cursor imagecursor = preferencesActivity.managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 
					columns, null, null, orderBy);
			int image_column_index = imagecursor.getColumnIndex(MediaStore.Images.Media._ID);
			count = imagecursor.getCount();

			ArrayList<String> paths = new ArrayList<String>();
			for (int i = 0; i < count; i++) {			
				imagecursor.moveToPosition(i);
				int id = imagecursor.getInt(image_column_index);
				int dataColumnIndex = imagecursor.getColumnIndex(MediaStore.Images.Media.DATA);
				String path = imagecursor.getString(dataColumnIndex);
				if (i==0 || (!path.substring(path.length()-4).equalsIgnoreCase(".png") && !paths.contains(getParentPath(path)))) {
					Log.i("PathDialog", getParentPath(path));	
					Bitmap thumbnail = MediaStore.Images.Thumbnails.getThumbnail(preferencesActivity.getApplicationContext().getContentResolver(), id, MediaStore.Images.Thumbnails.MICRO_KIND, null);				
					items.add(new ImageItem(thumbnail, getParentPath(path)));		
					paths.add(getParentPath(path));
				}											
			}
			imagecursor.close();
		} catch (Exception e) {			
		}
		itemsArray = new ImageItem[items.size()];
		items.toArray(itemsArray);		
		galleryGV.setAdapter(new ImageAdapter(preferencesActivity, itemsArray));		
	}
	
	private String getParentPath(String path) {
    	return path.substring(0, path.lastIndexOf("/"));
    }

	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub
		path = itemsArray[position].getPath();
		folderTV.setText(path.substring(path.lastIndexOf("/")+1, path.length()));
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId()==R.id.selectBtn) {
			Editor editor = preferences.edit();
			editor.putString(key, path);
			editor.commit();
			preference.setSummary(path);
			hide();			
		}
	}
}
