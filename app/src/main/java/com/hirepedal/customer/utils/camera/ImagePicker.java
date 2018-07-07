package com.hirepedal.customer.utils.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class ImagePicker {

    public static final String DEFAULT_FILE_LOCATION = "Titan/NFC/JPG/";

    public static final String DEFAULT_PREFIX = "Titan_IMG_";

    public static final String DEFAULT_EXTENSION = ".jpg";

    private Context mContext;

    private static ImagePicker singleInstance;


    private ImagePicker(Context context) {
        mContext = context;
    }

    public static ImagePicker getInstance(Context context) {
        if (singleInstance == null) {
            singleInstance = new ImagePicker(context);
        }
        return singleInstance;
    }

    public Bitmap processCameraImageWithCompression(Context context, File f, int compressionPercentage) throws NullPointerException {
        if (f == null) {
            throw new NullPointerException("parameter uri is null.");
        }
        try {
            Bitmap bm = BitmapFactory.decodeFile(f.getAbsolutePath());
            Log.e("bitmaps",bm.getHeight()+","+bm.getWidth());
            if(bm.getWidth() >2048){
                bm = Bitmap.createScaledBitmap(bm, bm.getWidth() / 4, bm.getHeight() / 4, true);
            }else if(bm.getWidth()==1280 && bm.getHeight() == 720){
                bm = Bitmap.createScaledBitmap(bm, bm.getWidth(), bm.getHeight(), true);
            }else if(bm.getWidth() > 960){
                bm = Bitmap.createScaledBitmap(bm, bm.getWidth() / 2, bm.getHeight() / 2, true);
            }else if (bm.getWidth()<960){
                bm = Bitmap.createScaledBitmap(bm, bm.getWidth(), bm.getHeight(), true);
            }
            Log.e("after compr bitmaps",bm.getHeight()+","+bm.getWidth());

            String imageFileName = getFilename(null);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFileName);

            bm.compress(Bitmap.CompressFormat.JPEG, compressionPercentage, fileOutputStream);
            bm.recycle();
            bm = null;

            Bitmap compressedBitmap = BitmapFactory.decodeFile(imageFileName);

            //delete the file.
            File file = new File(imageFileName);
            file.delete();

            try {
                ExifInterface exifInterface = new ExifInterface(f.getAbsolutePath());
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                switch (orientation) {
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        compressedBitmap = rotateImage(compressedBitmap, 90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        compressedBitmap = rotateImage(compressedBitmap, 180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        compressedBitmap = rotateImage(compressedBitmap, 270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                        break;

                    default:
                        break;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            return compressedBitmap;
        } catch (IllegalArgumentException e) {
            if (compressionPercentage < 0 || compressionPercentage > 100) {
                throw new IllegalArgumentException("compression percentage must be 0..100");
            } else {
                e.printStackTrace();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix,
                true);
    }

    public String getFilename(@Nullable String locationForFiles) {
        File file = null;

        if (locationForFiles == null || locationForFiles.length() == 0) {
            file = new File(Environment.getExternalStorageDirectory().getPath(), DEFAULT_FILE_LOCATION);//if No name is provided.
        } else {
            file = new File(Environment.getExternalStorageDirectory().getPath(), locationForFiles);
        }

        if (!file.exists()) {
            file.mkdirs();//creating folder if not already exist.
        }

        //generating file name including folder name
        String uriSting = (file.getAbsolutePath() + "/" + System.currentTimeMillis() + ".jpg");
        return uriSting;
    }

    public File createFile(@Nullable String locationForFiles, @Nullable String prefix, @Nullable String extensionName) throws IOException {
        if (locationForFiles == null || locationForFiles.length() == 0) {
            locationForFiles = DEFAULT_FILE_LOCATION;
        }

        if (prefix == null || prefix.length() == 0) {
            prefix = DEFAULT_PREFIX+ System.currentTimeMillis();//if No name is provided.
        }

        if (extensionName == null || extensionName.length() == 0) {
            extensionName = DEFAULT_EXTENSION;//if No extension is provided.
        }

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/"+locationForFiles);

        if (!file.exists()) {
            file.mkdirs();//creating folder if not already exist.
        }

        return File.createTempFile(prefix, extensionName, file);

    }
}
