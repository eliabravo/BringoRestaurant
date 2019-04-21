package com.example.bringorest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

import static android.content.Context.MODE_PRIVATE;


public class HomeFragment extends Fragment {

    ImageView ivUserPhoto;
    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;
    SharedPreferences preferences;
    EditText etRestName;
    EditText etRestAddress;
    EditText etRestZip;
    EditText etRestCountry;
    //ImageView ivRestPic=(ImageView) view.findViewById(R.id.rest_pic);
    //ImageView ivRestSavedPic=(ImageView) view.findViewById(R.id.);
    EditText etRestEmail;
    EditText etRestPhone;
    EditText etRestInsta;
    EditText etRestTime;
    String img_str;

    @Override
    public void onPause() {
        super.onPause();
        //code image to string
        ivUserPhoto.buildDrawingCache();
        Bitmap bitmap = ivUserPhoto.getDrawingCache();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        //bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image=stream.toByteArray();
        //System.out.println("byte array:"+image);
        //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
        //System.out.println("string:"+img_str);
        img_str = Base64.encodeToString(image, 0);

        preferences = this.getActivity().getSharedPreferences("myprefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("rest_name", etRestName.getText().toString());
        editor.putString("rest_address", etRestAddress.getText().toString());
        editor.putString("rest_zip", etRestZip.getText().toString());
        editor.putString("rest_country", etRestCountry.getText().toString());
        editor.putString("rest_email", etRestEmail.getText().toString());
        editor.putString("rest_phone", etRestPhone.getText().toString());
        editor.putString("rest_insta", etRestInsta.getText().toString());
        editor.putString("rest_time", etRestTime.getText().toString());
        editor.putString("userphoto", img_str);
        editor.commit();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile, container, false);

            //prepareForm();
            preferences = this.getActivity().getSharedPreferences("myprefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            etRestName=(EditText)view.findViewById(R.id.rest_name);
            etRestAddress=(EditText)view.findViewById(R.id.rest_address);
            etRestZip=(EditText)view.findViewById(R.id.rest_zipCode);
            etRestCountry=(EditText)view.findViewById(R.id.rest_country);

            etRestEmail=(EditText)view.findViewById(R.id.rest_email);
            etRestPhone=(EditText)view.findViewById(R.id.rest_phone);
            etRestInsta=(EditText)view.findViewById(R.id.rest_insta);
            etRestTime=(EditText)view.findViewById(R.id.rest_time);
            ivUserPhoto = (ImageView) view.findViewById(R.id.rest_pic);
            ivUserPhoto.isClickable();

            // If value for key not exist then return second param value - In this case "..."
            etRestName.setText(preferences.getString("rest_name", ""));
            etRestAddress.setText(preferences.getString("rest_address",""));
            etRestZip.setText(preferences.getString("rest_zip", ""));
            etRestCountry.setText(preferences.getString("rest_country", ""));
            etRestEmail.setText(preferences.getString("rest_email", ""));
            etRestPhone.setText(preferences.getString("rest_phone", ""));
            etRestInsta.setText(preferences.getString("rest_insta", ""));
            etRestTime.setText(preferences.getString("rest_time", ""));
            img_str=preferences.getString("userphoto", "default");


            if (!img_str.equals("default")) {
                //decode string to image
                String base = img_str;
                byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
                ivUserPhoto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
                //ivUserSavedPhoto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
            }


                ivUserPhoto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(getBaseContext(), "Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, IMAGE_CAPTURE);
                    }
                });

                ivUserPhoto.setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View v) {
                        // TODO Auto-generated method stub
                        //Toast.makeText(getBaseContext(), "Long Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Select Photo"), IMAGE_PICK);
                        return true;
                    }
                });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case IMAGE_PICK: this.imageFromGallery(resultCode, data);
                    break;
                case IMAGE_CAPTURE: this.imageFromCamera(resultCode, data);
                    break;
                default:
                    break; }

        }
    }

    private void imageFromCamera(int resultCode, Intent data) {
        this.ivUserPhoto.setImageBitmap((Bitmap) data.getExtras().get("data"));
    }

    private void imageFromGallery(int resultCode, Intent data) {
        Uri selectedImage = data.getData();
        String [] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        this.ivUserPhoto.setImageBitmap(BitmapFactory.decodeFile(filePath));
    }

    public void setProfilePhoto(View view){
        ImageView ivphoto = (ImageView) view.findViewById(R.id.rest_pic);

        //code image to string
        ivphoto.buildDrawingCache();
        Bitmap bitmap = ivphoto.getDrawingCache();
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
        byte[] image =stream.toByteArray();
        //System.out.println("byte array:"+image);
        //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
        //System.out.println("string:"+img_str);
        String img_str = Base64.encodeToString(image, 0);

        //decode string to image
        //String base=img_str;
        //byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
        //ImageView ivphoto = (ImageView) view.findViewById(R.id.rest_pic);
        //ivsavedphoto.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length) );

        //save in sharedpreferences
        preferences = this.getActivity().getSharedPreferences("myprefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("userphoto",img_str);
        editor.commit();
    }
}
