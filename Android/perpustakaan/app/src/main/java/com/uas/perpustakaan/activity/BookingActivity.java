package com.uas.perpustakaan.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.uas.perpustakaan.R;
import com.uas.perpustakaan.helper.SessionManager;
import com.uas.perpustakaan.helper.UtilMessage;
import com.uas.perpustakaan.model.Book;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.uas.perpustakaan.helper.GlobalVariable.BASE_URL;

public class BookingActivity extends AppCompatActivity {
    private Button buttonSubmit;
    private EditText edtId,edtJudul,edtIdAnggota;
    private DatePicker datePinjam;
    private SessionManager sessionManager;
    private Book book;
    private UtilMessage utilMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        edtId = findViewById(R.id.edt_idbuku);
        edtIdAnggota = findViewById(R.id.edt_idanggota);
        edtJudul = findViewById(R.id.edt_judulbuku);
        buttonSubmit = findViewById(R.id.btn_book);
        datePinjam =  findViewById(R.id.date_pinjam);

        book = getIntent().getParcelableExtra("data");
        utilMessage = new UtilMessage(this);
        sessionManager = new SessionManager(this);

        setData(book);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }

    private void setData(Book book) {
        edtId.setText(book.getId());
        edtIdAnggota.setText(sessionManager.getUserId());
        edtJudul.setText(book.getTitle());
    }
    private void submitData(){
        final String id_anggota = edtIdAnggota.getText().toString();
        final String id_buku  = edtId.getText().toString();

        int   day  = datePinjam.getDayOfMonth();
        int   month= datePinjam.getMonth();
        int   year = datePinjam.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String formatedDate = sdf.format(calendar.getTime());

        StringRequest request = new StringRequest(Request.Method.POST,
                BASE_URL + "booking.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            int status = jsonResponse.getInt("status");
                            String message = jsonResponse.getString("message");

                            Toast.makeText(BookingActivity.this, message, Toast.LENGTH_SHORT).show();

                            if (status == 0) {
                                finish();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(BookingActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                utilMessage.dismissProgressBar();
                Toast.makeText(BookingActivity.this, "Error"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("id_buku", id_buku);
                params.put("id_anggota", id_anggota);
                params.put("tanggal_pinjam", formatedDate);

                return params;
            }
        };
        utilMessage.showProgressBar("Submiting Data");
        Volley.newRequestQueue(BookingActivity.this).add(request);
    }
}
