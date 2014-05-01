package pohkahkong.livewallpaper.collage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class Preferences extends PreferenceActivity implements OnPreferenceClickListener, OnPreferenceChangeListener {	
	private Preference path;
	private Preference transitionSpeed2;
	private Preference imageNumber;
	private Preference hasBorder;
	private Preference borderColour;
	private Preference borderThickness;
	private Preference rating;
	@SuppressWarnings("unused")
	private Preference donation;
	private Preference contact;

	private PathDialog pathDialog;
	private ImageNumberDialog imageNumberDialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);		
		
		path = findPreference("path");
		transitionSpeed2 = findPreference("transitionSpeed2");
		imageNumber = findPreference("imageNumber");
		hasBorder = findPreference("hasBorder");
		borderColour = findPreference("borderColour");
		borderThickness = findPreference("borderThickness");
		rating = findPreference("rating");	
		//donation = findPreference("donation");
		contact = findPreference("contact");	
		
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		transitionSpeed2.setSummary(pref.getString("transitionSpeed2", ""));
		borderColour.setSummary(pref.getString("borderColour", ""));
		borderThickness.setSummary(pref.getString("borderThickness", ""));
		
		if (pref.getBoolean("hasBorder", false)) {
			borderColour.setEnabled(true);
			borderThickness.setEnabled(true);
		} else {
			borderColour.setEnabled(false);
			borderThickness.setEnabled(false);				
		}						
		
		path.setOnPreferenceClickListener(this);
		imageNumber.setOnPreferenceClickListener(this);
		rating.setOnPreferenceClickListener(this);
		//donation.setOnPreferenceClickListener(this);
		contact.setOnPreferenceClickListener(this);
		
		transitionSpeed2.setOnPreferenceChangeListener(this);
		hasBorder.setOnPreferenceChangeListener(this);
		borderColour.setOnPreferenceChangeListener(this);
		borderThickness.setOnPreferenceChangeListener(this);

		pathDialog = new PathDialog(this, path);
		imageNumberDialog = new ImageNumberDialog(this, imageNumber);		
	}
	
	public boolean onPreferenceClick(Preference pref) {
		// TODO Auto-generated method stub
		if (pref.getKey().equals("path"))
			pathDialog.show();
		else if (pref.getKey().equals("imageNumber"))
			imageNumberDialog.show();
		else if (pref.getKey().equals("rating")) {				
			Uri uri = Uri.parse( "https://play.google.com/store/apps/details?id=pohkahkong.livewallpaper.collage&feature=search_result#?t=W251bGwsMSwxLDEsInBvaGthaGtvbmcubGl2ZXdhbGxwYXBlci5jb2xsYWdlIl0." );
			startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
		} else if (pref.getKey().equals("contact")) {				
			Uri uri = Uri.parse( "https://www.facebook.com/CollageLiveWallpaper" );
			startActivity( new Intent( Intent.ACTION_VIEW, uri ) );
		}		
		return true;
	}	

	public boolean onPreferenceChange(Preference pref, Object value) {
		// TODO Auto-generated method stub
		if (pref.getKey().equals("transitionSpeed2")) 
			transitionSpeed2.setSummary(value.toString());
		else if (pref.getKey().equals("hasBorder")) {
			if (value.toString().equals("true")) {
				borderColour.setEnabled(true);
				borderThickness.setEnabled(true);
			} else {
				borderColour.setEnabled(false);
				borderThickness.setEnabled(false);				
			}						
		}
		else if (pref.getKey().equals("borderColour")) 
			borderColour.setSummary(value.toString());
		else if (pref.getKey().equals("borderThickness")) 
			borderThickness.setSummary(value.toString());			
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();	
		pathDialog.dismiss();
	}	
}
