package com.example.seoulo1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AddressViewModel extends ViewModel {
    // LiveData를 사용하여 데이터 관리
    private MutableLiveData<List<String>> addressList = new MutableLiveData<>();
    private MutableLiveData<String> departureAddress = new MutableLiveData<>();
    private MutableLiveData<String> arrivalAddress = new MutableLiveData<>();

    // Getter 및 Setter 메서드 작성
    public LiveData<List<String>> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<String> addresses) {
        addressList.setValue(addresses);
    }

    public LiveData<String> getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(String address) {
        departureAddress.setValue(address);
    }

    public LiveData<String> getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(String address) {
        arrivalAddress.setValue(address);
    }
}


