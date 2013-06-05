package com.mydomain.wallpaper.mywallpaper;

import java.util.ArrayList;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.Camera;
import rajawali.animation.Animation3D;
import rajawali.animation.RotateAnimation3D;
import rajawali.lights.ALight;
import rajawali.lights.DirectionalLight;
import rajawali.lights.PointLight;
import rajawali.lights.SpotLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.materials.PhongMaterial;
import rajawali.math.Vector3;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.fbx.FBXParser;
import rajawali.primitives.Cube;
import rajawali.primitives.Sphere;
import rajawali.renderer.RajawaliRenderer;
import rajawali.util.RajLog;
import android.content.Context; 

public class Renderer extends RajawaliRenderer{
	private Animation3D mAnim;
	float pointLightPower;
	float spotLightPower;
	float directLightPower;
	float coneAngle;
	Vector3 spotPos;
	Vector3 spotDir;
	Vector3 spotLookAt;
	Vector3 directPos;
	Vector3 directLookAt;
	Vector3 directDir;
	Vector3 pointPos;
	boolean animateCube = true;
	Vector3 camPos;
	Vector3 camLookAt;
	float Fov;
	boolean car;
	boolean cube;
	boolean settings;
	public Renderer(Context context) {
		super(context);		
	}

	public void initScene() {
		Sphere obj = new Sphere(5, 10, 10);
		obj.setMaterial(new DiffuseMaterial());
		
		RajLog.systemInformation();
		Camera cam = new Camera();
		cam.setPosition(20, 0, 0);
		cam.setFieldOfView(60);
		cam.setLookAt(0, 0, 0);

		Vector3 axis = new Vector3(10, 10, 0);
		axis.normalize();
		mAnim = new RotateAnimation3D(axis, 360);
		mAnim.setDuration(8000);
		mAnim.setRepeatMode(Animation3D.RepeatMode.INFINITE);
		
		try {
			// -- Model by Sampo Rask (http://www.blendswap.com/blends/characters/low-poly-rocks-character/)
			FBXParser parser = new FBXParser(this, R.raw.untitled);
			parser.parse();
			BaseObject3D o = parser.getParsedObject();

			addChild(o);
//			mAnim.setTransformable3D(o);
			mAnim.setTransformable3D(o.getChildByName("Model::Cube"));
			obj.addLight(o.getLight(0));
			obj.addLight(o.getLight(1));
			obj.addLight(o.getLight(2));
			
			cam.setFieldOfView(getCurrentCamera().getFieldOfView());
			cam.setPosition(getCurrentCamera().getPosition());
			cam.setLookAt(getCurrentCamera().getLookAt());
			
		mAnim.setTransformable3D(o);
		} catch(ParsingException e) {
			e.printStackTrace();
		} 
		
		addChild(obj);
		getScene(0).registerAnimation(mAnim);
		getScene(0).switchCamera(cam);
		
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		super.onSurfaceCreated(gl, config);
		if(animateCube)
			mAnim.play();
		else mAnim.pause();
		Camera cam = getCurrentCamera();
		cam.setPosition(camPos);
		cam.setFieldOfView(Fov);
		cam.setLookAt(camLookAt);		
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
	
		ArrayList<BaseObject3D> o = getCurrentScene().getChildrenCopy();
		o.get(0).setVisible(cube);
		o.get(1).setVisible(car);
		if(o != null){
			
			for(int n = 0; n<3; n++){
				ALight light = o.get(0).getLight(n);	
				if(light.getLightType() == ALight.POINT_LIGHT){
					light.setPower(pointLightPower/100);
					light.setPosition(pointPos);
				}
				else if(light.getLightType() == ALight.SPOT_LIGHT){
					SpotLight sp = (SpotLight) light;
					sp.setPower(spotLightPower/100);
					sp.setPosition(spotPos);
					sp.setDirection(spotDir);
					sp.setLookAt(spotLookAt);
					sp.setCutoffAngle(coneAngle);
				}
				else if(light.getLightType() == ALight.DIRECTIONAL_LIGHT){
					DirectionalLight dl = (DirectionalLight) light;
					dl.setPower(directLightPower/100);
					dl.setPosition(directPos);
					dl.setDirection(directDir);
					dl.setLookAt(directLookAt);
				}				
								
			}
		}
		
	}

}
