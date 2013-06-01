package com.mydomain.wallpaper.mywallpaper;

import rajawali.math.Vector3;
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
		
		mRenderer.pointLightPower = Float.parseFloat(prefs.getString("point_light_power", "0"));
		mRenderer.directLightPower = Float.parseFloat(prefs.getString("direct_light_power", "50"));
		mRenderer.spotLightPower = Float.parseFloat(prefs.getString("spot_light_power", "10"));	
		mRenderer.coneAngle = Float.parseFloat(prefs.getString("spot_light_cone", "1"));
		
		mRenderer.spotPos = new Vector3 (
					Float.parseFloat(prefs.getString("spot_pos_x", "0")), 
					Float.parseFloat(prefs.getString("spot_pos_y", "0")), 	
					Float.parseFloat(prefs.getString("spot_pos_z", "50")));
		mRenderer.spotDir = new Vector3(
					Float.parseFloat(prefs.getString("spot_dir_x", "0")),
					Float.parseFloat(prefs.getString("spot_dir_y", "0")),
					Float.parseFloat(prefs.getString("spot_dir_z", "0")));
		mRenderer.spotLookAt = new Vector3(
					Float.parseFloat(prefs.getString("spot_look_x", "0")),
					Float.parseFloat(prefs.getString("spot_look_y", "0")),
					Float.parseFloat(prefs.getString("spot_look_z", "0")));
		
		mRenderer.directPos = new Vector3 (
				Float.parseFloat(prefs.getString("direct_pos_x", "0")), 
				Float.parseFloat(prefs.getString("direct_pos_y", "50")), 	
				Float.parseFloat(prefs.getString("direct_pos_z", "0")));
		mRenderer.directDir = new Vector3(
				Float.parseFloat(prefs.getString("direct_dir_x", "0")),
				Float.parseFloat(prefs.getString("direct_dir_y", "0")),
				Float.parseFloat(prefs.getString("direct_dir_z", "0")));
		mRenderer.directLookAt = new Vector3(
				Float.parseFloat(prefs.getString("direct_look_x", "0")),
				Float.parseFloat(prefs.getString("direct_look_y", "0")),
				Float.parseFloat(prefs.getString("direct_look_z", "0")));
		
		mRenderer.pointPos = new Vector3 (
				Float.parseFloat(prefs.getString("point_pos_x", "0")), 
				Float.parseFloat(prefs.getString("point_pos_y", "0")), 	
				Float.parseFloat(prefs.getString("point_pos_z", "0")));
		
		mRenderer.animateCube = prefs.getBoolean("animateCube", true);

		mRenderer.Fov = Float.parseFloat(prefs.getString("FoV", "60"));
		mRenderer.camPos = new Vector3 (
				Float.parseFloat(prefs.getString("cam_pos_x", "0")), 
				Float.parseFloat(prefs.getString("cam_pos_y", "0")), 	
				Float.parseFloat(prefs.getString("cam_pos_z", "20")));
		mRenderer.camLookAt = new Vector3 (
				Float.parseFloat(prefs.getString("cam_look_x", "0")), 
				Float.parseFloat(prefs.getString("cam_look_y", "0")), 	
				Float.parseFloat(prefs.getString("cam_look_z", "0")));
		
		mRenderer.car = prefs.getBoolean("showCar", false);
		mRenderer.cube = prefs.getBoolean("showCube", true);
		
		
//		mRenderer.settings = true;
	}
}
