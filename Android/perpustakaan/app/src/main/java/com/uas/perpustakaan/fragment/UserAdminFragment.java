package com.uas.perpustakaan.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.uas.perpustakaan.R;
import com.uas.perpustakaan.activity.LoginAdminActivity;
import com.uas.perpustakaan.helper.SessionManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAdminFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private TextView tv_user;
    private Button btnLogout, btnfeedback;
    private SessionManager sessionManager;


    public UserAdminFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tv_user = view.findViewById(R.id.tv_user_id);
        btnLogout = view.findViewById(R.id.btn_logout);


        sessionManager  = new SessionManager(getContext());



        tv_user.setText("User id : " + sessionManager.getUserIdAdmin());
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Konfirmasi");
                builder.setMessage("Apakah anda yakin logout? ");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sessionManager.clearEditorAdmin();
                        Intent intentLogin = new Intent(getContext(), LoginAdminActivity.class);
                        startActivity(intentLogin);
                        getActivity().finish();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();

                    }
                });
                builder.show();
            }
        });
    }
}
