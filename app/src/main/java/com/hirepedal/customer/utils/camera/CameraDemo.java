package com.hirepedal.customer.utils.camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.hirepedal.customer.R;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CameraDemo extends Activity {
	private static final String TAG = "FrontCamera";
	Camera camera;
	Preview preview;
	ImageView buttonClick;
	Button doneButton;
	ImagePicker imagePicker;
	ArrayList<String> uris;
	android.hardware.Camera.CameraInfo info = new android.hardware.Camera.CameraInfo();
	private List<byte[]> imageData;
	private Bundle bundle;
	private FrameLayout overlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);

		uris = new ArrayList<>();
		imageData = new ArrayList<>();

		bundle = getIntent().getExtras();
		overlay = (FrameLayout) findViewById(R.id.overlay);

		preview = new Preview(this);
		imagePicker = ImagePicker.getInstance(getApplicationContext());
		((FrameLayout) findViewById(R.id.preview)).addView(preview);
		android.hardware.Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);

		buttonClick = (ImageView) findViewById(R.id.buttonClick);
		buttonClick.bringToFront();
		buttonClick.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				overlay.setVisibility(View.VISIBLE);
				try {
					if (preview.isIsSafeToOpenCamera()) {
						preview.camera.takePicture(shutterCallback, rawCallback,
								jpegCallback);
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		});

		doneButton = (Button) findViewById(R.id.done);
		doneButton.setVisibility(View.GONE);

		doneButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("uri",uris);
				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
				imageData.clear();
			}
		});

		Log.d(TAG, "onCreate'd");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG,"onPause");
		if (camera != null) {
			camera.release();
		}
	}

	ShutterCallback shutterCallback = new ShutterCallback() {
		public void onShutter() {
			Log.d(TAG, "onShutter'd");
		}
	};

	PictureCallback rawCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.d(TAG, "onPictureTaken - raw with data = " + ((data != null) ? data.length : " NULL"));
		}
	};

	PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			FileOutputStream outStream = null;
			try {
				if (data != null) {
					new ProcessImage().execute(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {

			}
			Log.d(TAG, "onPictureTaken - jpeg");
			try {
				camera.startPreview();
				overlay.setVisibility(View.INVISIBLE);
			} catch (Exception e) {
				Log.d(TAG, "Error starting preview: " + e.toString());
			}
		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		imageData.clear();
		imageData = null;
	}

	public static Bitmap rotate(Bitmap bitmap, int degree) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix mtx = new Matrix();
		mtx.postRotate(degree);

		return Bitmap.createBitmap(bitmap, 0, 0, w, h, mtx, true);
	}

	public class ProcessImage extends AsyncTask<byte[],Void,List<Uri>> {

		FileOutputStream outStream;

		@Override
		protected List<Uri> doInBackground(byte[]... bytes) {
			try{
				File f = imagePicker.createFile(null,null,null);
				outStream = new FileOutputStream(f);
				outStream.write(bytes[0]);
				outStream.close();

				Bitmap cBmp = imagePicker.processCameraImageWithCompression(CameraDemo.this,f,40);
				File f2 = imagePicker.createFile(null,null,null);
				outStream = new FileOutputStream(f2);
				cBmp.compress(Bitmap.CompressFormat.JPEG,90,outStream);
				outStream.flush();
				outStream.close();
				f.delete();

				uris.add(Uri.fromFile(f2).toString());
			}catch (Exception e){
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			buttonClick.setVisibility(View.GONE);
			doneButton.setVisibility(View.GONE);
		}

		@Override
		protected void onPostExecute(List<Uri> uris) {
			super.onPostExecute(uris);
			buttonClick.setVisibility(View.VISIBLE);
			doneButton.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList("uri",uris);
		Intent intent = new Intent();
		intent.putExtras(bundle);
		setResult(RESULT_CANCELED, intent);
		finish();
	}
}

