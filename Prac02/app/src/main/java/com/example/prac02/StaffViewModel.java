package com.example.prac02;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class StaffViewModel extends ViewModel {

    private MutableLiveData<List<Staff>> staffList;

    public StaffViewModel() {
        staffList = new MutableLiveData<>();
        staffList.setValue(new ArrayList<>());
    }


    public LiveData<List<Staff>> getStaffList() {
        return staffList;
    }


    public void addStaff(Staff staff) {
        List<Staff> currentList = staffList.getValue();
        if (currentList != null) {
            currentList.add(staff);
            staffList.setValue(currentList);
        }
    }
}
