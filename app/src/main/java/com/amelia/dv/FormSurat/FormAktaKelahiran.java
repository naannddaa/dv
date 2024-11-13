package com.amelia.dv.FormSurat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amelia.dv.R;

public class FormAktaKelahiran extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private static final int PICK_IMAGE_REQUEST = 1;
    private TextView tupkk;
    private ImageButton ibupkk;
    private Button ajukan;

    public FormAktaKelahiran() {

    }

    public static FormAktaKelahiran newInstance(String param1, String param2) {
        FormAktaKelahiran fragment = new FormAktaKelahiran();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_akta_kelahiran, container, false);
    //inisiasi
    tupkk = view.findViewById(R.id.t_upkk);
    ibupkk = view.findViewById(R.id.b_upkk);
    ajukan = view.findViewById(R.id.b_ajukan);


        return view;
    }}
