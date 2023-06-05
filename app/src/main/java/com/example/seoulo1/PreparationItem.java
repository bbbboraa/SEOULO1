package com.example.seoulo1;

public class PreparationItem {
    boolean checked;
    String itemString;
    PreparationItem(boolean checked, String itemString){
        this.checked =checked;
        this.itemString=itemString;
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
}
