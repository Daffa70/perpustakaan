package com.uas.perpustakaan.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uas.perpustakaan.R;
import com.uas.perpustakaan.helper.SessionManager;
import com.uas.perpustakaan.helper.UtilMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.uas.perpustakaan.helper.GlobalVariable.BASE_URL;

/**

 * create an instance of this fragment.
 */
public class AddBookFragment extends Fragment {
    private EditText edtJudul, edtSinopsis, edtUrlGambar, edtPengarang;
    private Button btnSubmit;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;
    private DatePicker date;
    public AddBookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtJudul = view.findViewById(R.id.edt_buku);
        edtSinopsis = view.findViewById(R.id.edt_sinopsis);
        edtPengarang = view.findViewById(R.id.edt_pengarang);
        edtUrlGambar = view.findViewById(R.id.edt_url_gambar);
        btnSubmit = view.findViewById(R.id.btn_submit);
        date  = view.findViewById(R.id.edt_tanggal);




        utilMessage = new UtilMessage(getActivity());
        sessionManager = new SessionManager(getContext());

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });
    }
    private void submitData() {
        final String judul = edtJudul.getText().toString();
        final String sinopsis = edtSinopsis.getText().toString();
        final String pengarang = edtPengarang.getText().toString();
        final String urlGambar = edtUrlGambar.getText().toString();
        int   day  = date.getDayOfMonth();
        int   month= date.getMonth();
        int   year = date.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        final String formatedDate = sdf.format(calendar.getTime());




        if (judul.trim().isEmpty()){
            Toast.makeText(getContext(), "Judul tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
        else if (sinopsis.trim().isEmpty()){
            Toast.makeText(getContext(), "Isi tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
        else if (urlGambar.trim().isEmpty()){
            Toast.makeText(getContext(), "Url gambar tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
        else if (pengarang.trim().isEmpty()){
            Toast.makeText(getContext(), "Pengarang tidak boleh kosong", Toast.LENGTH_SHORT).show();
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST,
                    BASE_URL + "tambah_buku.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            utilMessage.dismissProgressBar();
                            try {
                                JSONObject jsonRespone = new JSONObject(response);

                                int status = jsonRespone.getInt("status");
                                String message = jsonRespone.getString("message");

                                if (status == 0 ){
                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();

                                    resetInput();
                                }
                                else{
                                    Toast.makeText(getContext(), "Tambar buku gagal " + message, Toast.LENGTH_SHORT).show();
                                }
                            }
                            catch (JSONException e){
                                Toast.makeText(getContext(), "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    utilMessage.dismissProgressBar();
                    Toast.makeText(getContext(), "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
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

                    return params;
                }
            };

            utilMessage.showProgressBar("Submiting Data");
            Volley.newRequestQueue(getContext()).add(request);
        }
    }

    private void resetInput() {
        edtJudul.setText("");
        edtPengarang.setText("");
        edtUrlGambar.setText( "");
        edtSinopsis.setText("");

    }
}
