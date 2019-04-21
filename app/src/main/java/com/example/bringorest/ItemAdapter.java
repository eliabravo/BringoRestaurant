package com.example.bringorest;

import android.content.ClipData;
import android.graphics.BitmapFactory;
import android.speech.RecognizerIntent;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>  {
    private ArrayList<ItemMenu> myMenuList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTvName;
        public TextView mTvDesc;
        public TextView mTvQty;
        public TextView mTvPrice;
        public ImageView mDeleteItem;


        public ItemViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTvName = itemView.findViewById(R.id.tvName);
            mTvDesc = itemView.findViewById(R.id.tvDesc);
            mTvQty = itemView.findViewById(R.id.tvQtyValue);
            mTvPrice = itemView.findViewById(R.id.tvPriceValue);
            mDeleteItem = itemView.findViewById(R.id.iv_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });


            mDeleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v){
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public ItemAdapter(ArrayList<ItemMenu> menuList) {
        myMenuList = menuList;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        ItemViewHolder evh = new ItemViewHolder(v, mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ItemMenu currentItem = myMenuList.get(position);
        //holder.mImageView.setImageResource(currentItem.getImageRes());
        String base = currentItem.getImageString();
        byte[] imageAsBytes = Base64.decode(base.getBytes(), Base64.DEFAULT);
        holder.mImageView.setImageBitmap(BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length));
        holder.mTvName.setText(currentItem.getName());
        holder.mTvDesc.setText(currentItem.getDesc());
        holder.mTvQty.setText(currentItem.getQty());
        holder.mTvPrice.setText(currentItem.getPrice() + "EUR");
    }

    @Override
    public int getItemCount() {
        return (myMenuList == null) ? 0 : myMenuList.size();
        //return myMenuList.size();
    }

    /*
    public void applyTexts (String name, String desc, String qty, String img_str) {
        TextView tvName = findViewById(R.id.tvName);
        TextView tvDesc = findViewById(R.id.tvDesc);
        TextView tvQty = findViewById(R.id.tvQtyValue);

        tvName.setText(name);
        tvDesc.setText(desc);
        tvQty.setText(qty);
    }
    */
}