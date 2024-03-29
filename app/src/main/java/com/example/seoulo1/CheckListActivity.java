package com.example.seoulo1;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CheckListActivity extends AppCompatActivity implements myCheckListAdapter.ListBtnClickListener {
    List<PreparationItem> items= new ArrayList<>();

    //int count=0;
    ImageButton plus;

    CheckBox checkbox;
    EditText editText, editText_second;
    SharedPreferences sp;
    String  s;
    boolean b;
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    static final String DB_NAME = "Check.db";
    static final String TABLE_NAME = "Table_content";
    private final String INFO="info";
    private final String STATE="state";
    private myCheckListAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_list);
        plus = findViewById(R.id.btn_checkplus);
        editText_second=findViewById(R.id.item_edit);
        editText = findViewById(R.id.item_edit_first);
        editText.setText("");
        dbHelper=new DBHelper(this);
        db=openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null);
        db=dbHelper.getReadableDatabase();
//        db.execSQL("DELETE FROM "+ TABLE_NAME);
        final ListView listview = findViewById(R.id.listview);
        adapter=new myCheckListAdapter(this, R.layout.checklist_items,items, this);
        listview.setAdapter(adapter);
        show(listview);



        plus.setOnClickListener(v -> {
            db=dbHelper.getWritableDatabase();
            items.add(new PreparationItem(false,editText.getText().toString()));
            Log.d(TAG, items +" ###### 단어 등록 성공 ++++++++++++++");
            dbHelper.insertContent(editText.getText().toString(), String.valueOf(false));
            editText.setText("");
            adapter.notifyDataSetChanged(); // 리스트 갱신
            show(listview);
        });

        editText.setOnKeyListener((v, keyCode, event) -> {
            if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_UP)
                plus.callOnClick();
            return false;
        });

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


    }
    @SuppressLint({"NonConstantResourceId", "Range"})
    @Override
    public void onListBtnClick(int position, int resourceid) {
        db=dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c=db.rawQuery("select * from "+ TABLE_NAME, null);
        Log.d(TAG, position + "!!!!!! 리스트뷰 누름 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        switch (resourceid) {
            case R.id.btn_delete -> {
                if (dbHelper != null)
                    db = dbHelper.getWritableDatabase();
                //s=editText_second.getText().toString();
                if (c != null) c.moveToPosition(position);
                assert c != null;
                s = c.getString(c.getColumnIndex(INFO));
                b = Boolean.parseBoolean(c.getString(c.getColumnIndex(STATE)));
                PreparationItem selected = items.get(position);
                Log.d(TAG, selected + " ###### 단어 삭제 성공 ???????");
                //db.execSQL("delete from "+TABLE_NAME+" WHERE INFO="+s);
                if (!adapter.isEmpty() && position >= 0 && position < adapter.getCount()) {
                dbHelper.deleteData(selected.getItemString());
                //myCheckListAdapter adapter = new myCheckListAdapter(this, R.layout.checklist_items, dbHelper.SelectAll(), this);
                @SuppressLint("CutPasteId") ListView listview = findViewById(R.id.listview);
                items.remove(selected);
                adapter.notifyDataSetChanged();
                show(listview);
                break;}
            }
            case R.id.checkbox -> {
                Log.d(TAG, position + "!!!!!! 체크박스 버튼 누름 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                if (dbHelper != null)
                    db = dbHelper.getWritableDatabase();
                if (c != null) c.moveToPosition(position);
                assert c != null;
                s = c.getString(c.getColumnIndex(INFO));
                b = Boolean.parseBoolean(c.getString(c.getColumnIndex(STATE)));
                if (!b) {
                    //dbHelper.updateContent(s, String.valueOf(b));
                    dbHelper.updateContent(s, String.valueOf(true));
                    myCheckListAdapter adapter = new myCheckListAdapter(this, R.layout.checklist_items, items, this);
                    ListView listview = findViewById(R.id.listview);
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    show(listview);
                } else {
                    dbHelper.updateContent(s, String.valueOf(false));
                    myCheckListAdapter adapter = new myCheckListAdapter(this, R.layout.checklist_items, items, this);
                    ListView listview = findViewById(R.id.listview);
                    listview.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    show(listview);}
            }
            case R.id.item_edit -> {
                handleEnterKey(position);

                   break;
            }
        }
    }

    void handleEnterKey(int position) {
        db=dbHelper.getReadableDatabase();
        @SuppressLint("Recycle") Cursor c=db.rawQuery("select * from "+ TABLE_NAME, null);
        Log.d(TAG, position + "!!!!!! 리스트뷰 누름 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        if (c.moveToPosition(position)) {
            // 원하는 위치로 이동한 후 데이터를 가져옴
            EditText editText = findViewById(R.id.item_edit);
            editText.requestFocus();
            Log.d(TAG, "IME_ACTION_DONE 못드감 ㅋㅋㅋㅋ");
            editText.setImeOptions(EditorInfo.IME_ACTION_DONE);

            editText.setOnEditorActionListener(new EditText.OnEditorActionListener()  {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    Log.d(TAG, "onEditorAction: actionId = " + actionId + ", event = " + event);
                    final boolean isEnterEvent = event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
                    Log.d(TAG, "IME_ACTION_DONE if");
                    final boolean isEnterUpEvent = isEnterEvent && event.getAction() == KeyEvent.ACTION_UP;
                    final boolean isEnterDownEvent = isEnterEvent && event.getAction() == KeyEvent.ACTION_DOWN;
                    if ( actionId == EditorInfo.IME_ACTION_DONE || isEnterUpEvent || isEnterDownEvent) {
                        PreparationItem item = items.get(position);
                        item.setItemString(v.getText().toString());
                        Log.d(TAG, v.getText().toString() + "준비물 수정 ...... ");
                        // 어댑터에 데이터셋 변경을 알립니다.
                        dbHelper.updateContent(v.getText().toString(), String.valueOf(item.getChecked()));
                        // 키보드를 숨깁니다.
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        ListView listview = findViewById(R.id.listview);
                        listview.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        show(listview);

                        return true; // 이벤트가 소비되었음을 알립니다.
                    }                        Log.d(TAG, "IME_ACTION_DONE else");
                    return false;

                }
            });
//                    String s = c.getString(c.getColumnIndex(INFO));
//                    Log.d(TAG, "Selected item at position " + position + ": " + s); // 추가한 로그
//                    PreparationItem selected = items.get(position);
//                    dbHelper.updateContent(s, String.valueOf(selected.getChecked()));
//                    adapter.updateItemText(position, selected.getItemString()); // 어댑터에서 리스트 업데이트
//                    //myCheckListAdapter adapter = new myCheckListAdapter(this, R.layout.checklist_items, dbHelper.SelectAll(), this);
//                    @SuppressLint("CutPasteId") ListView listview = findViewById(R.id.listview);
//                    selected.setItemString(s);
//                    items.set(position, selected);
//                    adapter.notifyDataSetChanged();
//                    show(listview);
        } else {
            Log.e(TAG, "Failed to move cursor to position: " + position);
        }

    }

    public void show(ListView listView) {
        db=dbHelper.getReadableDatabase();
        Cursor c=db.rawQuery("select * from "+ TABLE_NAME, null);
        List<PreparationItem> item_db= new ArrayList<>();
        Log.d(TAG, items + "!!!!!!!!!!!!!!!!!!!!!!!!11show: ");
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
                        items.add(new PreparationItem(Boolean.valueOf(state), info));
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
//            //새로운 apapter를 생성하여 데이터를 넣은 후..
//            PreparationAdapter adapter = new PreparationAdapter(
//                    this, item_db, listView);
//            //화면에 보여주기 위해 Listview에 연결합니다.
//            listView.setAdapter(adapter);
            myCheckListAdapter adapter = new myCheckListAdapter(this, R.layout.checklist_items, item_db, this);
            listView.setAdapter(adapter);


        } catch (
                SQLiteException se) {
            Toast.makeText(getApplicationContext(), se.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("", se.getMessage());
        }
    }

}