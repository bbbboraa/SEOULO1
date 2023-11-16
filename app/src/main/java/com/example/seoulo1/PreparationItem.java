package com.example.seoulo1;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.Objects;

public class PreparationItem {
    boolean checked;
    String itemString, newitemString;
    public TextWatcher mTextWatcher;

    PreparationItem(boolean checked, String itemString){
        this.checked =checked;
        this.itemString=itemString;
        this.newitemString="";
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //변경된 값을 저장한다
                newitemString= s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        };
    }
    public String getItemString(){
        return itemString;
    }
    public boolean getChecked(){
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setItemString(String itemString) {
        this.itemString = itemString;
    }

    public boolean isChecked(){
        return checked;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PreparationItem other = (PreparationItem) obj;
        // 여러 필드를 비교하여 객체가 동일한지 확인
        return checked == other.checked && itemString.equals(other.itemString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(checked, itemString);
    }

}
