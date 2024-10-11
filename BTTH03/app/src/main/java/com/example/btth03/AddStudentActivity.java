package com.example.btth03;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddStudentActivity extends AppCompatActivity {
    private TextInputLayout ipl_id,ipl_name,ipl_birthdate,ipl_email,ipl_gpa;
    private TextInputEditText edt_id,edt_name,edt_birthdate,edt_email,edt_gpa;
    private Spinner address, major, year;
    private RadioGroup gender;
    private Button btn_finish;
    private String validated_string = "không được để trống.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ipl_id = findViewById(R.id.ipl_id);
        ipl_name = findViewById(R.id.ipl_name);
        ipl_birthdate = findViewById(R.id.ipl_birthdate);
        ipl_email = findViewById(R.id.ipl_email);
        ipl_gpa = findViewById(R.id.ipl_gpa);
        edt_id = findViewById(R.id.edt_id);
        edt_name = findViewById(R.id.edt_name);
        edt_birthdate = findViewById(R.id.edt_birthdate);
        edt_email = findViewById(R.id.edt_email);
        edt_gpa = findViewById(R.id.edt_gpa);
        address = findViewById(R.id.address);
        major = findViewById(R.id.major);
        year = findViewById(R.id.year);
        gender = findViewById(R.id.gender);
        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudent();
            }
        });
    }
    private void addStudent(){
        Student student = getData();
        if(student!=null){
            Intent i = new Intent(AddStudentActivity.this,StudentListActivity.class);
            i.putExtra("new_student",student);
            setResult(1,i);
            finish();
        }
    }
    private Student getData(){
        Student student = null;
        String id = edt_id.getText().toString().trim();
        String name = edt_name.getText().toString().trim();
        String birth_date = edt_birthdate.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String gpa = edt_gpa.getText().toString().trim();
        boolean valid = true;
        if(TextUtils.isEmpty(id)){
            ipl_id.setError("Mã sinh viên "+validated_string);
            valid=false;
        }else {
            ipl_id.setError(null);
        }
        if(TextUtils.isEmpty(name)){
            ipl_name.setError("Họ và tên "+validated_string);
            valid=false;
        }else {
            ipl_name.setError(null);
        }
        if(TextUtils.isEmpty(birth_date)){
            ipl_birthdate.setError("Ngày sinh "+validated_string);
            valid=false;
        }else {
            ipl_birthdate.setError(null);
        }
        if(TextUtils.isEmpty(email)){
            ipl_email.setError("Email "+validated_string);
            valid=false;
        }else {
            ipl_email.setError(null);
        }
        if(TextUtils.isEmpty(gpa)){
            ipl_gpa.setError("GPA "+validated_string);
            valid=false;
        }else {
            ipl_gpa.setError(null);
        }
        if(valid){
            String dc = address.getSelectedItem().toString();
            String chuyen_nganh = major.getSelectedItem().toString();
            String nam = year.getSelectedItem().toString();
            RadioButton radiogt = findViewById(gender.getCheckedRadioButtonId());
            String gt = radiogt.getText().toString();
            Full_name full_name = new Full_name(name);
            student = new Student(id,gt,birth_date,email,dc,chuyen_nganh,Float.parseFloat(gpa),Integer.parseInt(nam),full_name);
        }
        return student;
    }
}
