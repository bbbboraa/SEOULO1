package com.example.seoulo1;

public class HotplaceItem {
    private String itemName;
    private String naverUrl;

    public HotplaceItem(String itemName, String naverUrl) {
        this.itemName = itemName;
        this.naverUrl = naverUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public String getNaverUrl() {
        return naverUrl;
    }
}
