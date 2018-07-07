package com.hirepedal.customer.utils.camera;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.hirepedal.customer.R;
import java.io.IOException;
import java.util.List;

class Preview extends SurfaceView implements SurfaceHolder.Callback {
	private static final String TAG = "Preview";

	SurfaceHolder mHolder;
	public Camera camera;
	private Camera.Parameters mParameters;
	private Camera.Size mPreviewSize;
	private static boolean isSafeToOpenCamera = false;

	Preview(Context context) {
		super(context);

		try{
			// Install a SurfaceHolder.Callback so we get notified when the
			// underlying surface is created and destroyed.
			mHolder = getHolder();
			mHolder.addCallback(this);
			mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

			camera = openFrontFacingCamera();
			camera.setDisplayOrientation(90);

			mParameters = camera.getParameters();
			mParameters.setRotation(90);

			List<Camera.Size> cameraSize = mParameters.getSupportedPreviewSizes();
			mPreviewSize = cameraSize.get(0);

			for (Camera.Size s : cameraSize) {
				if ((s.width * s.height) > (mPreviewSize.width * mPreviewSize.height)) {
					mPreviewSize = s;
				}
			}

			List<Camera.Size> sizes = mParameters.getSupportedPictureSizes();

			mParameters.setPreviewSize(mPreviewSize.width,mPreviewSize.height);
			mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
			camera.setParameters(mParameters);
		}catch (NullPointerException e){
			e.printStackTrace();
			AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.CameraAlert);
			builder.setMessage("Unable to start camera. Please ensure that you have enabled camera permission");
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {

				}
			});
			builder.create().show();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// The Surface has been created, acquire the camera and tell it where
		// to draw.

		try{
			if(camera == null){
				camera = openFrontFacingCamera();
				if(camera!=null) {
					camera.setDisplayOrientation(90);
					mParameters = camera.getParameters();
					mParameters.setRotation(90);
				}

				List<Camera.Size> cameraSize = mParameters.getSupportedPreviewSizes();

				List<Camera.Size> sizes = mParameters.getSupportedPictureSizes();

				for(Camera.Size s: sizes){
					Log.e("Size",s.height+","+s.width);
				}

				mPreviewSize = cameraSize.get(0);

				for (Camera.Size s : cameraSize) {
					if ((s.width * s.height) > (mPreviewSize.width * mPreviewSize.height)) {
						mPreviewSize = s;
					}
				}
				mParameters.setPreviewSize(mPreviewSize.width,mPreviewSize.height);
				camera.setParameters(mParameters);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		try {
			camera.setPreviewDisplay(holder);
			camera.setPreviewCallback(new PreviewCallback() {
				public void onPreviewFrame(byte[] data, Camera arg1) {
					Preview.this.invalidate();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when we return, so stop the preview.
		// Because the CameraDevice object is not a shared resource, it's very
		// important to release it when the activity is paused.
		camera.stopPreview();
		isSafeToOpenCamera = false;
		camera = null;
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// Now that the size is known, set up the camera parameters and begin
		// the preview.
		if (camera != null) {
			camera.startPreview();
			isSafeToOpenCamera = true;
		}

	}

	private Camera openFrontFacingCamera() {
		int cameraCount = 0;
		Camera cam = null;
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		cameraCount = Camera.getNumberOfCameras();
		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				try {
					cam = Camera.open(camIdx);
				} catch (RuntimeException e) {
					Log.e(TAG,
							"Camera failed to open: " + e.getLocalizedMessage());
				}
			}
		}
		return cam;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
	}

	public static boolean isIsSafeToOpenCamera(){
		return isSafeToOpenCamera;
	}

}