package com.example.bringorest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OrdersFragment extends Fragment implements OrderDialog.OrderDialogListener {
    private RecyclerView myRecyclerView;
    private OrderAdapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    ArrayList<ItemOrder> exampleList;
    SharedPreferences preferences;

    public enum OrderStatus {PENDING, IN_PREPARATION, PREPARED, REJECTED}



    @Override
    public void onPause() {
        super.onPause();

        preferences = this.getActivity().getSharedPreferences("myprefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String json = gson.toJson(exampleList);

        editor.remove("key2").commit();
        editor.putString("key2", json);
        editor.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders, container, false);
        OrderStatus status = OrderStatus.PENDING;

        preferences = this.getActivity().getSharedPreferences("myprefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        //create example list
        Gson gson = new Gson();
        String response = preferences.getString("key2", "");
        if (response.isEmpty()) {
            exampleList = new ArrayList<>();
            exampleList.add(new ItemOrder(status, "Mario Rossi", "1 pizza, 2 pasta", "12:00", "Mario Rossi", "via del Campo 15", "12345", "03020302030", "some notes"));
            exampleList.add(new ItemOrder(status, "John Lennon", "2 pasta, 3 pizza", "12:50", "John Lennon", "via Torino 57", "12345", "030203730", "some notes"));
            exampleList.add(new ItemOrder(status, "Luca Bianchi", "3 pizza, 1 riso", "14:00", "Luca Bianchi", "via Roma 24", "12365", "03020302030", "some notes"));
            exampleList.add(new ItemOrder(status, "Sofia Ricci", "4 pizza, 3 parmigiana", "20:00", "Sofia Ricci", "corso Caduti 24", "12365", "03020305530", "ketchup e maio"));
        } else{
            Type type = new TypeToken<List<ItemOrder>>() {}.getType();
            exampleList = gson.fromJson(response, type);
        }

        //create example list
        /*
        exampleList = new ArrayList<>();
        exampleList.add(new ItemOrder(status, "Mario Rossi", "1 pizza, 2 pasta", "12:00", "Mario Rossi", "via del Campo 15", "12345", "03020302030", "some notes"));
        exampleList.add(new ItemOrder(status, "John Lennon", "2 pasta, 3 pizza", "12:50", "John Lennon", "via Torino 57", "12345", "030203730", "some notes"));
        exampleList.add(new ItemOrder(status, "Luca Bianchi", "3 pizza, 1 riso", "14:00", "Luca Bianchi", "via Roma 24", "12365", "03020302030", "some notes"));
        */

        //build recycler view
        myRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewOrders);
        myRecyclerView.setHasFixedSize(true);
        //FragmentActivity c = getActivity();
        myLayoutManager = new LinearLayoutManager(this.getActivity());
        myAdapter = new OrderAdapter(exampleList);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);


        myAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //changeItem(position, "Clicked");
                //String status = exampleList.get(position).getmOrderStatus().toString();
                openDialog(position, exampleList.get(position));
            }
            });

        return view;
    }
/*
    public void insertItem(int position) {
        exampleList.add(position, new ItemMenu(R.drawable.ic_home_black_24dp, "New Dish at position" + position, "Dish Desc", "5","", "15"));
        myAdapter.notifyItemInserted(position);
    }

    public void removeItem(int position) {
        exampleList.remove(position);
        myAdapter.notifyItemRemoved(position);
    }
*/
/*
    public void changeItem(int position, String line1, String line2, String qty, String img_str, String price) {
        ItemOrder item = exampleList.get(position);
        item.changeName(name);
        item.changeDesc(desc);
        item.changeQty(qty);
        item.changeImageString(img_str);
        item.changePrice(price);
        myAdapter.notifyItemChanged(position);
    }
*/
public void changeStatus(int position, String checked) {
    ItemOrder item = exampleList.get(position);
    switch (checked){
        case ("pending"):
            item.setmOrderStatus(OrderStatus.PENDING);
            //exampleList.get(position).setmOrderStatus(OrderStatus.PENDING);
            break;
        case ("in preparation"):
            item.setmOrderStatus(OrderStatus.IN_PREPARATION);
            //exampleList.get(position).setmOrderStatus(OrderStatus.IN_PREPARATION);
            break;
        case ("prepared"):
            item.setmOrderStatus(OrderStatus.PREPARED);
            //exampleList.get(position).setmOrderStatus(OrderStatus.PREPARED);
            break;
        case ("rejected"):
            item.setmOrderStatus(OrderStatus.REJECTED);
            //exampleList.get(position).setmOrderStatus(OrderStatus.REJECTED);
            break;
    }
}

    public void openDialog(int pos, ItemOrder itemOrder) {
        OrderDialog mDialog = new OrderDialog();
        Bundle args = new Bundle();

        args.putInt("pos", pos);
        args.putString("address", itemOrder.getmCustomerAddress());
        args.putString("status", itemOrder.getmOrderStatus().toString());
        args.putString("customer_info", itemOrder.getmCustomerInfo());
        args.putString("name", itemOrder.getmCustomerName());
        args.putString("phone", itemOrder.getmCustomerPhone());
        args.putString("zip", itemOrder.getmCustomerZip());
        args.putString("order_info", itemOrder.getmOrderInfo());
        args.putString("notes", itemOrder.getmOrderNotes());
        args.putString("time", itemOrder.getmOrderTime());
        mDialog.setArguments(args);
        mDialog.setTargetFragment(OrdersFragment.this, 1);
        mDialog.show(getActivity().getSupportFragmentManager(), "order dialog");
    }


    public void applyStatus (int position, String checked) {
        changeStatus(position, checked);

        myRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerViewOrders);
        myRecyclerView.setHasFixedSize(true);
        //FragmentActivity c = getActivity();
        myLayoutManager = new LinearLayoutManager(this.getActivity());
        myAdapter = new OrderAdapter(exampleList);

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);

        myAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //changeItem(position, "Clicked");
                //String status = exampleList.get(position).getmOrderStatus().toString();
                openDialog(position, exampleList.get(position));
            }
        });
    }
}