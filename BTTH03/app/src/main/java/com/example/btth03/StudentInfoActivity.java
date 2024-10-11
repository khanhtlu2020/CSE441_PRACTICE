package com.example.btth03;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StudentInfoActivity extends AppCompatActivity {
    private ImageView img;
    private TextView tv_id,tv_name,tv_birth_date,tv_address,tv_gender,tv_email,tv_major,tv_gpa,tv_year;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // find by id
        img = findViewById(R.id.img);
        tv_id = findViewById(R.id.tv_id);
        tv_name = findViewById(R.id.tv_name);
        tv_birth_date = findViewById(R.id.tv_birth_date);
        tv_address = findViewById(R.id.tv_address);
        tv_gender = findViewById(R.id.tv_gender);
        tv_email = findViewById(R.id.tv_email);
        tv_major = findViewById(R.id.tv_major);
        tv_gpa = findViewById(R.id.tv_gpa);
        tv_year = findViewById(R.id.tv_year);
        // logic processing
        // img gender
        Student student = (Student) getIntent().getSerializableExtra("student_data");
        if(student.getGender().trim().equals("Nữ")){
            img.setImageResource(R.drawable.ic_female);
        }else {
            img.setImageResource(R.drawable.ic_male);
        }
        // id
        tv_id.setText("Mã sinh viên: "+student.getId());
        // name
        tv_name.setText("Họ và tên: "+student.getFull_name().toString());
        tv_address.setText("Địa chỉ: "+student.getAddress());
        tv_email.setText("Email: "+student.getEmail());
        tv_gender.setText("Giới tính: "+student.getGender());
        tv_birth_date.setText("Ngày sinh: "+student.getBirth_date());
        tv_major.setText("Chuyên ngành: "+student.getMajor());
        tv_gpa.setText("Điểm TB tích lũy: "+student.getGpa());
        tv_year.setText("Sinh viên năm thứ: "+student.getYear());
    }
}