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
import com.uas.perpustakaan.adapter.BookAdapter;
import com.uas.perpustakaan.helper.UtilMessage;
import com.uas.perpustakaan.model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.uas.perpustakaan.helper.GlobalVariable.BASE_URL;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class BookFragment extends Fragment {
    private RecyclerView rvList;
    private BookAdapter bookAdapter;
    private UtilMessage utilMessage;
    public BookFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        rvList = view.findViewById(R.id.rv_list);

        utilMessage = new UtilMessage(getActivity());
        bookAdapter = new BookAdapter(getContext(), new ArrayList<Book>());
        bookAdapter.setAdapterListener(new BookAdapter.BookAdapterListener() {
            @Override
            public void onItemClickListener(Book book) {
                Intent intentdetail = new Intent(getContext(), BookDetailActivity.class);
                intentdetail.putExtra("data", book);

                startActivity(intentdetail);
            }
        });
        rvList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvList.setAdapter(bookAdapter);

        getData();
    }

    private void getData() {
        utilMessage.showProgressBar("Gettingbook news...");
        StringRequest request = new StringRequest(Request.Method.GET,
                BASE_URL + "ambil_buku.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        utilMessage.dismissProgressBar();

                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonData = jsonResponse.getJSONArray("data");

                            ArrayList<Book> data = new ArrayList<>();
                            for (int index = 0; index < jsonData.length(); index++) {
                                JSONObject item = jsonData.getJSONObject(index);

                                Book book = new Book();
                                book.setId(item.getString("id"));
                                book.setImageUrl(item.getString("foto_buku"));
                                book.setTitle(item.getString("judul"));
                                book.setContent(item.getString("sinopsis_buku"));
                                book.setAuthor(item.getString("nama_pengarang"));
                                book.setCreatedAt(item.getString("created_at"));
                                book.setReleaseBook(item.getString("tanggal_rilisbuku"));
                                data.add(book);
                            }

                            bookAdapter.setData(data);

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
