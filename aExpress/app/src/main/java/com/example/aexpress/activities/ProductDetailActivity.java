package com.example.aexpress.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.aexpress.R;
import com.example.aexpress.databinding.ActivityProductDetailBinding;
import com.example.aexpress.model.Product;
import com.example.aexpress.utils.Constants;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {


    ActivityProductDetailBinding binding;
    JSONArray jsonObject;
    Product currentProduct;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String name = getIntent().getStringExtra("name");
        String image = getIntent().getStringExtra("image");
        int id = getIntent().getIntExtra("id",0);
        double price = getIntent().getDoubleExtra("price",0);

        binding.productDescription.setText(
                Html.fromHtml("<b>Product Name:</b> <t/>" + name + "<br/> <br/> <b>Product Price:</b> <t/> " + price + "BDT")
        );

       // binding.productDescription.setText( "Product Name: " + name +"\nProduct Price: "+ price );

        Glide.with(this)
                .load(image)
                .into(binding.productImage);

        getProductDetails(id);

        getSupportActionBar().setTitle(name);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Cart cart = TinyCartHelper.getCart();


        binding.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addItem(currentProduct,1);
                binding.addToCartBtn.setEnabled(false);
                binding.addToCartBtn.setText("Added in cart");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart) {
            startActivity(new Intent(this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    void getProductDetails(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://lgorithmbd.com/php_rest_app/api/products/read.php?id=" + id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray dataArray = object.getJSONArray("data");
                    if(dataArray.length() > 0) {
                        JSONObject productObject = dataArray.getJSONObject(0);
                        String productId = productObject.getString("id");
                        String name = productObject.getString("name");
                        String image = productObject.getString("image");
                        String price = productObject.getString("price");
                        String price_discount = productObject.getString("price_discount");
                        String stock = productObject.getString("stock");
                        String draft = productObject.getString("draft");
                        String status = productObject.getString("status");
                        System.out.println( "sifat "+ productId + name + price + stock);

                        currentProduct = new Product(
                                name,
                                Constants.PRODUCTS_IMAGE_URL + image,
                                status,
                                Double.parseDouble(price),
                                Double.parseDouble(price_discount),
                                Integer.parseInt(stock),
                                Integer.parseInt(productId)
                        );
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    /*
    void getProductDetails(int id) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://lgorithmbd.com/php_rest_app/api/products/read.php?id=" + id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                   // if(object.getString("status").equals("success")) {
                    JSONObject product = object.getJSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray(0);
                    JSONObject productObject = jsonArray.getJSONObject(0);
                    String id = productObject.getString("id");
                    String name = productObject.getString("name");
                    String image = productObject.getString("image");
                    String price = productObject.getString("price");
                    String price_discount = productObject.getString("price_discount");
                    String stock = productObject.getString("stock");
                    String draft = productObject.getString("draft");
                    String status = productObject.getString("status");
                    System.out.println( "sifat "+ id + name + price + stock);
                      //  String description = product.getString("description");

//                    binding.productDescription.setText(
//                            Html.fromHtml(description)
//                    );
                        currentProduct = new Product(
                                product.getString("name"),
                                Constants.PRODUCTS_IMAGE_URL + product.getString("image"),
                                product.getString("status"),
                                product.getDouble("price"),
                                product.getDouble("price_discount"),
                                product.getInt("stock"),
                                product.getInt("id")
                        );
                   // }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);
    }
*/
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}