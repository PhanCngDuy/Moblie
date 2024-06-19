package com.phancongduy.de4.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.phancongduy.de4.Model.Product;
import com.phancongduy.de4.R;

import java.util.List;

public class ItemAdapter extends BaseAdapter {
    Context context;
    int layout;
    List<Product> productList;

    public ItemAdapter(Context context, int layout, List<Product> productList) {
        this.context = context;
        this.layout = layout;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);

            holder.txtproductCode = convertView.findViewById(R.id.txtproductCode);
            holder.txtproductName = convertView.findViewById(R.id.txtproductName);
            holder.txtproductPrice = convertView.findViewById(R.id.txtproductPrice);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        Product product = productList.get(position);

        holder.txtproductCode.setText(String.valueOf(product.getProductCode()));
        holder.txtproductName.setText(String.valueOf(product.getProductName()));
        holder.txtproductPrice.setText(String.valueOf(product.getProductPrice()));

        return convertView;
    }
    public static class ViewHolder {
        TextView txtproductCode, txtproductName, txtproductPrice;
    }
}
