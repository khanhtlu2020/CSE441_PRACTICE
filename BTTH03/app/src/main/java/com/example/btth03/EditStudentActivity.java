package com.example.btth03;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class EditStudentActivity extends AppCompatActivity {
    private TextInputLayout ipl_id,ipl_name,ipl_birthdate,ipl_email,ipl_gpa;
    private TextInputEditText edt_id,edt_name,edt_birthdate,edt_email,edt_gpa;
    private Spinner address, major, year;
    private RadioGroup gender;
    private Button btn_finish;
    private Student student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_student);
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
        student = (Student) getIntent().getSerializableExtra("student_data");
        loadData();
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStudent();
            }
        });
    }
    public void updateStudent(){
        student = getUpdateStudent();
        Intent i = new Intent(EditStudentActivity.this,StudentInfoActivity.class);
        i.putExtra("student_update",student);
        setResult(9,i);
        finish();
    }
    public Student getUpdateStudent(){
        Student student1 = null;
        String id = edt_id.getText().toString().trim();
        String name = edt_name.getText().toString().trim();
        String birth_date = edt_birthdate.getText().toString().trim();
        String email = edt_email.getText().toString().trim();
        String gpa = edt_gpa.getText().toString().trim();
        String dc = address.getSelectedItem().toString();
        String chuyen_nganh = major.getSelectedItem().toString();
        String nam = year.getSelectedItem().toString();
        RadioButton radiogt = findViewById(gender.getCheckedRadioButtonId());
        String gt = radiogt.getText().toString();
        Full_name full_name = new Full_name(name);
        student1 = new Student(id,gt,birth_date,email,dc,chuyen_nganh,Float.parseFloat(gpa),Integer.parseInt(nam),full_name);
        return student1;
    }
    public void loadData(){
        edt_id.setText(student.getId());
        edt_birthdate.setText(student.getBirth_date());
        edt_email.setText(student.getEmail());
        edt_gpa.setText(student.getGpa()+"");
        edt_name.setText(student.getFull_name().toString());
        // address, major, year
        address.setSelection(getAddressIndex(getResources().getStringArray(R.array.address)));
        major.setSelection(getMajorIndex(getResources().getStringArray(R.array.major)));
        year.setSelection(getYearIndex(getResources().getStringArray(R.array.year)));
        // gender
        for (int i = 0; i < gender.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) gender.getChildAt(i);
            if (radioButton.getText().toString().equals(student.getGender())) {
                radioButton.setChecked(true); // Đặt RadioButton này là được chọn
                break; // Thoát khỏi vòng lặp khi đã tìm thấy
            }
        }
    }
    public int getAddressIndex(String[] a){
        int index = -1;
        for (int i = 0; i < a.length; i++) {
            if(a[i].equals(student.getAddress())){
                index=i;
            }
        }
        return index;
    }
    public int getMajorIndex(String[] a){
        int index = -1;
        for (int i = 0; i < a.length; i++) {
            if(a[i].equals(student.getMajor())){
                index=i;
            }
        }
        return index;
    }
    public int getYearIndex(String[] a){
        int index = -1;
        for (int i = 0; i < a.length; i++) {
            if(a[i].equals(student.getYear()+"")){
                index=i;
            }
        }
        return index;
    }
}
