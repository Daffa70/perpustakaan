package com.uas.perpustakaan.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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
import com.uas.perpustakaan.model.Booking;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.uas.perpustakaan.helper.GlobalVariable.BASE_URL;

public class BookingDetailActivity extends AppCompatActivity {
    private TextView tvTitle, tvNama, tvIdbooking, tvDate, tvStatus, tvDenda;
    private boolean isMine, status_booking, status_booking_user;
    private Booking booking;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;
    private ImageView imageView;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);

        imageView = findViewById(R.id.iv_image);
        tvTitle = findViewById(R.id.tv_title);
        tvDate = findViewById(R.id.tv_datestart);
        tvNama = findViewById(R.id.tv_nama);
        tvIdbooking = findViewById(R.id.tv_idbooking);
        tvStatus = findViewById(R.id.tv_status);
        tvDenda = findViewById(R.id.tv_denda);

        sessionManager = new SessionManager(this);
        utilMessage = new UtilMessage(this);
        booking = (Booking) getIntent().getExtras().get("data");
        isMine = getIntent().getBooleanExtra("is_mine", false);
        setData(booking);

    }

    private void setData(Booking booking) {
        if (booking != null){
            if (getSupportActionBar() != null){
                getSupportActionBar().setTitle(booking.getJudulBuku());
            }
            Glide.with(this)
                    .load(booking.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
            tvTitle.setText(booking.getJudulBuku());
            tvDate.setText(booking.getTanggal_pinjam());
            tvNama.setText(booking.getNamaAnggota());
            tvIdbooking.setText(booking.getId_Book());
            tvStatus.setText(booking.getStatus_booking());


            status = booking.getStatus_booking();

            if (status.equals("Pengajuan pengembalian") ){
                status_booking = true;
                status_booking_user = false;
                tvDenda.setText(booking.getDenda());
            }
            else if(status.equals("Sudah Kembali")){
                status_booking = false;
                status_booking_user = false;
                tvDenda.setText("");
            }
            else if(status.equals("dibooking")){
                status_booking = false;
                status_booking_user = true;
                tvDenda.setText(booking.getDenda());
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(isMine == false) {
            if(status_booking == true) {
                getMenuInflater().inflate(R.menu.menu_finish_admin, menu);
            }
        }
        else{
            if(status_booking_user == true)
            getMenuInflater().inflate(R.menu.menu_update_booking, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_dikembalikan:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Konfirmasi");
                    builder.setMessage("Anda yakin mengkonfirmasi pengembalian \"" + booking.getNamaAnggota() + booking.getJudulBuku() + "\"?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            StringRequest request = new StringRequest(Request.Method.POST,
                                    BASE_URL + "update_status.php",
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            utilMessage.dismissProgressBar();
                                            try {
                                                JSONObject jsonResponse = new JSONObject(response);

                                                int status = jsonResponse.getInt("status");
                                                String message = jsonResponse.getString("message");

                                                Toast.makeText(BookingDetailActivity.this, message, Toast.LENGTH_SHORT).show();

                                                if (status == 0) {
                                                    finish();
                                                }
                                            } catch (JSONException e) {
                                                Toast.makeText(BookingDetailActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            utilMessage.dismissProgressBar();
                                            Toast.makeText(BookingDetailActivity.this, "Error" + error.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String, String> params = new HashMap<>();
                                    params.put("id_book", booking.getId_Book());
                                    return params;

                                }
                            };
                            utilMessage.showProgressBar("");
                            Volley.newRequestQueue(BookingDetailActivity.this).add(request);
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
            case R.id.action_pengajuan:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
                builder1.setTitle("Konfirmasi");
                builder1.setMessage("Anda yakin ini mengajuakn pengembalian \"" + booking.getNamaAnggota() + booking.getJudulBuku() +"\"?");
                builder1.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                 StringRequest request = new StringRequest(Request.Method.POST,
                        BASE_URL + "pengajuan_kembali.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                utilMessage.dismissProgressBar();
                                try {
                                    JSONObject jsonRespone = new JSONObject(response);

                                    int status = jsonRespone.getInt("status");
                                    String message = jsonRespone.getString("message");

                                    Toast.makeText(BookingDetailActivity.this, message, Toast.LENGTH_SHORT).show();

                                    if (status == 0 ){
                                        finish();
                                    }
                                }
                                catch (JSONException e){
                                    Toast.makeText(BookingDetailActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utilMessage.dismissProgressBar();
                        Toast.makeText(BookingDetailActivity.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id_booking", booking.getId_Book());
                        params.put("denda", booking.getDenda());

                        return params;
                    }
                };

                utilMessage.showProgressBar("Submiting Data");
                Volley.newRequestQueue(BookingDetailActivity.this).add(request);
                    }
                });
                builder1.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder1.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
