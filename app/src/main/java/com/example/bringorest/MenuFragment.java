package com.example.bringorest;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class MenuFragment extends Fragment implements DishDialog.DishDialogListener {
    private RecyclerView myRecyclerView;
    private ItemAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    ArrayList<ItemMenu> exampleList;
    Button bInsert;
    SharedPreferences preferences;
    //EditText editTextInsert;
    //Button bRemove;
    //EditText editTextRemove;


    @Override
    public void onPause() {
        super.onPause();

        preferences = this.getActivity().getSharedPreferences("myprefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(exampleList);

        editor.remove("key").commit();
        editor.putString("key", json);
        editor.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dailymenu, container, false);
        //exampleList = new ArrayList<>();

        preferences = this.getActivity().getSharedPreferences("myprefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //create example list
        Gson gson = new Gson();
        String response = preferences.getString("key", "");
        if (response.isEmpty()) {
           exampleList = new ArrayList<>();
        } else{
            Type type = new TypeToken<List<ItemMenu>>() {}.getType();
            exampleList = gson.fromJson(response, type);
        }
        //
        //exampleList = gson.fromJson(response, new TypeToken<List<ItemMenu>>(){}.getType());


        /*
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 1", "Desc dish 1", "5", "","15"));
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 2", "Desc dish 2", "5", "","15"));
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 3", "Desc dish 3", "5", "","15"));
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 4", "Desc dish 4", "5", "","15"));
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 5", "Desc dish 5", "5", "","15"));
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 6", "Desc dish 6", "5", "","15"));
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 7", "Desc dish 7", "5", "","15"));
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 8", "Desc dish 8", "5", "","15"));
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 9", "Desc dish 9", "5", "","15"));
        exampleList.add(new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "Dish 10","Desc dish 10","5", "","15"));
        */

        //build recycler view
        myRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewMenu);
        myRecyclerView.setHasFixedSize(true);
        //FragmentActivity c = getActivity();
        myLayoutManager = new LinearLayoutManager(this.getActivity());
        myAdapter = new ItemAdapter(exampleList);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //changeItem(position, "Clicked");
                openDialog(position, exampleList.get(position));
            }

            @Override
            public void onDeleteClick(int position) {
                removeItem(position);
            }
        });

        //set buttons
        bInsert = (Button) view.findViewById(R.id.button_insert);
        //bRemove = view.findViewById(R.id.button_remove);
        //editTextInsert = view.findViewById(R.id.edittext_insert);
        //editTextRemove = view.findViewById(R.id.edittext_remove);

        bInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //int position = (exampleList == null) ? 0 : exampleList.size();
                int position = exampleList.size();
                insertItem(position);
            }
        });

        /*
        bRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = Integer.parseInt(editTextRemove.getText().toString());
                removeItem(position);
            }
        });
        */

        return view;
    }

    public void insertItem(int position) {
        exampleList.add(position, new ItemMenu(R.drawable.ic_restaurant_menu_black_24dp, "New Dish" + position, "Dish Desc", "5","", "15"));
        myAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        exampleList.remove(position);
        myAdapter.notifyItemRemoved(position);
    }

    public void changeItem(int position, String name, String desc, String qty, String img_str, String price) {
        ItemMenu item = exampleList.get(position);
        item.changeName(name);
        item.changeDesc(desc);
        item.changeQty(qty);
        item.changeImageString(img_str);

        item.changePrice(price);
        myAdapter.notifyItemChanged(position);
    }

    public void openDialog(int pos, ItemMenu itemMenu) {
        DishDialog mDialog = new DishDialog();
        Bundle args = new Bundle();
        args.putInt("pos", pos);
        args.putInt("img_res", itemMenu.getImageRes());
        args.putString("name", itemMenu.getName());
        args.putString("desc", itemMenu.getDesc());
        args.putString("price", itemMenu.getPrice());
        args.putString("qty", itemMenu.getQty());
        args.putString("img_str", itemMenu.getImageString());
        mDialog.setArguments(args);
        mDialog.setTargetFragment(MenuFragment.this, 1);
        mDialog.show(getActivity().getSupportFragmentManager(), "example dialog");

    }

    public void applyTexts (int pos, String name, String desc, String qty, String img_str, String price) {
        changeItem(pos, name, desc, qty, img_str, price);
    }
}


