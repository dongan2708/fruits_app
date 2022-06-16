package com.android.appfruit.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.appfruit.R;
import com.android.appfruit.adapter.CartAdapter;
import com.android.appfruit.dto.CheckOutDto;
import com.android.appfruit.entity.ShoppingCart;
import com.android.appfruit.service.CartService;
import com.android.appfruit.util.RetrofitGenerator;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

import retrofit2.Response;

public class CheckOutFragment extends Fragment {

    private View view;
    private Context currentContext;
    private CartService cartService;
    private String token = null;
    private TextInputEditText etName, etAddress, etPhone, etNote;

    public CheckOutFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        currentContext = container.getContext();
        view = inflater.inflate(R.layout.fragment_check_out, container, false);
        AppCompatButton submitCheckOut = view.findViewById(R.id.button_check_out);
        etName = view.findViewById(R.id.textInputEditTextName);
        etAddress = view.findViewById(R.id.textInputEditTextAddress);
        etPhone = view.findViewById(R.id.textInputEditTextPhone);
        etNote = view.findViewById(R.id.textInputEditTextNote);

        submitCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String address = etAddress.getText().toString();
                String phone = etPhone.getText().toString();
                String note = etNote.getText().toString();

                CheckOutDto checkOutDto = new CheckOutDto();
                checkOutDto.setShipName(name);
                checkOutDto.setShipAddress(address);
                checkOutDto.setShipPhone(phone);
                checkOutDto.setShipNote(note);

                try {
                    cartService.checkOut(checkOutDto).execute();
                    Toast.makeText(view.getContext(), "Submit Success",Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        config();

        return view;
    }

    private void config() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        SharedPreferences settings = getActivity().getSharedPreferences("token", Context.MODE_PRIVATE);
        token = settings.getString("token", "");
        String refreshToken = settings.getString("refreshToken", "");
        Log.d("token", token);
        Log.d("refreshToken", refreshToken);
        if (cartService == null) {
            cartService = RetrofitGenerator.createService(CartService.class,token);
        }
    }
}