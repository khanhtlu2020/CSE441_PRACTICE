package com.example.btth03;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.InputStream;

public class StudentInfoActivity extends AppCompatActivity {
    private ImageView img;
    private TextView tv_id,tv_name,tv_birth_date,tv_address,tv_gender,tv_email,tv_major,tv_gpa,tv_year;
    private Button btn; // test delete
    private Student student;
    private int position;
    private final int GO_TO_EDIT = 9;
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
        student = (Student) getIntent().getSerializableExtra("student_data");
        position = getIntent().getIntExtra("position",0);
        loadData();
        // test btn delete
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editStudent();
            }
        });
    }
    private void deleteStudent(int position){
        Intent i = new Intent(StudentInfoActivity.this,StudentListActivity.class);
        i.putExtra("position",position);
        setResult(2,i);
        finish();
    }
    private void editStudent(){
        Intent i = new Intent(StudentInfoActivity.this, EditStudentActivity.class);
        i.putExtra("student_data",student);
        i.putExtra("position",position);
        startActivityForResult(i,GO_TO_EDIT);
    }
    private void loadData(){
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            student = (Student)data.getSerializableExtra("student_update");
            loadData();
            Intent i = new Intent(StudentInfoActivity.this,StudentListActivity.class);
            i.putExtra("student_update",student);
            i.putExtra("position",position);
            setResult(3,i);
        }
    }
}