package com.hdway.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.droid4you.util.cropimage.CropImage;

public class ImageUtils {
	public static final int PICK_FROM_CAMERA = 11;
	public static final int CROP_FROM_CAMERA = 12;
	public static final int PICK_FROM_FILE = 13;

	public static Uri showImageDiaglog(final Activity activity,
			String title) {
		final Uri imageUri = Uri.fromFile(new File(Utils.getExternalDir() + "tmp_image_"
				+ String.valueOf(System.currentTimeMillis())
				+ ".jpg"));
		final String[] items = new String[] { "Take from camera",
				"Select from gallery" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,
				android.R.layout.select_dialog_item, items);
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);

		builder.setTitle(title);
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) { // pick from
																	// camera
				if (item == 0) {
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

					
					intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
							imageUri);

					try {
						intent.putExtra("return-data", true);

						activity.startActivityForResult(intent,
								PICK_FROM_CAMERA);
					} catch (ActivityNotFoundException e) {
						e.printStackTrace();
					}
				} else { // pick from file
					Intent intent = new Intent();

					intent.setType("image/*");
					intent.setAction(Intent.ACTION_GET_CONTENT);

					activity.startActivityForResult(intent, PICK_FROM_FILE);
				}
			}
		});
		builder.create().show();
		return imageUri;
	}

	public static void doCrop(final Activity activity, final String imageURI, String savePath, int width, int height) {
		if( imageURI == null ) {
			return;
		}
		if( imageURI.startsWith("http") ) {
			Toast.makeText(activity, "This is an Online image, which is not supported! Please try other images.",  Toast.LENGTH_LONG).show();
			return;
		}
		Intent intentCrop = new Intent(activity, CropImage.class);
		intentCrop.putExtra("image-path", imageURI);
		intentCrop.putExtra("save-path", savePath);
		intentCrop.putExtra("aspectX", width);
		intentCrop.putExtra("aspectY", height);
		intentCrop.putExtra("outputX", width);
		intentCrop.putExtra("outputY", height);
		intentCrop.putExtra("return-data", true);
		activity.startActivityForResult(intentCrop, CROP_FROM_CAMERA);
	}
	
	public static String getRealPathFromURI(Uri contentURI) {
	    Cursor cursor = Utils.getContext().getContentResolver().query(contentURI, null, null, null, null);
	    if (cursor == null) {
	        return contentURI.getPath();
	    } else { 
	        cursor.moveToFirst(); 
	        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); 
	        return cursor.getString(idx); 
	    }
	}
	
	public static String getImagePath(String path){
		if( path == null ) return null;
		path = path.replaceAll("\r", "");
		path = path.replaceAll("\n", "");
		if( path.contains("{EXT_DIR}/") ) {
			return path.replace("{EXT_DIR}/", Utils.getExternalDir());
		} else if ( path.contains(Utils.getExternalDir()) ) {
			return path.replace(Utils.getExternalDir(), "{EXT_DIR}/");
		} else {
			return path;
		}		
	}
	
	public static boolean saveImageToFile(Bitmap photo, String imageSavedPath) {
		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		photo.compress(Bitmap.CompressFormat.JPEG, 90, boas);
		File f = new File(imageSavedPath);
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
			FileOutputStream fo = new FileOutputStream(f);
			fo.write(boas.toByteArray());
			fo.close();
			
			return true;
		} catch (IOException e) {
			Logger.LogE("Exception when trying to save image file: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
	
	public static Bitmap createThumbnail(String filepath, int width, int height) {
		//Get the origina image width/height
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filepath, options);
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		
		// Calculate ratios of height and width to requested height and width
        final int heightRatio = Math.round((float) imageHeight / (float) height);
        final int widthRatio = Math.round((float) imageWidth / (float) width);

        final BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inJustDecodeBounds = false;
        options2.inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        return BitmapFactory.decodeFile(filepath, options2);
	}
	
	public static String getNewImagePath(String seed){
		return Utils.getExternalDir() + seed + "_" + new Date().getTime() + ".jpg";
	}
	
}
