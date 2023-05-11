package com.example.aexpress.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aexpress.R;
import com.example.aexpress.adapters.CartAdapter;
import com.example.aexpress.databinding.ActivityCheckoutBinding;
import com.example.aexpress.model.Product;
import com.example.aexpress.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    ActivityCheckoutBinding binding;
    CartAdapter adapter;
    ArrayList<Product> products;
    double totalPrice = 0;
    final int tax = 11;
    ProgressDialog progressDialog;
    Cart cart;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        products = new ArrayList<>();

        cart = TinyCartHelper.getCart();

        for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);

            products.add(product);
        }

        adapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                binding.subtotal.setText(String.format("%.2f BDT", cart.getTotalPrice()));
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        binding.cartList.setLayoutManager(layoutManager);
        binding.cartList.addItemDecoration(itemDecoration);
        binding.cartList.setAdapter(adapter);

        binding.subtotal.setText(String.format("%.2f BDT", cart.getTotalPrice()));

        totalPrice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
        binding.total.setText(totalPrice + " BDT");

        binding.checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processOrder();
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    void processOrder() {
        String error = "";
        String type = "";
        String existingkey = "";
        String address = binding.addressBox.getText().toString();
        String buyer = binding.nameBox.getText().toString();
        String comment = binding.commentBox.getText().toString();
        String last_update = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String date_ship = String.valueOf(Calendar.getInstance().getTimeInMillis());
        String email = binding.emailBox.getText().toString();
        String phone = binding.phoneBox.getText().toString();
        String serial = "cab8c1a4e4421a3b";
        int tax1 = tax;
        Double total_fees = totalPrice;

        for (Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);
            int amount = quantity;
            Double price_item = product.getPrice();
            String product_id = String.valueOf(product.getId());
            String product_name = product.getName();
            String value = address + "---" + buyer + "---" + comment + "---" + last_update + "---" + date_ship + "---" + email + "---" + phone + "---" + serial + "---" + tax1 + "---" + total_fees
                    + "---" + amount + "---" + price_item + "---" + product_id + "---" + product_name;
            KeyValueDB db = new KeyValueDB(CheckoutActivity.this);
            /* write code to generate a unique id */
            if (existingkey.length() == 0) {
                String key = buyer + System.currentTimeMillis();
                existingkey = key;
                System.out.println(key);
                db.insertKeyValue(key, value);
                Toast.makeText(CheckoutActivity.this , "Successful Stored", Toast.LENGTH_SHORT).show();


            } else {
                db.updateValueByKey(existingkey, value);

            }
            db.close();

        }

    }
}