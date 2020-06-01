package com.uas.perpustakaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.uas.perpustakaan.R;
import com.uas.perpustakaan.helper.SessionManager;
import com.uas.perpustakaan.helper.UtilMessage;
import com.uas.perpustakaan.model.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.uas.perpustakaan.helper.GlobalVariable.BASE_URL;

public class BookDetailActivity extends AppCompatActivity {
    private ImageView ivImage;
    private TextView tvTittle, tvAuthor, tvContent, tvDate;
    private boolean isMine;
    private Book book;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        ivImage = findViewById(R.id.iv_image);
        tvTittle = findViewById(R.id.tv_title);
        tvAuthor = findViewById(R.id.tv_author);
        tvContent = findViewById(R.id.tv_content);
        tvDate = findViewById(R.id.tv_date);
        utilMessage = new UtilMessage(this);
        sessionManager = new SessionManager(this);

        if(sessionManager.isLoggedInAdmin()){
            isMine = true;
        }


        book = (Book) getIntent().getExtras().get("data");
        setData(book);
    }

    private void setData(Book book) {
        if(book != null){
            if(getSupportActionBar() != null){
                getSupportActionBar().setTitle(book.getTitle());
            }
            Glide.with(this)
                    .load(book.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivImage);
            tvTittle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvContent.setText(book.getContent());
            tvDate.setText(book.getReleaseBook());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isMine == true){
            getMenuInflater().inflate(R.menu.menu_edit_delete, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.menu_book_buku, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_edit:
                Intent intentEdit = new Intent(this, EditBookActivity.class);
                intentEdit.putExtra("data", book);
                startActivity(intentEdit);
                break;
            case R.id.action_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Anda yakin menghapus \"" + book.getTitle() +"\"?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        StringRequest request = new StringRequest(Request.Method.POST,
                                BASE_URL + "hapus_berita.php",
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        utilMessage.dismissProgressBar();

                                        try{
                                            JSONObject jsonResponse = new JSONObject(response);

                                            int status = jsonResponse.getInt("status");
                                            String message = jsonResponse.getString("message");

                                            Toast.makeText(BookDetailActivity.this, message,Toast.LENGTH_SHORT).show();

                                            if(status == 0){
                                                finish();
                                            }
                                        } catch (JSONException e){
                                            Toast.makeText(BookDetailActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        utilMessage.dismissProgressBar();
                                        Toast.makeText(BookDetailActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> params = new HashMap<>();
                                params.put("id", book.getId());
                                return params;

                            }
                        };
                        utilMessage.showProgressBar("");
                        Volley.newRequestQueue(BookDetailActivity.this).add(request);
                    }
                });
                builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.action_book:
                Intent intentbooking = new Intent(this, BookingActivity.class);
                intentbooking.putExtra("data", book);
                startActivity(intentbooking);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
