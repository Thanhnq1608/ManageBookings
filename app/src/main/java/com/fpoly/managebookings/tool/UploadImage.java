package com.fpoly.managebookings.tool;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;

import com.fpoly.managebookings.api.pictureOfRoom.ApiPictureRoom;
import com.fpoly.managebookings.api.user.ApiUser;
import com.fpoly.managebookings.views.listOrderWaiting.ListOrderWaitingActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

public class UploadImage {
    private static final int REQUEST_CODE_PICK_IMAGE = 101;
    private ImageResizer imageResizer;

    @SuppressLint("StaticFieldLeak")
    private static UploadImage mInstance;

    @SuppressLint("StaticFieldLeak")
    private Activity context;

    private ApiUser apiUser = new ApiUser();
    private ApiPictureRoom apiPictureRoom = new ApiPictureRoom();

    private UploadImage(Activity context) {
        this.context = context;
        imageResizer = new ImageResizer();
    }

    public static synchronized UploadImage getInstance(Activity context) {
        if (mInstance == null) {
            mInstance = new UploadImage(context);
        }
        return mInstance;
    }

    public void changeAvatar(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        context.startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(context, contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public void uploadRoomPictures(String[] images, int price){
        MultipartBody.Part[] arrayImages = new MultipartBody.Part[images.length];

        for (int i = 0; i < images.length; i++){
            Bitmap bitmapFullSize = BitmapFactory.decodeFile(images[i]);

            imageResizer.reduceBitmapSize(bitmapFullSize, 240000);

            File reduceFile = getBitmapToFile(bitmapFullSize);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), reduceFile);

            arrayImages[i] = MultipartBody.Part.createFormData("picture",
                    reduceFile.getName(),
                    requestFile);
        }

        apiPictureRoom.uploadRoomPicture(price, arrayImages);
    }

    public void uploadImage(Uri uri) {
//        File file = new File(getRealPathFromURI(uri));
        Bitmap bitmapFullSize = BitmapFactory.decodeFile(getRealPathFromURI(uri));

        imageResizer.reduceBitmapSize(bitmapFullSize, 240000);

        File reduceFile = getBitmapToFile(bitmapFullSize);

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), reduceFile);

        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("avatar",reduceFile.getName(), requestFile);

        apiUser.uploadAvatar(context, multipartBody);
    }

    private File getBitmapToFile(Bitmap bitmap)  {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "reduced_file");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, bos);

        byte[] bitmapData = bos.toByteArray();

        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bitmapData);

            fos.flush();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


}
