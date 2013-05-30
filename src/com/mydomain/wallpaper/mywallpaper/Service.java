package com.mydomain.wallpaper.mywallpaper;

import rajawali.wallpaper.Wallpaper;
import android.content.Context;
import android.content.SharedPreferences;

public class Service extends Wallpaper  implements SharedPreferences.OnSharedPreferenceChangeListener{
	private Renderer mRenderer;
	private SharedPreferences settings;

	public Engine onCreateEngine() {
		mRenderer = new Renderer(this);
		settings = this.getSharedPreferences(SHARED_PREFS_NAME,
				Context.MODE_PRIVATE);

		settings.registerOnSharedPreferenceChangeListener(this);
        onSharedPreferenceChanged(settings, null);
		return new WallpaperEngine(settings, getBaseContext(), mRenderer, false);
	}


	@Override
	public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
		// TODO Auto-generated method stub
		
		mRenderer.pointLightPower = Float.parseFloat(prefs.getString("point_light_power", "100"));
		mRenderer.directLightPower = Float.parseFloat(prefs.getString("direct_light_power", "100"));
		mRenderer.spotLightPower = Float.parseFloat(prefs.getString("spot_light_power", "100"));	
		mRenderer.coneAngle = Float.parseFloat(prefs.getString("spot_light_cone", "100"));	
		
	}
}
