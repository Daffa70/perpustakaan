package com.uas.perpustakaan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uas.perpustakaan.R;
import com.uas.perpustakaan.model.Book;
import com.uas.perpustakaan.model.Booking;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context context;
    private ArrayList<Booking> data;
    private BookingAdapterListener bookingAdapterListener;


    public interface BookingAdapterListener{
        void onItemClickListener(Booking booking);
    }

    public BookingAdapter(Context context, ArrayList<Booking> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<Booking> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(BookingAdapterListener adapterListener) {
        this.bookingAdapterListener = adapterListener;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_booking_item, parent, false);

        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BookingViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvTitle, tvAuthor, tvCreatedAt;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvCreatedAt = itemView.findViewById(R.id.tv_created_at);
        }

        public void bind(final Booking booking) {
            tvTitle.setText(booking.getJudulBuku());
            tvAuthor.setText(booking.getNamaAnggota());
            tvCreatedAt.setText(booking.getTanggal_pinjam());

            Glide.with(context)
                    .load(booking.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivImage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookingAdapterListener.onItemClickListener(booking);
                }
            });
        }
    }
}