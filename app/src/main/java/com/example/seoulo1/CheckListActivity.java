package com.example.seoulo1;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class CheckListActivity extends AppCompatActivity implements myCheckListAdapter.ListBtnClickListener {
    ArrayList checkList=null;
    ArrayList<PreparationItem> items= new ArrayList<>();

    //int count=0;
    ImageButton plus, btn_delete;
    EditText editText, editText_delete;
    SharedPreferences sp;
    String  s;
    boolean b;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    static final String DB_NAME = "Check.db";
    static final String TABLE_NAME = "Table_content";
    private final String INFO="info";
    private final String STATE="state";
    myCheckListAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        plus = findViewById(R.id.btn_checkplus);
        editText_delete=findViewById(R.id.item_edit);
        editText = findViewById(R.id.item_edit_first);
        editText.setText("");
        sp = getSharedPreferences("temp", MODE_PRIVATE);
        dbHelper=new DBHelper(this);
        db=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        db=dbHelper.getReadableDatabase();
//        db.execSQL("DELETE FROM "+ TABLE_NAME);
        adapter=new myCheckListAdapter(this, R.layout.checklist_items,dbHelper.SelectAll(), this);
        final ListView listview = findViewById(R.id.listview);
        listview.setAdapter(adapter);
        //show(listview);
//        if(count==0 && !Objects.equals(s, "")){
//            items.add(new PreparationItem(b,s));
//            showlist(count, listview);
//        }

        s = sp.getString("id", "");
        b = sp.getBoolean("cb", false);

        plus.setOnClickListener(v -> {
            db=dbHelper.getWritableDatabase();
            items.add(new PreparationItem(false,editText.getText().toString()));
            dbHelper.insertContent(editText.getText().toString(), String.valueOf(false));
            //setpref(b,editText.getText().toString());
            //s=editText.getText().toString();
            //count++;
            //showlist(count, listview);
            editText.setText("");
            show(listview);
        });
//        myCheckListAdapter adapter=new myCheckListAdapter(this, R.layout.checklist_items,dbHelper.SelectAll(), this);
//        checkList=dbHelper.SelectAll();
//        delete = findViewById(R.id.btn_delete);
//        delete.setOnClickListener(v->{
//
//            //show(listview);
//        });

        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.d("@@@Changed", "mListView.getFirstVisiblePosition()=" + listview.getFirstVisiblePosition());
                Log.d("@@@Changed", "mListView.getLastVisiblePosition()=" + listview.getLastVisiblePosition());
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

//        checkBox.setOnClickListener(v -> {
//            if (checkBox.isChecked()) {
//                // TODO : CheckBox is checked.
//            } else {
//                // TODO : CheckBox is unchecked.
//            }
//        });

    }
    @Override
    public void onListBtnClick(int position, int resourceid) {
        //Object selected =  checkList.get(position);
        switch(resourceid){
            case  R.id.btn_delete:
                if(dbHelper!=null)
                    db=dbHelper.getWritableDatabase();
                    s=editText_delete.getText().toString();
                    db.execSQL("delete from "+TABLE_NAME+" WHERE INFO ="+ s);
                    myCheckListAdapter adapter=new myCheckListAdapter(this, R.layout.checklist_items,dbHelper.SelectAll(), this);
                    ListView listview = findViewById(R.id.listview);
                    listview.setAdapter(adapter);
                    break;
        }
    }
    public void show(ListView listView) {
        db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+ TABLE_NAME, null);
        ArrayList item_db= new ArrayList<>();

        try {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        //테이블에서 두개의 컬럼값을 가져와서
                        @SuppressLint("Range") String info = c.getString(c.getColumnIndex(INFO));
                        @SuppressLint("Range") String state = c.getString(c.getColumnIndex(STATE));

                        //HashMap에 넣습니다.
                        //HashMap<String, String> persons = new HashMap<String, String>();

                        item_db.add(new PreparationItem(Boolean.valueOf(state), info));
                        //persons.put(INFO, info);
                        //persons.put(STATE, state);
                        Log.d(TAG, info+"++++++++++++++++"+state);
                        //ArrayList에 추가합니다..
                        //item_db.add(persons);

                    } while (c.moveToNext());
                }
            }
            db.close();

            //새로운 apapter를 생성하여 데이터를 넣은 후..
//            SimpleAdapter adapter = new SimpleAdapter(
//                    this, item_db, R.layout.checklist_items,
//                    new String[]{INFO, STATE},
//                    new int[]{R.id.item_edit, R.id.checkbox}
//            );
            //새로운 apapter를 생성하여 데이터를 넣은 후..
            PreparationAdapter adapter = new PreparationAdapter(
                    this, item_db, listView);
            //화면에 보여주기 위해 Listview에 연결합니다.
            listView.setAdapter(adapter);

        } catch (
                SQLiteException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }
    }


    public void showlist(int count, ListView listview){
        if(count!=0){
            //final PreparationAdapter preparationAdapter = new PreparationAdapter(this, items, listview);
            // 6. ListView 객체에 adapter 객체를 연결합니다.
            final PreparationAdapter preparationAdapter = new PreparationAdapter(this, items, listview);
            // 6. ListView 객체에 adapter 객체를 연결합니다.
            listview.setAdapter(preparationAdapter);
            Log.d(TAG, "저장소 :id  "+ sp.getString("id", "")+ "???"+sp.getBoolean("cb", false));
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

//        try{sp = getSharedPreferences("temp", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        editor.putString("id", editText.getText().toString());
//        editor.putBoolean("cb", false);
//        editor.apply();
//            Log.d(TAG, "onDestroy: 저장"+editText.getText());
//        }
//        catch (NullPointerException e){
//            Log.d(TAG, "onStop: ddddd"+ e);
//        }
    }
    public void setpref(boolean a, String b) {
        try{sp = getSharedPreferences("temp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("id", b);
            editor.putBoolean("cb", a);
            editor.apply();
            Log.d(TAG, " setpref() 저장"+editText.getText());
        }
        catch (NullPointerException e){
            Log.d(TAG, "onStop: ddddd"+ e);
        }
    }
//    private void settingPrefs(){
//        sp = getSharedPreferences("temp", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sp.edit();
//        s=sp.getString("id", null);
//
//    }
}