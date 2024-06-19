package com.phancongduy.de4;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.phancongduy.de4.Adapter.ItemAdapter;
import com.phancongduy.de4.Database.database;
import com.phancongduy.de4.Model.Product;
import com.phancongduy.de4.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ActivityMain2Binding binding;
    database db;
    ItemAdapter adapter;
    ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new database(MainActivity2.this);
        db.createSampleData();
        
        loadData();
        addEvent();
    }

    private void addEvent() {
        binding.lvProduct.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                openEditDiaLog(products.get(position));
                return false;
            }
        });
    }

    private void openEditDiaLog(Product product) {
        Dialog dialog = new Dialog(MainActivity2.this);
        dialog.setContentView(R.layout.dialog_layout);

        EditText eProductCode = dialog.findViewById(R.id.editprodutCode);
        //eProductCode.setText(String.valueOf(product.getProductCode()));

        EditText eProductName = dialog.findViewById(R.id.editprodutName);
        //eProductName.setText(String.valueOf(product.getProductName()));

        EditText eProdutcPrice = dialog.findViewById(R.id.editprodutPrice);
        //eProdutcPrice.setText(String.valueOf(product.getProductPrice()));

        Button btnLuu = dialog.findViewById(R.id.btnLuu);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int code = Integer.parseInt(eProductCode.getText().toString());
                String name = eProductName.getText().toString();
                Double price = Double.valueOf(eProdutcPrice.getText().toString());

               db.execSql(" UPDATE " + database.TBL_NAME + " SET " + db.COL_NAME + " = '" + name + "', " + db.COL_PRICE + " = " + price +
                       " WHERE " + db.COL_CODE + " = " +product.getProductCode());

                db.execSql("INSERT INTO " + database.TBL_NAME + " (" + database.COL_NAME + ", " + database.COL_PRICE + ", " + database.COL_CODE + ") VALUES ('" + name + "', " + price + ", " + code + ")");

                db.execSql("DELETE FROM " + database.TBL_NAME + " WHERE " + database.COL_CODE + " = " + product.getProductCode());

                loadData();
                dialog.dismiss();
            }
        });

        Button btnTroVe = dialog.findViewById(R.id.btnTroVe);
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private List<Product> getDataFromDB() {
        products = new ArrayList<>();
        Cursor cursor = db.getData("SELECT * FROM " + database.TBL_NAME);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            products.add(new Product(cursor.getInt(0),cursor.getString(1),cursor.getDouble(2)));

            cursor.moveToNext();
        }
        cursor.close();
        return  products;
    }

    private void loadData() {
        adapter = new ItemAdapter(MainActivity2.this,R.layout.item_layout,getDataFromDB());
        binding.lvProduct.setAdapter(adapter);
    }
}