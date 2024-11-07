package fpoly.account.ph56774_thi19.Ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fpoly.account.ph56774_thi19.Adapter.Adapter;
import fpoly.account.ph56774_thi19.DAO.SanPhamDAO;
import fpoly.account.ph56774_thi19.Model.SanPham;
import fpoly.account.ph56774_thi19.lab1_and2.R;
import jp.wasabeef.recyclerview.animators.SlideInDownAnimator;

public class MainActivity extends AppCompatActivity {
    private EditText edtTitle, edtContent, edtDate, edtStyle, edtSrc;
    private Button btnAdd, btnUpdate, btnDelete;
    private RecyclerView rcvSP;
    private ArrayList<SanPham> lst;
    private SanPhamDAO dao;
    private SanPham selectedSanPham = null;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        dao = new SanPhamDAO(MainActivity.this);
        initView();
        lst = dao.getSP();
        adapter = new Adapter(MainActivity.this, lst);
        // Lấy dữ liệu từ DB
        loadData();

        // Cài đặt adapter cho RecyclerView
        setAdapter();

        // Thêm sự kiện cho nút Add
        addData();

        // Thêm sự kiện cho nút Update và Delete
        updateData();
        deleteData();
        SlideInDownAnimator animator = new SlideInDownAnimator();
        animator.setAddDuration(1000); // Thời gian hiệu ứng khi thêm item
        animator.setRemoveDuration(1000); // Thời gian hiệu ứng khi xóa item
        rcvSP.setItemAnimator(animator);
    }

    private void initView() {
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        edtDate = findViewById(R.id.edtDate);
        edtStyle = findViewById(R.id.edtStyle);
        edtSrc = findViewById(R.id.edtSrc);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        rcvSP = findViewById(R.id.rcvSP);
    }

    private void addData() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String date = edtDate.getText().toString();
                String type = edtStyle.getText().toString();
                String src = edtSrc.getText().toString(); // Lấy giá trị từ EditText src

                if (validateInput(title, content, date, type, src)) { // Kiểm tra giá trị của src
                    SanPham todoModel = new SanPham(title, content, date, type, src, 0);
                    if (dao.add(todoModel)) {
                        lst.add(todoModel);
                        adapter.notifyDataSetChanged();
                    }
                    clearInputFields();
                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateData() {
        btnUpdate.setOnClickListener(v -> {
            if (selectedSanPham != null) {
                String title = edtTitle.getText().toString();
                String content = edtContent.getText().toString();
                String date = edtDate.getText().toString();
                String style = edtStyle.getText().toString();
                String src = edtSrc.getText().toString();

                SanPham updatedSanPham = new SanPham(title, content, date, style, src, selectedSanPham.getId());
                if (dao.update(updatedSanPham)) {
                    // Cập nhật sản phẩm trong danh sách và notify adapter
                    int position = lst.indexOf(selectedSanPham);
                    lst.set(position, updatedSanPham);
                    adapter.notifyItemChanged(position);
                    clearInputFields();  // Làm sạch các trường nhập
                    selectedSanPham = null;  // Reset sản phẩm đã chọn
                } else {
                    Toast.makeText(getApplicationContext(), "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteData() {
        btnDelete.setOnClickListener(v -> {
            if (selectedSanPham != null) {
                new AlertDialog.Builder(this)
                        .setTitle("Xác nhận xóa")
                        .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            if (dao.delete(selectedSanPham.getId())) {
                                // Xóa sản phẩm trong danh sách và notify adapter
                                lst.remove(selectedSanPham);
                                adapter.notifyDataSetChanged();
                                clearInputFields();  // Làm sạch các trường nhập
                                selectedSanPham = null;  // Reset sản phẩm đã chọn
                                Toast.makeText(getApplicationContext(), "Xóa thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });
    }


    private boolean validateInput(String title, String content, String date, String type, String src) {
        return !title.isEmpty() && !content.isEmpty() && !date.isEmpty() && !type.isEmpty() && !src.isEmpty();
    }

    private void clearInputFields() {
        edtTitle.setText("");
        edtContent.setText("");
        edtDate.setText("");
        edtStyle.setText("");
        edtSrc.setText("");
    }

    private void loadData() {
        lst.clear();
        lst.addAll(dao.getSP());  // Lấy danh sách sản phẩm từ cơ sở dữ liệu
        adapter.notifyDataSetChanged();
    }

    private void setAdapter() {
        rcvSP.setAdapter(adapter);
        rcvSP.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter.setOnItemClickListener(sanPham -> {
            selectedSanPham = sanPham;
            edtTitle.setText(sanPham.getTitle());
            edtContent.setText(sanPham.getContent());
            edtDate.setText(sanPham.getDate());
            edtStyle.setText(sanPham.getStyle());
            edtSrc.setText(sanPham.getSrc());
        });
    }
}
