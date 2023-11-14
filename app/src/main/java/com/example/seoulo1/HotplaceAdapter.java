package com.example.seoulo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class HotplaceAdapter extends ArrayAdapter<HotplaceItem> {
    private int resource;

    public HotplaceAdapter(Context context, int resource, List<HotplaceItem> items) {
        super(context, resource, items);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }

        HotplaceItem item = getItem(position);

        if (item != null) {
            TextView itemNameTextView = convertView.findViewById(R.id.textview_name);
            itemNameTextView.setText(item.getItemName());
        }

        return convertView;
    }
}
