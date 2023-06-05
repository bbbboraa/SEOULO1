package com.example.seoulo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class myCheckListAdapter extends ArrayAdapter implements View.OnClickListener{
    private ListBtnClickListener listBtnClickListener = null ;

    private CheckBox checkBox ;
    private EditText item_edit ;
    private ImageButton btn_delete;
    public interface ListBtnClickListener {

        void onListBtnClick(int position, int resourceid) ;

    }
    public myCheckListAdapter(@NonNull Context context, int resource, @NonNull List objects, ListBtnClickListener listBtnClickListener) {
        super(context, resource, objects);
        this.listBtnClickListener = listBtnClickListener;
    }
    @NonNull

    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int pos = position;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.checklist_items, parent, false);
        }

        checkBox = convertView.findViewById(R.id.checkbox);
        item_edit = convertView.findViewById(R.id.item_edit);
        btn_delete = convertView.findViewById(R.id.btn_delete);
        PreparationItem checklist_items = (PreparationItem) getItem(position);
        checkBox.setTag(pos);
        checkBox.setOnClickListener(this);
        btn_delete.setTag(pos);
        btn_delete.setOnClickListener(this);
        btn_delete.setVisibility(View.VISIBLE);
        item_edit.setText(checklist_items.getItemString());
        return convertView;
    }
    @Override

    public void onClick(View view) {
        if (this.listBtnClickListener != null) {
            this.listBtnClickListener.onListBtnClick((int)view.getTag(), view.getId()) ;
        }
    }
}
