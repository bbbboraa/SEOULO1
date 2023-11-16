package com.example.seoulo1;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
public class myCheckListAdapter extends ArrayAdapter<PreparationItem> implements View.OnClickListener{
    private ListBtnClickListener listBtnClickListener = null ;


    private List<PreparationItem> preparationItems;

    public interface ListBtnClickListener {
        void onListBtnClick(int position, int resourceid) ;
    }
    static class UserViewHolder {
        public CheckBox checkBox;
        public EditText item_edit;
        public ImageView btn_delete;
    }
    public myCheckListAdapter(@NonNull Context context, int resource, @NonNull List<PreparationItem> objects, ListBtnClickListener listBtnClickListener) {
        super(context, resource, objects);
        this.listBtnClickListener = listBtnClickListener;
        this.preparationItems = objects;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int pos = position;
        if(convertView==null){
            LayoutInflater inflater=(LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.checklist_items, parent, false);
            UserViewHolder viewHolder = new UserViewHolder();
            viewHolder.checkBox = convertView.findViewById(R.id.checkbox);
            viewHolder.item_edit = convertView.findViewById(R.id.item_edit);
            viewHolder.btn_delete = convertView.findViewById(R.id.btn_delete);
            viewHolder.checkBox.setOnClickListener(this);
            viewHolder.btn_delete.setOnClickListener(this);
            viewHolder.item_edit.setOnEditorActionListener((v, actionId, event) -> {
                int clickedPosition = (int) v.getTag();
                ((CheckListActivity) getContext()).handleEnterKey(clickedPosition);
                return true;
            });
            convertView.setTag(viewHolder);
        }
        UserViewHolder viewHolder = (UserViewHolder) convertView.getTag();
        PreparationItem checklist_items = (PreparationItem) getItem(position);
        viewHolder.checkBox.setTag(pos);
        //viewHolder.checkBox.setOnClickListener(this);
        if (checklist_items.getChecked()) {
            Log.d(TAG, checklist_items.getChecked() + "어댑터의 좋아요 이미지 설정 getstatus () ");
            viewHolder.checkBox.setChecked(true);
        } else {
            viewHolder.checkBox.setChecked(false);
        }
        viewHolder.btn_delete.setTag(pos);

        viewHolder.item_edit.setTag(pos);
        //값을 불려오고 해당 리스너를 적용한다
        viewHolder.item_edit.setText(checklist_items.itemString);
        clearTextChangedListener(viewHolder.item_edit);
        viewHolder.item_edit.addTextChangedListener(checklist_items.mTextWatcher);
        viewHolder.item_edit.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // 포커스를 잃었을 때
                checklist_items.newitemString = viewHolder.item_edit.getText().toString();
            }
        });
        //viewHolder.item_edit.setText(checklist_items.getItemString());


        Log.d("@@@", "preparationitem[" + position + "]" + " row view is " + checklist_items.getItemString());

        return convertView;
//        checkBox = convertView.findViewById(R.id.checkbox);
//        item_edit = convertView.findViewById(R.id.item_edit);
//        btn_delete = convertView.findViewById(R.id.btn_delete);
//        checkBox.setTag(pos);
//        checkBox.setOnClickListener(this);
//        //checkBox.setOnClickListener(v -> checklist_items.getChecked());
//        btn_delete.setTag(pos);
//        btn_delete.setOnClickListener(this);
//        btn_delete.setVisibility(View.VISIBLE);
//        item_edit.setText(checklist_items.getItemString());
//        this.notifyDataSetChanged();
//
//        Log.d("@@@", "preparationitem[" + position + "]" + " row view is " + checklist_items.getItemString());
//
//        return convertView;
    }
    private void clearTextChangedListener(EditText editText) {
        //리스트 목록의 모든 리스너를 대상으로 검사하여 삭제해 준다
        int count = getCount();
        for (int i = 0 ; i < count ; i++)
            editText.removeTextChangedListener(getItem(i).mTextWatcher);
    }
    public void updateItemText(int position, String newText) {
        PreparationItem item = preparationItems.get(position);
        item.setItemString(newText);
        notifyDataSetChanged();
    }
    @Override
    public void onClick(View view) {
        if (this.listBtnClickListener != null) {
            int position = (int) view.getTag();
            int resourceId = view.getId();
            this.listBtnClickListener.onListBtnClick(position, resourceId) ;
            if (resourceId == R.id.checkbox) {
                PreparationItem item = preparationItems.get(position);
                item.setChecked(!item.isChecked());
                notifyDataSetChanged(); // 이 부분이 변경된 상태를 어댑터에 알려 업데이트합니다.
            }

        }

    }
}