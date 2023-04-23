package tdtu.edu.nnshop;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    private ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//
//        // Khai báo biến productList
//        productList = findViewById(R.id.product_list);
//
//        // Lấy danh sách sản phẩm từ cơ sở dữ liệu
//        List<Product> products = null;
//        try {
//            ProductDAO productDAO = new ProductDAOImpl();
//            products = productDAO.getAllProducts();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        // Tạo đối tượng ProductListAdapter và truyền danh sách sản phẩm vào
//        ProductListAdapter adapter = new ProductListAdapter(this, R.layout.product_list, products);
//
//        // Gán adapter cho productList
//        productList.setAdapter(adapter);
    }
}
