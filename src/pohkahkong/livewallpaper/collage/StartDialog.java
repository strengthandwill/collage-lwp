package pohkahkong.livewallpaper.collage;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * 
 * @author Poh Kah Kong
 *
 */
public class StartDialog extends Activity implements OnClickListener {
	private final String androidRateUri = "https://play.google.com/store/apps/details?id=pohkahkong.livewallpaper.collage&feature=search_result#?t=W251bGwsMSwxLDEsInBvaGthaGtvbmcubGl2ZXdhbGxwYXBlci5jb2xsYWdlIl0.";
	private final String facebookLikeUri = "https://www.facebook.com/CollageLiveWallpaper";
	private final String rainbowUri = "https://play.google.com/store/apps/details?id=pohkahkong.game.rainbow&feature=more_from_developer#?t=W251bGwsMSwxLDEwMiwicG9oa2Foa29uZy5nYW1lLnJhaW5ib3ciXQ..";
	
	private LinearLayout applicationLL;
	private LinearLayout androidRateLL;
	private LinearLayout facebookLikeLL;
	private LinearLayout rainbowLL;
	private LinearLayout closeLL;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
        
        applicationLL = (LinearLayout) findViewById(R.id.applicationLL);
        androidRateLL = (LinearLayout) findViewById(R.id.androidRateLL);
        facebookLikeLL = (LinearLayout) findViewById(R.id.facebookLikeLL);
        rainbowLL = (LinearLayout) findViewById(R.id.rainbowLL);
        closeLL = (LinearLayout) findViewById(R.id.closeLL);
        
        applicationLL.setOnClickListener(this);
        androidRateLL.setOnClickListener(this);
        facebookLikeLL.setOnClickListener(this);
        rainbowLL.setOnClickListener(this);
        closeLL.setOnClickListener(this);
        
        Toast msg = Toast.makeText(this, "Double tap on the screen to\ndrag or zoom the wallpaper!", Toast.LENGTH_LONG);
        msg.show();
    }

	public void onClick(View view) {
		// TODO Auto-generated method stub
		if (view.getId()==R.id.applicationLL) {			
			Intent intent = new Intent();
		    intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
		    finish();
		    startActivity(intent);						
		} else if (view.getId()==R.id.androidRateLL)
			goWebSite(androidRateUri);		
		else if (view.getId()==R.id.facebookLikeLL)
			goWebSite(facebookLikeUri);
		else if (view.getId()==R.id.rainbowLL)
			goWebSite(rainbowUri);		
		else if (view.getId()==R.id.closeLL)
			finish();		
	}
	
	private void goWebSite(String uriStr) {
		Uri uri = Uri.parse(uriStr);
		finish();
		startActivity( new Intent( Intent.ACTION_VIEW, uri ) );		
	}
}