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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
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

                        database.getReference().child("notes")
                                .orderByChild("mssv")
                                .equalTo(mssv)
                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        progressDialog.dismiss();
                                        if (snapshot.exists()) {
                                            mssvLayout.setError("Mã số sinh viên bị trùng!");
                                        } else {
                                            mssvLayout.setError(null);
                                            // Thêm dữ liệu vào Firebase nếu MSSV không trùng
                                            ProgressDialog dialog = new ProgressDialog(MainActivity.this);
                                            dialog.setMessage("Đang lưu vào cơ sở dữ liệu...");
                                            dialog.show();

                                            Student student = new Student();
                                            student.setTen(hoten);
                                            student.setMssv(mssv);
                                            student.setLop(lop);
                                            student.setDiemtb(diemtb);

                                            database.getReference().child("notes").push()
                                                    .setValue(student)
                                                    .addOnSuccessListener(unused -> {
                                                        dialog.dismiss();
                                                        alertDialog.dismiss();
                                                        Toast.makeText(MainActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                                                    })
                                                    .addOnFailureListener(e -> {
                                                        dialog.dismiss();
                                                        Toast.makeText(MainActivity.this, "Có lỗi khi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                                                    });
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        progressDialog.dismiss();
                                        Toast.makeText(MainActivity.this, "Lỗi khi kiểm tra MSSV", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    });
                });
                alertDialog.show();
            }
        });

        TextView empty = findViewById(R.id.empty);

        RecyclerView recyclerView = findViewById(R.id.recycler);

        database.getReference().child("notes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Student> arrayList = new ArrayList<>();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Student student = dataSnapshot.getValue(Student.class);
                    Objects.requireNonNull(student).setKey(dataSnapshot.getKey());
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

                adapter.setOnItemClickListener(new StudentAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Student student) {
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

                        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);

                        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Sửa thông tin sinh viên")
                                .setView(view)
                                .setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (Objects.requireNonNull(hotenET.getText()).toString().isEmpty()) {
                                            hotenLayout.setError("Không được để trống!");
                                        } else if (Objects.requireNonNull(mssvET.getText()).toString().isEmpty()) {
                                            mssvLayout.setError("Không được để trống!");
                                        } else if (Objects.requireNonNull(lopET.getText()).toString().isEmpty()) {
                                            lopLayout.setError("Không được để trống!");
                                        } else if (Objects.requireNonNull(diemtbET.getText()).toString().isEmpty()) {
                                            diemtbLayout.setError("Không được để trống!");
                                        } else {
                                            progressDialog.setMessage("Đang lưu...");
                                            progressDialog.show();
                                            Student student1 = new Student();
                                            student1.setTen(hotenET.getText().toString());
                                            student1.setMssv(mssvET.getText().toString());
                                            student1.setLop(lopET.getText().toString());
                                            student1.setDiemtb(diemtbET.getText().toString());
                                            database.getReference().child("notes").child(student.getKey()).setValue(student1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    progressDialog.dismiss();
                                                    dialogInterface.dismiss();
                                                    Toast.makeText(MainActivity.this, "Lưu thành công!", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(MainActivity.this, "Có lỗi khi lưu dữ liệu", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    }
                                })
                                .setNeutralButton("Đóng", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        progressDialog.setTitle("Đang xóa...");
                                        progressDialog.show();
                                        database.getReference().child("notes").child(student.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).create();
                        alertDialog.show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}