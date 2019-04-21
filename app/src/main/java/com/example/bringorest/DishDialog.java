package com.example.bringorest;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class DishDialog extends AppCompatDialogFragment {
    private static final int IMAGE_PICK = 1;
    private static final int IMAGE_CAPTURE = 2;

    private EditText etDishName;
    private EditText etDishDesc;
    private EditText etDishQty;
    private EditText etDishPrice;
    private ImageView ivDishPic;
    private DishDialogListener listener;
    private int mPos;
    private int mImgRes;
    private String mName;
    private String mDesc;
    private String mPrice;
    private String mQty;
    private String mImageString;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate ( R.layout.dish, null);
        mPos = getArguments().getInt("pos");
        mName = getArguments().getString("name");
        mImgRes = getArguments().getInt("img_res");
        mDesc = getArguments().getString("desc");
        mPrice = getArguments().getString("price");
        mQty = getArguments().getString("qty");
        mImageString= getArguments().getString("img_str");

        etDishName = view.findViewById(R.id.etDishName);
        etDishDesc = view.findViewById(R.id.etDishDesc);
        etDishPrice = view.findViewById(R.id.etDishPrice);
        etDishQty = view.findViewById(R.id.etDishQty);
        ivDishPic = view.findViewById(R.id.dishPic);

        etDishName.setText(mName);
        etDishDesc.setText(mDesc);
        etDishPrice.setText(mPrice);
        etDishQty.setText(mQty);

        if (!mImageString.equals("")) {
            //decode string to image
            String base = mImageString;
            byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
            ivDishPic.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        }


        ivDishPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //Toast.makeText(getBaseContext(), "Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, IMAGE_CAPTURE);
            }
        });


        builder.setView(view)
                .setTitle("Dish Details")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String name = etDishName.getText().toString();
                        String desc = etDishDesc.getText().toString();
                        String qty = etDishQty.getText().toString();
                        String price = etDishPrice.getText().toString();

                        //code image to string
                        ivDishPic.buildDrawingCache();
                        Bitmap bitmap = ivDishPic.getDrawingCache();
                        ByteArrayOutputStream stream=new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                        byte[] image = stream.toByteArray();
                        //System.out.println("byte array:"+image);
                        //final String img_str = "data:image/png;base64,"+ Base64.encodeToString(image, 0);
                        //System.out.println("string:"+img_str);
                        String img_str = Base64.encodeToString(image, 0);

                        listener.applyTexts(mPos, name, desc, qty, img_str, price);
                    }
                });

        return builder.create();
    }

    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (DishDialogListener) getTargetFragment();
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement DishDialogListener");
        }
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
        this.ivDishPic.setImageBitmap((Bitmap) data.getExtras().get("data"));
    }

    private void imageFromGallery(int resultCode, Intent data) {
        Uri selectedImage = data.getData();
        String [] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String filePath = cursor.getString(columnIndex);
        cursor.close();
        this.ivDishPic.setImageBitmap(BitmapFactory.decodeFile(filePath));
    }

    public interface DishDialogListener{
        void applyTexts (int pos, String name, String desc, String qty, String img_str, String price);
    }
}
