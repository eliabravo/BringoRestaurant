package com.example.bringorest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDialog extends AppCompatDialogFragment {
    private TextView tvDishInfo;
    private TextView tvCustomerName;
    private TextView tvCustomerPhone;
    private TextView tvCustomerAddress;
    private TextView tvCustomerZipCode;
    private TextView tvOrderTime;
    private TextView tvOrderNotes;
    private RadioGroup rgStatus;
    private RadioButton rbStatus;
    private OrderDialogListener listener;
    private int mPos;
    private String mOrderStatus;
    private String mCustomerInfo;
    private String mOrderInfo;
    private String mOrderTime;
    private String mCustomerPhone;
    private String mCustomerAddress;
    private String mCustomerName;
    private String mCustomerZip;
    private String mOrderNotes;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.order_det, null);
        tvOrderTime = view.findViewById(R.id.order_time);
        tvDishInfo = view.findViewById(R.id.order_dish_info);
        tvCustomerName = view.findViewById(R.id.order_customer_name);
        tvCustomerPhone = view.findViewById(R.id.order_customer_phone);
        tvCustomerAddress = view.findViewById(R.id.order_customer_address);
        tvCustomerZipCode = view.findViewById(R.id.order_customer_ZipCode);
        tvOrderNotes = view.findViewById(R.id.order_notes);
        rgStatus = (RadioGroup) view.findViewById(R.id.rgStatus);

        mPos = getArguments().getInt("pos");
        mOrderStatus = getArguments().getString("status");
        mCustomerInfo = getArguments().getString("customer_info");
        mOrderInfo = getArguments().getString("order_info");
        mOrderTime = getArguments().getString("time");
        mCustomerPhone = getArguments().getString("phone");
        mCustomerAddress = getArguments().getString("address");
        mCustomerName = getArguments().getString("name");
        mCustomerZip = getArguments().getString("zip");
        mOrderNotes = getArguments().getString("notes");

        tvOrderTime.setText(mOrderTime);
        tvDishInfo.setText(mOrderInfo);
        tvCustomerAddress.setText(mCustomerAddress);
        tvCustomerName.setText(mCustomerName);
        tvCustomerPhone.setText(mCustomerPhone);
        tvCustomerZipCode.setText(mCustomerZip);
        tvOrderNotes.setText(mOrderNotes);


        switch (mOrderStatus){
            case ("PENDING"):
                rgStatus.check(R.id.pending);
                break;
            case ("IN_PREPARATION"):
                rgStatus.check(R.id.in_preparation);
                break;
            case ("PREPARED"):
                rgStatus.check(R.id.prepared);
                break;
            case ("REJECTED"):
                rgStatus.check(R.id.rejected);
                break;
        }

        /*
        rgStatus.setOnCheckedChangeListener(
            new RadioGroup.OnCheckedChangeListener(){
                public void onCheckedChanged (RadioGroup rgStatus, int checkedId) {
                    if (checkedId != -1) {
                        rbStatus = (RadioButton) view.findViewById(checkedId);
                    }
                }
            });
        */

        builder.setView(view)
                .setTitle("Order Details")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int radioId = rgStatus.getCheckedRadioButtonId();
                        rbStatus = (RadioButton) view.findViewById(radioId);
                        String prova =(String) rbStatus.getText();
                        listener.applyStatus(mPos, (String) rbStatus.getText());
                    }
                });

        return builder.create();
    }

    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (OrderDialogListener) getTargetFragment();
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement OrderDialogListener");
        }

    }

    public interface OrderDialogListener{
        void applyStatus (int position, String checked);
    }

    /*
    public void checkButton(View v) {
        int radioId = rgStatus.getCheckedRadioButtonId();
        rbStatus = v.findViewById(radioId);

       //Toast.makeText(this, "Selected Radio Button: " + rbStatus.getText(), Toast.LENGTH_SHORT).show();
    }
    */
}
