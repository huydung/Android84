package com.hdway.utils;
 
import java.io.File;
import java.lang.ref.WeakReference;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
 
public class SDImageLoader {
	
	
    public void load(String filePath, ImageView v) {
    	if( filePath == null ) return;
    	if( filePath.isEmpty() ) return;
    	
        if(cancelPotentialSDLoad(filePath, v)) {        	
            SDLoadImageTask task = new SDLoadImageTask(v, v.getWidth(), v.getHeight());
            SDLoadDrawable sdDrawable = new SDLoadDrawable(task);
            v.setImageDrawable(sdDrawable);
            
            task.execute(filePath);
        }
    }
 
    private Bitmap loadImageFromSDCardOrServer(final String filePath, int width, int height) {
    	BitmapFactory.Options bfo = new BitmapFactory.Options();
        //HDVD: Do not downsample the image
        //bfo.inSampleSize = 4;
        bfo.outWidth = width;
        bfo.outHeight = height;        
    	File f = new File(filePath);
    	Bitmap photo = BitmapFactory.decodeFile(filePath, bfo);
        return photo; 
    } 
 
    private static boolean cancelPotentialSDLoad(String filePath, ImageView v) {
        SDLoadImageTask sdLoadTask = getAsyncSDLoadImageTask(v);
 
        if(sdLoadTask != null) {
            String path = sdLoadTask.getFilePath();
            if((path == null) || (!path.equals(filePath))) {
                sdLoadTask.cancel(true);
            } else {
                return false;
            }
        }
        return true;
    }
 
    private static SDLoadImageTask getAsyncSDLoadImageTask(ImageView v) {
        if(v != null) {
            Drawable drawable = v.getDrawable();
            if(drawable instanceof SDLoadDrawable) {
                SDLoadDrawable asyncLoadedDrawable = (SDLoadDrawable)drawable;
                return asyncLoadedDrawable.getAsyncSDLoadTask();
            }
        }
        return null;
    }
 
    private class SDLoadImageTask extends AsyncTask<String, Void, Bitmap> {
 
        private String mFilePath;
        private final WeakReference<ImageView> mImageViewReference;
 
        public String getFilePath() {
            return mFilePath;
        }
        
        int width;
    	int height;
 
        public SDLoadImageTask(ImageView v, int width, int height) {
            mImageViewReference = new WeakReference<ImageView>(v);
            this.width = width;
            this.height = height;
        }
 
        @Override
        protected void onPostExecute(Bitmap bmp) {
            if(mImageViewReference != null) {
                ImageView v = mImageViewReference.get();
                SDLoadImageTask sdLoadTask = getAsyncSDLoadImageTask(v);
                // Change bitmap only if this process is still associated with it
                if(this == sdLoadTask) {
                    v.setImageBitmap(bmp);
                }
            }
        }
 
        @Override
        protected Bitmap doInBackground(String... params) {
            mFilePath = params[0];
            return loadImageFromSDCardOrServer(mFilePath, width, height);
        }
    }
 
    private class SDLoadDrawable extends ColorDrawable {
        private final WeakReference<SDLoadImageTask> asyncSDLoadTaskReference;
 
        public SDLoadDrawable(SDLoadImageTask asyncSDLoadTask) {
            super(Color.BLACK);
            asyncSDLoadTaskReference = new WeakReference<SDLoadImageTask>(asyncSDLoadTask);
        }
 
        public SDLoadImageTask getAsyncSDLoadTask() {
            return asyncSDLoadTaskReference.get();
        }
 
    }
}