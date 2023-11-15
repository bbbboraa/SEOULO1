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
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PreparationItem other = (PreparationItem) obj;
        return itemString.equals(other.itemString); // 여기에서 name은 LocationItem 객체를 고유하게 식별하는 어떤 값인지에 따라 달라집니다.
    }

}
