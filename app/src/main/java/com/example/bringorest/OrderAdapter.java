package com.example.bringorest;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private ArrayList<ItemOrder> myOrderList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
        //void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OrderAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        public ImageView mImageView;
        public TextView mTvCustomerInfo;
        public TextView mTvOrderInfo;
        public TextView mTvTime;
        public TextView mTvStatus;

        public OrderViewHolder(View itemView, final OrderAdapter.OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.ivOrderStatus);
            mTvCustomerInfo = itemView.findViewById(R.id.tvOrderLine1);
            mTvOrderInfo = itemView.findViewById(R.id.tvOrderLine2);
            mTvTime= itemView.findViewById(R.id.tvOrderTimeValue);
            mTvStatus = itemView.findViewById(R.id.tvStatus);


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
        }
    }

    public OrderAdapter (ArrayList<ItemOrder> orderList) {
        myOrderList = orderList;
    }

    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        OrderAdapter.OrderViewHolder evh = new OrderAdapter.OrderViewHolder(v, mListener);
        return evh;
    }


    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        ItemOrder currentItem = myOrderList.get(position);

        switch (currentItem.getmOrderStatus().toString()){
            case ("PENDING"):
                holder.mImageView.setColorFilter(Color.YELLOW);
                //holder.mTvStatus.setText("PENDING");
                break;
            case ("IN_PREPARATION"):
                holder.mImageView.setColorFilter(Color.BLUE);
                //holder.mTvStatus.setText("PENDING");
                break;
            case ("PREPARED"):
                holder.mImageView.setColorFilter(Color.GREEN);
                //holder.mTvStatus.setText("PENDING");
                break;
            case ("REJECTED"):
                holder.mImageView.setColorFilter(Color.RED);
                //holder.mTvStatus.setText("PENDING");
                break;
        }

        holder.mTvCustomerInfo.setText(currentItem.getmCustomerInfo());
        holder.mTvOrderInfo.setText(currentItem.getmOrderInfo());
        holder.mTvTime.setText(currentItem.getmOrderTime());
        holder.mTvStatus.setText(currentItem.getmOrderStatus().toString());
    }

    @Override
    public int getItemCount() {
        return myOrderList.size();
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
