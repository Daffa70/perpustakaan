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

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private ArrayList<Book> data;
    private BookAdapterListener bookAdapterListerner;


    public interface BookAdapterListener{
        void onItemClickListener(Book book);
    }

    public BookAdapter(Context context, ArrayList<Book> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(ArrayList<Book> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void setAdapterListener(BookAdapterListener adapterListener) {
        this.bookAdapterListerner = adapterListener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_book_item, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivImage;
        private TextView tvTitle, tvAuthor, tvCreatedAt;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.iv_image);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvAuthor = itemView.findViewById(R.id.tv_author);
            tvCreatedAt = itemView.findViewById(R.id.tv_created_at);
        }

        public void bind(final Book book) {
            tvTitle.setText(book.getTitle());
            tvAuthor.setText(book.getAuthor());
            tvCreatedAt.setText(book.getCreatedAt());

            Glide.with(context)
                    .load(book.getImageUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .into(ivImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bookAdapterListerner.onItemClickListener(book);
                }
            });
        }
    }
}