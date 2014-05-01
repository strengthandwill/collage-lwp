package pohkahkong.livewallpaper.collage;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class ImageNumberDialog extends Dialog implements OnClickListener {	
	private Preference preference;
	
	private String imageNumber;
	private CheckBox oneImageCollageCB;
	private CheckBox twoImagesCollageCB;
	private CheckBox threeImagesCollageCB;
	private CheckBox fourImagesCollageCB;
	private CheckBox fiveImagesCollageCB;
	private CheckBox sixImagesCollageCB;
	private CheckBox sevenImagesCollageCB;
	private CheckBox eightImagesCollageCB;
	private Button okBtn;
		
	private SharedPreferences preferences;
	private String key = "imageNumber";

	public ImageNumberDialog(Context context, Preference preference) {
		super(context, android.R.style.Theme_Light);		
		// TODO Auto-generated constructor stub
		this.preference = preference;
		setContentView(R.layout.imagenumber);
		this.setTitle("Image number");
		
		preferences = PreferenceManager.getDefaultSharedPreferences(context);				
		imageNumber = preferences.getString(key, "");			
		preference.setSummary(imageNumber);	
		
		oneImageCollageCB = (CheckBox) findViewById(R.id.oneImageCollageCB);
		twoImagesCollageCB = (CheckBox) findViewById(R.id.twoImagesCollageCB);
		threeImagesCollageCB = (CheckBox) findViewById(R.id.threeImagesCollageCB);
		fourImagesCollageCB = (CheckBox) findViewById(R.id.fourImagesCollageCB);
		fiveImagesCollageCB = (CheckBox) findViewById(R.id.fiveImagesCollageCB);
		sixImagesCollageCB = (CheckBox) findViewById(R.id.sixImagesCollageCB);
		sevenImagesCollageCB = (CheckBox) findViewById(R.id.sevenImagesCollageCB);
		eightImagesCollageCB = (CheckBox) findViewById(R.id.eightImagesCollageCB);
		okBtn = (Button) findViewById(R.id.okBtn);
		
		oneImageCollageCB.setChecked(false);
		twoImagesCollageCB.setChecked(false);
		threeImagesCollageCB.setChecked(false);
		fourImagesCollageCB.setChecked(false);
		fiveImagesCollageCB.setChecked(false);
		sixImagesCollageCB.setChecked(false);
		sevenImagesCollageCB.setChecked(false);
		eightImagesCollageCB.setChecked(false);
		okBtn.setOnClickListener(this);
		
		String[] values = imageNumber.split(",");
		for (int i=0; i<values.length; i++) {
			switch (Integer.parseInt(values[i])) {
				case 1: oneImageCollageCB.setChecked(true);
						break;
				case 2: twoImagesCollageCB.setChecked(true);
						break;
				case 3: threeImagesCollageCB.setChecked(true);
						break;
				case 4: fourImagesCollageCB.setChecked(true);
						break;
				case 5: fiveImagesCollageCB.setChecked(true);
						break;
				case 6: sixImagesCollageCB.setChecked(true);
						break;
				case 7: sevenImagesCollageCB.setChecked(true);
						break;
				case 8: eightImagesCollageCB.setChecked(true);
						break;										
			}
		}		
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId()==R.id.okBtn) {
			String newImageNumber = "";
			if (oneImageCollageCB.isChecked())
				newImageNumber += "1,";
			if (twoImagesCollageCB.isChecked())
				newImageNumber += "2,";
			if (threeImagesCollageCB.isChecked())
				newImageNumber += "3,";
			if (fourImagesCollageCB.isChecked())
				newImageNumber += "4,";
			if (fiveImagesCollageCB.isChecked())
				newImageNumber += "5,";
			if (sixImagesCollageCB.isChecked())
				newImageNumber += "6,";
			if (sevenImagesCollageCB.isChecked())
				newImageNumber += "7,";
			if (eightImagesCollageCB.isChecked())
				newImageNumber += "8,";

			if (!newImageNumber.equals("")) {
				imageNumber = newImageNumber.substring(0, newImageNumber.length()-1);
				Editor editor = preferences.edit();
				editor.putString(key, imageNumber);
				editor.commit();
				preference.setSummary(imageNumber);			    	    
			}
			hide();
		}		
	}
}

