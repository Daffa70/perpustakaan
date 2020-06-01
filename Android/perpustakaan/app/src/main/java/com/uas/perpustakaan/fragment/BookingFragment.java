package com.uas.perpustakaan.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.uas.perpustakaan.R;
import com.uas.perpustakaan.activity.BookDetailActivity;
import com.uas.perpustakaan.activity.BookingDetailActivity;
import com.uas.perpustakaan.adapter.BookAdapter;
import com.uas.perpustakaan.adapter.BookingAdapter;
import com.uas.perpustakaan.helper.SessionManager;
import com.uas.perpustakaan.helper.UtilMessage;
import com.uas.perpustakaan.model.Book;
import com.uas.perpustakaan.model.Booking;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.uas.perpustakaan.helper.GlobalVariable.BASE_URL;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {
    private RecyclerView rvList;
    private BookingAdapter bookingAdapter;
    private UtilMessage utilMessage;
    private SessionManager sessionManager;
    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_booking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        rvList = view.findViewById(R.id.rv_list);
        sessionManager = new SessionManager(getContext());
        utilMessage = new UtilMessage(getActivity());
        bookingAdapter = new BookingAdapter(getContext(), new ArrayList<Booking>());
        bookingAdapter.setAdapterListener(new BookingAdapter.BookingAdapterListener() {
            @Override
            public void onItemClickListener(Booking booking) {
                Intent intentdetail = new Intent(getContext(), BookingDetailActivity.class);
                intentdetail.putExtra("data", booking);
                intentdetail.putExtra("is_mine", true);

                startActivity(intentdetail);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(bookingAdapter);

        getData();
    }

    private void getData() {
            utilMessage.showProgressBar("Getting Booking...");
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "ambil_booking.php?id_anggota="+sessionManager.getUserId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<Booking> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                Booking booking = new Booking();
                                booking.setId_Book(item.getString("id_book"));
                                booking.setIdBuku(item.getString("id_buku"));
                                booking.setId_Anggota(item.getString("id_anggota"));
                                booking.setTanggal_pinjam(item.getString("tanggal_pinjam"));
                                booking.setTanggal_proses(item.getString("tanggal_proses"));
                                booking.setJudulBuku(item.getString("judul_buku"));
                                booking.setDenda(item.getString("denda"));
                                booking.setStatus_booking(item.getString("status_booking"));
                                booking.setNamaAnggota(item.getString("nama_anggota"));
                                booking.setImageUrl(item.getString("foto_buku"));
                                data.add(booking);
                            }

                            bookingAdapter.setData(data);

                        } catch (JSONException e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        utilMessage.dismissProgressBar();
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(getContext()).add(request);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_refresh, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_refresh){
            getData();
        }
        return super.onOptionsItemSelected(item);
    }
}
