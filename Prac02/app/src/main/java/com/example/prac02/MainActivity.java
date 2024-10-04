package com.example.prac02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private StaffViewModel staffViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        staffViewModel = new ViewModelProvider(this).get(StaffViewModel.class);


        EditText staffIdInput = findViewById(R.id.staffIdInput);
        EditText fullNameInput = findViewById(R.id.fullNameInput);
        EditText birthDateInput = findViewById(R.id.birthDateInput);
        EditText salaryInput = findViewById(R.id.salaryInput);
        Button addButton = findViewById(R.id.addButton);
        TextView staffListTextView = findViewById(R.id.staffListTextView);


        staffViewModel.getStaffList().observe(this, new Observer<List<Staff>>() {
            @Override
            public void onChanged(List<Staff> staffList) {
                if (staffList.isEmpty()) {
                    staffListTextView.setText("No Result!");
                } else {
                    StringBuilder displayText = new StringBuilder();
                    for (Staff staff : staffList) {
                        displayText.append(staff.getStaffId())
                                .append("-")
                                .append(staff.getFullName())
                                .append("-")
                                .append(staff.getBirthDate())
                                .append("-")
                                .append(staff.getSalary())
                                .append("\n");
                    }
                    staffListTextView.setText(displayText.toString());
                }
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String staffId = staffIdInput.getText().toString();
                String fullName = fullNameInput.getText().toString();
                String birthDate = birthDateInput.getText().toString();
                long salary = Long.parseLong(salaryInput.getText().toString());

                Staff newStaff = new Staff(staffId, fullName, birthDate, salary);
                staffViewModel.addStaff(newStaff);


                staffIdInput.setText("");
                fullNameInput.setText("");
                birthDateInput.setText("");
                salaryInput.setText("");
            }
        });
    }
}
