package com.idlab.idcorp.assignment_android.common.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by diygame5 on 2017-03-28.
 */

public class ImageUtil {
    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        Bitmap bm;
        try {
            bm = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);
        } catch (IOException e) {
            return null;
        }
        return bm;
    }

    public static Bitmap getResizedBitmapFromUri(Context context, Uri uri, int maxResolution) {
        Bitmap source = getBitmapFromUri(context, uri);
        if (source == null) {
            return null;
        }
        int width = source.getWidth();
        int height = source.getHeight();
        int newWidth = width;
        int newHeight = height;
        float rate;
        if (width > height) {
            if (maxResolution < width) {
                rate = maxResolution / (float) width;
                newHeight = (int) (height * rate);
                newWidth = maxResolution;
            }
        } else {
            if (maxResolution < height) {
                rate = maxResolution / (float) height;
                newWidth = (int) (width * rate);
                newHeight = maxResolution;
            }
        }
        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }

    public static Bitmap getResizedBitmap(Bitmap source, int maxResolution) {
        int width = source.getWidth();
        int height = source.getHeight();
        int newWidth = width;
        int newHeight = height;
        float rate;
        if (width > height) {
            if (maxResolution < width) {
                rate = maxResolution / (float) width;
                newHeight = (int) (height * rate);
                newWidth = maxResolution;
            }
        } else {
            if (maxResolution < height) {
                rate = maxResolution / (float) height;
                newWidth = (int) (width * rate);
                newHeight = maxResolution;
            }
        }
        return Bitmap.createScaledBitmap(source, newWidth, newHeight, true);
    }

    public static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }

    // public static Bitmap getResizedBitmap(Bitmap bitmap){
    //     final BitmapFactory.Options options = new BitmapFactory.Options();
    //     options.inJustDecodeBounds = true;
    //     BitmapFactory.decodeFile(image.getAbsolutePath(), options);
    //     options.inSampleSize = calculateInSampleSize(options);
    //     options.inJustDecodeBounds = false;
    //     BitmapFactory.
    //     return BitmapFactory.decodeFile(image.getAbsolutePath(), options);
    // }

    public static final int IMAGE_MAXIMUM_WIDTH = 512;
    public static final int IMAGE_MAXIMUM_HEIGHT = 512;

    public static int calculateInSampleSize(BitmapFactory.Options options) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > IMAGE_MAXIMUM_HEIGHT || width > IMAGE_MAXIMUM_WIDTH) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > IMAGE_MAXIMUM_HEIGHT
                    && (halfWidth / inSampleSize) > IMAGE_MAXIMUM_WIDTH) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }



    public static Bitmap convert(String base64Str) throws IllegalArgumentException
    {
        byte[] decodedBytes = Base64.decode(
                base64Str.substring(base64Str.indexOf(",")  + 1),
                Base64.DEFAULT
        );
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

}