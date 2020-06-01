package com.uas.perpustakaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uas.perpustakaan.MainActivity;
import com.uas.perpustakaan.R;
import com.uas.perpustakaan.fragment.BookFragment;
import com.uas.perpustakaan.helper.SessionManager;
import com.uas.perpustakaan.helper.UtilMessage;
import com.uas.perpustakaan.model.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.uas.perpustakaan.helper.GlobalVariable.BASE_URL;

public class EditBookActivity extends AppCompatActivity {
    private EditText edtJudul, edtSinopsis, edtUrlGambar, edtPengarang;
    private Button btnSubmit;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;
    private DatePicker edt_date;
    private Book book;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        edtJudul = findViewById(R.id.edt_buku);
        edtSinopsis = findViewById(R.id.edt_sinopsis);
        edtPengarang = findViewById(R.id.edt_pengarang);
        edtUrlGambar = findViewById(R.id.edt_url_gambar);
        btnSubmit = findViewById(R.id.btn_submit);
        edt_date  = findViewById(R.id.edt_tanggal);
        textView = findViewById(R.id.tv_set);


        book = getIntent().getParcelableExtra("data");
        utilMessage = new UtilMessage(this);
        sessionManager = new SessionManager(this);

        setData(book);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }
    private void setData(Book book){
        edtJudul.setText(book.getTitle());
        edtSinopsis.setText(book.getContent());
        edtPengarang.setText(book.getAuthor());
        edtUrlGambar.setText(book.getImageUrl());


        String dtStart = book.getReleaseBook();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dtStart);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DATE);
            int month = c.get(Calendar.MONTH);
            int year = c.get(Calendar.YEAR);

            edt_date.updateDate(year, month, dayOfWeek);
        } catch (ParseException e){
            e.printStackTrace();
        }

    }
    private void submitData(){
        final String judul = edtJudul.getText().toString();
        final String sinopsis = edtSinopsis.getText().toString();
        final String pengarang = edtPengarang.getText().toString();
        final String urlGambar = edtUrlGambar.getText().toString();
        int   day  = edt_date.getDayOfMonth();
        int   month= edt_date.getMonth();
        int   year = edt_date.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String formatedDate = sdf.format(calendar.getTime());
        StringRequest request = new StringRequest(Request.Method.POST,
                BASE_URL + "edit_buku.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();
                        try {
                            JSONObject jsonRespone = new JSONObject(response);

                            int status = jsonRespone.getInt("status");
                            String message = jsonRespone.getString("message");
                            Toast.makeText(EditBookActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (status == 0 ){
                                finish();
                            }
                            else{
                                Toast.makeText(EditBookActivity.this, "Edit buku gagal " + message, Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch (JSONException e){
                            Toast.makeText(EditBookActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilMessage.dismissProgressBar();
                Toast.makeText(EditBookActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("judul", judul);
                params.put("sinopsis", sinopsis);
                params.put("gambar", urlGambar);
                params.put("pengarang", pengarang);
                params.put("tanggal", formatedDate);
                params.put("id_admin", sessionManager.getUserIdAdmin());
                params.put("id_buku", book.getId());

                return params;
            }
        };

        utilMessage.showProgressBar("Submiting Data");
        Volley.newRequestQueue(EditBookActivity.this).add(request);

    }
}
