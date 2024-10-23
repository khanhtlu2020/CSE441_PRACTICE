package com.example.btth04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FloatingActionButton add = findViewById(R.id.addStudent);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_student_dialog, null);
                TextInputLayout hotenLayout, mssvLayout, lopLayout, diemtbLayout;
                hotenLayout = view1.findViewById(R.id.hotenLayout);
                mssvLayout = view1.findViewById(R.id.mssvLayout);
                lopLayout = view1.findViewById(R.id.lopLayout);
                diemtbLayout = view1.findViewById(R.id.diemtbLayout);
                TextInputEditText hotenET, mssvET, lopET, diemtbET;
                hotenET = view1.findViewById(R.id.hotenET);
                mssvET = view1.findViewById(R.id.mssvET);
                lopET = view1.findViewById(R.id.lopET);
                diemtbET = view1.findViewById(R.id.diemtbET);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Thêm sinh viên")
                        .setView(view1)
                        .setPositiveButton("Thêm", null)
                        .setNegativeButton("Hủy", (dialogInterface, i) -> dialogInterface.dismiss())
                        .create();

                alertDialog.setOnShowListener(dialog -> {
                    Button button = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(view2 -> {
                        String hoten = Objects.requireNonNull(hotenET.getText()).toString().trim();
                        String mssv = Objects.requireNonNull(mssvET.getText()).toString().trim();
                        String lop = Objects.requireNonNull(lopET.getText()).toString().trim();
                        String diemtb = Objects.requireNonNull(diemtbET.getText()).toString().trim();

                        // Kiểm tra các trường không được để trống
                        if (hoten.isEmpty()) {
                            hotenLayout.setError("Không được để trống!");
                            return;
                        } else {
                            hotenLayout.setError(null);
                        }

                        if (mssv.isEmpty()) {
                            mssvLayout.setError("Không được để trống!");
                            return;
                        } else {
                            mssvLayout.setError(null);
                        }

                        if (lop.isEmpty()) {
                            lopLayout.setError("Không được để trống!");
                            return;
                        } else {
                            lopLayout.setError(null);
                        }

                        if (diemtb.isEmpty()) {
                            diemtbLayout.setError("Không được để trống!");
                            return;
                        } else {
                            diemtbLayout.setError(null);
                        }

                        // Kiểm tra MSSV có bị trùng hay không
                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                        progressDialog.setMessage("Đang kiểm tra MSSV...");
                        progressDialog.show();

                        db.collection("students")
                                .whereEqualTo("mssv", mssv)
                                .get()
                                .addOnCompleteListener(task -> {
                                    progressDialog.dismiss();
                                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                                        mssvLayout.setError("Mã số sinh viên bị trùng!");
                                    } else {
                                        mssvLayout.setError(null);
                                        // Thêm dữ liệu vào Firestore nếu MSSV không trùng
                                        ProgressDialog saveProgressDialog = new ProgressDialog(MainActivity.this);
                                        saveProgressDialog.setMessage("Đang lưu vào cơ sở dữ liệu...");
                                        saveProgressDialog.show();

                                        Student student = new Student();
                                        student.setTen(hoten);
                                        student.setMssv(mssv);
                                        student.setLop(lop);
                                        student.setDiemtb(diemtb);

                                        db.collection("students").add(student)
                                                .addOnSuccessListener(documentReference -> {
                                                    saveProgressDialog.dismiss();
                                                    alertDialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                                                })
                                                .addOnFailureListener(e -> {
                                                    saveProgressDialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "Có lỗi khi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                })
                                .addOnFailureListener(e -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(MainActivity.this, "Lỗi khi kiểm tra MSSV", Toast.LENGTH_SHORT).show();
                                });
                    });
                });
                alertDialog.show();
            }
        });

        TextView empty = findViewById(R.id.empty);
        RecyclerView recyclerView = findViewById(R.id.recycler);

        db.collection("students").addSnapshotListener((snapshots, e) -> {
            if (e != null) {
                Toast.makeText(MainActivity.this, "Lỗi khi tải dữ liệu", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<Student> arrayList = new ArrayList<>();
            for (DocumentSnapshot document : snapshots) {
                Student student = document.toObject(Student.class);
                Objects.requireNonNull(student).setKey(document.getId());
                arrayList.add(student);
            }

            if (arrayList.isEmpty()) {
                empty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                empty.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
            }

            StudentAdapter adapter = new StudentAdapter(MainActivity.this, arrayList);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(student -> {
                View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.add_student_dialog, null);
                TextInputLayout hotenLayout, mssvLayout, lopLayout, diemtbLayout;
                TextInputEditText hotenET, mssvET, lopET, diemtbET;

                hotenET = view.findViewById(R.id.hotenET);
                mssvET = view.findViewById(R.id.mssvET);
                lopET = view.findViewById(R.id.lopET);
                diemtbET = view.findViewById(R.id.diemtbET);
                hotenLayout = view.findViewById(R.id.hotenLayout);
                mssvLayout = view.findViewById(R.id.mssvLayout);
                lopLayout = view.findViewById(R.id.lopLayout);
                diemtbLayout = view.findViewById(R.id.diemtbLayout);

                hotenET.setText(student.getTen());
                mssvET.setText(student.getMssv());
                lopET.setText(student.getLop());
                diemtbET.setText(student.getDiemtb());

                ProgressDialog updateProgressDialog = new ProgressDialog(MainActivity.this);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Sửa thông tin sinh viên")
                        .setView(view)
                        .setPositiveButton("Lưu", (dialogInterface, i) -> {
                            if (Objects.requireNonNull(hotenET.getText()).toString().isEmpty()) {
                                hotenLayout.setError("Không được để trống!");
                            } else if (Objects.requireNonNull(mssvET.getText()).toString().isEmpty()) {
                                mssvLayout.setError("Không được để trống!");
                            } else if (Objects.requireNonNull(lopET.getText()).toString().isEmpty()) {
                                lopLayout.setError("Không được để trống!");
                            } else if (Objects.requireNonNull(diemtbET.getText()).toString().isEmpty()) {
                                diemtbLayout.setError("Không được để trống!");
                            } else {
                                updateProgressDialog.setMessage("Đang lưu...");
                                updateProgressDialog.show();
                                Student student1 = new Student();
                                student1.setTen(hotenET.getText().toString());
                                student1.setMssv(mssvET.getText().toString());
                                student1.setLop(lopET.getText().toString());
                                student1.setDiemtb(diemtbET.getText().toString());
                                db.collection("students").document(student.getKey())
                                        .set(student1)
                                        .addOnSuccessListener(unused -> {
                                            updateProgressDialog.dismiss();
                                            dialogInterface.dismiss();
                                            Toast.makeText(MainActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                                        })
                                        .addOnFailureListener(f -> {
                                            updateProgressDialog.dismiss();
                                            Toast.makeText(MainActivity.this, "Có lỗi khi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        })
                        .setNeutralButton("Đóng", (dialogInterface, i) -> dialogInterface.dismiss())
                        .setNegativeButton("Xóa", (dialogInterface, i) -> {
                            updateProgressDialog.setTitle("Đang xóa...");
                            updateProgressDialog.show();
                            db.collection("students").document(student.getKey())
                                    .delete()
                                    .addOnSuccessListener(unused -> {
                                        updateProgressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(g -> {
                                        updateProgressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Có lỗi khi xóa!", Toast.LENGTH_SHORT).show();
                                    });
                        })
                        .create();
                alertDialog.show();
            });
        });
    }
}

