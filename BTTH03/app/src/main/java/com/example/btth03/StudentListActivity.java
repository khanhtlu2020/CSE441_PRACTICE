package com.example.btth03;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity implements studentAdapter.OnItemClickListener {
    private List<Student> students;
    private RecyclerView rv;
    private studentAdapter adapter;
    private FloatingActionButton btn_add;
    private final int ADD = 1, DELETE = 2, GO_TO_DETAIL = 0, UPDATE = 3;
    TextView txtSV;
    ImageView cancel_search,imgSearch;
    EditText edt_search;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // Nạp menu
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.student_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        rv = findViewById(R.id.rv);
        // set data for RecyclerView
        students = loadData();
        adapter = new studentAdapter(students, this);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        //
        btn_add = findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StudentListActivity.this, AddStudentActivity.class);
                startActivityForResult(i, ADD);
            }
        });
        txtSV = findViewById(R.id.txtSV);
        imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSV.setVisibility(View.GONE);
                imgSearch.setVisibility(View.GONE);
                edt_search.setVisibility(View.VISIBLE);
                cancel_search.setVisibility(View.VISIBLE);
            }
        });
        edt_search = findViewById(R.id.edt_search);
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                adapter.updateData(search(edt_search.getText().toString().trim()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cancel_search = findViewById(R.id.cancel_search);
        cancel_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSV.setVisibility(View.VISIBLE);
                imgSearch.setVisibility(View.VISIBLE);
                edt_search.setVisibility(View.GONE);
                cancel_search.setVisibility(View.GONE);
                adapter.updateData(students);
            }
        });
    }

    public List<Student> loadData() {
        List<Student> students = new ArrayList<>();
        try {
            InputStream fis = getResources().openRawResource(R.raw.students);
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            String json = new String(buffer, "UTF-8");

            Gson gson = new Gson();
            students = gson.fromJson(json, new TypeToken<List<Student>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public void deleteStudent(int position) {
        students.remove(position);
        adapter.notifyItemRemoved(position);
    }

    public void addStudent(Student s) {
        students.add(s);
        adapter.notifyItemInserted(students.size());
    }

    public void updateStudent(Student student, int position) {
        students.set(position, student);
        adapter.notifyItemChanged(position);
    }

    @Override
    public void OnItemClicked(int position) {
        Intent i = new Intent(this, StudentInfoActivity.class);
        i.putExtra("student_data", students.get(position));
        i.putExtra("position", position);
        startActivityForResult(i, GO_TO_DETAIL);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == DELETE) {
            int position = data.getIntExtra("position", 0);
            deleteStudent(position);
        } else if (data != null && resultCode == ADD) {
            Student student = (Student) data.getSerializableExtra("new_student");
            addStudent(student);
        } else if (data != null && resultCode == UPDATE) {
            int position = data.getIntExtra("position", 0);
            Student student = (Student) data.getSerializableExtra("student_update");
            updateStudent(student, position);
        }
    }

    public List<Student> search(String keyword) {
        List<Student> searchStudents = new ArrayList<>();
        String key = capitalizeFirstLetterOfEachWord(keyword);
        for (Student s : students) {
            if(s.getFull_name().toString().trim().contains(key)){
                searchStudents.add(s);
            }
        }
        return searchStudents;
    }

    public static String capitalizeFirstLetterOfEachWord(String input) {
        // Tách chuỗi thành các từ
        String[] words = input.split(" ");
        StringBuilder capitalizedWords = new StringBuilder();

        for (String word : words) {
            // Kiểm tra nếu từ không rỗng
            if (word.length() > 0) {
                // Viết hoa chữ cái đầu và thêm phần còn lại
                String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1);
                capitalizedWords.append(capitalizedWord).append(" ");
            }
        }

        // Trả về chuỗi đã được viết hoa, loại bỏ khoảng trắng thừa ở cuối
        return capitalizedWords.toString().trim();
    }
}