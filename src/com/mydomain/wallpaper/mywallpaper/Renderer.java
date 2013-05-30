package com.mydomain.wallpaper.mywallpaper;

import java.util.ArrayList;
import java.util.Map;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.ALight;
import rajawali.lights.DirectionalLight;
import rajawali.lights.PointLight;
import rajawali.lights.SpotLight;
import rajawali.math.Vector3;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.fbx.FBXParser;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.content.SharedPreferences;

public class Renderer extends RajawaliRenderer{
	private Animation3D mAnim;
	private SharedPreferences settings;
	float pointLightPower;
	float spotLightPower;
	float directLightPower;
	float coneAngle;

	public Renderer(Context context) {
		super(context);		
	}

	public void initScene() {
		ALight light = new DirectionalLight(1,0,0);
		light.setPower(0);
		light.setColor(1, 1, 0);
		light.setPosition(0, 3, 0);
		
		Vector3 axis = new Vector3(10, 10, 10);
		axis.normalize();
		mAnim = new RotateAnimation3D(axis, 360);
		mAnim.setDuration(8000);
		mAnim.setRepeatMode(Animation3D.RepeatMode.INFINITE);
//		mAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		
		try {
			// -- Model by Sampo Rask (http://www.blendswap.com/blends/characters/low-poly-rocks-character/)
			FBXParser parser = new FBXParser(this, R.raw.untitled);
			parser.parse();
			BaseObject3D o = parser.getParsedObject();

			addChild(o);
//			mAnim.setTransformable3D(o);
			mAnim.setTransformable3D(o.getChildByName("Model::Cube"));
			if(o.getLight(0).getLightType() == 0)
				o.getLight(0).setPower(pointLightPower/100);
			if(o.getLight(1).getLightType() == 1)
				o.getLight(1).setPower(spotLightPower/100);
			if(o.getLight(2).getLightType() == 2)
				o.getLight(2).setPower(directLightPower/100);
			
//			o.addLight(light);
			
		} catch(ParsingException e) {
			e.printStackTrace();
		} 
		
		getScene(0).registerAnimation(mAnim);
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		mAnim.play();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);

		ArrayList<BaseObject3D> o = getCurrentScene().getChildrenCopy();
		if(o != null){
			o.get(0).getLight(0).setPower(pointLightPower/100);
			o.get(0).getLight(2).setPower(directLightPower);
			o.get(0).getLight(1).setPower(spotLightPower/100);
			SpotLight sp = (SpotLight) o.get(0).getLight(1);
			sp.setCutoffAngle(coneAngle);
			
		}
	}

}
