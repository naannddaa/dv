package com.amelia.dv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amelia.dv.FormSurat.FormAktaKelahiran;
import com.amelia.dv.FormSurat.FormAktaKematian;
import com.amelia.dv.FormSurat.FormAktaPerkawinan;
import com.amelia.dv.FormSurat.FormAktaKematian;
import com.amelia.dv.FormSurat.FormKartuKeluarga;
import com.amelia.dv.FormSurat.FormMembuatKtp;
import com.amelia.dv.FormSurat.FormSktm;
import com.amelia.dv.FormSurat.FormSuratPernyataanMiskin;
import com.amelia.dv.FormSurat.formPindahPenduduk;
import com.amelia.dv.adapter.AdapterSubPengajuanSurat;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SuratFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterSubPengajuanSurat subPengajuanAdapter;
    private BottomNavigationView bottomNavigationView;
    private TextView tnamasurat;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // berdasarkan fragmentsurat cuyy
        View view = inflater.inflate(R.layout.fragment_surat, container, false);

        tnamasurat = view.findViewById(R.id.text_view);

        // nanti data tampil ini cuyy
        recyclerView = view.findViewById(R.id.recycler_view);
        ArrayList<SubPengajuan> subPengajuanArrayList = new ArrayList<>();
        subPengajuanArrayList.add(new SubPengajuan("Surat Pindah Penduduk"));
        subPengajuanArrayList.add(new SubPengajuan("Surat Keterangan Tidak Mampu"));
        subPengajuanArrayList.add(new SubPengajuan("Surat Pernyataan Miskin"));
        subPengajuanArrayList.add(new SubPengajuan("Kartu Keluarga"));
        subPengajuanArrayList.add(new SubPengajuan("Akta Kematian"));
        subPengajuanArrayList.add(new SubPengajuan("Akta Perkawinan"));
        subPengajuanArrayList.add(new SubPengajuan("Akta Kelahiran"));
        subPengajuanArrayList.add(new SubPengajuan("PEMBUATAN KTP"));



        AdapterSubPengajuanSurat.OnClickListener clickListener = new AdapterSubPengajuanSurat.OnClickListener() {
            @Override
            public void onClickListener(int position) {
                Fragment selectedFragment = null;

                switch (position) {
                    case 0: // Surat Pindah Penduduk
                        selectedFragment = new formPindahPenduduk();
                        break;
                    case 1: // Surat Keterangan Tidak Mampu
                        selectedFragment = new FormSktm();
                        break;
                    case 2: // Surat Pernyataan Miskin
                        selectedFragment = new FormSuratPernyataanMiskin();
                        break;
                    case 3: // Kartu Keluarga
                        selectedFragment = new FormKartuKeluarga();
                        break;
                    case 4: // Akta Kematian
                        selectedFragment = new FormAktaKematian();
                        break;
                    case 5: // Akta Perkawinan
                        selectedFragment = new FormAktaPerkawinan();
                        break;
                    case 6: // Akta Kelahiran
                        selectedFragment = new FormAktaKelahiran();
                        break;
                    case 7: // Pembuatan KTP
                        selectedFragment = new FormMembuatKtp();
                        break;
                    default:
                        break;
                }

                if (selectedFragment != null) {
                    // Mulai transaksi fragment
                    getParentFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selectedFragment) // Ganti fragment_container dengan ID yang sesuai
                            .addToBackStack(null) // Opsional: jika ingin bisa kembali ke fragment sebelumnya
                            .commit();
                }
            }
        };

        // Set up Adapter and RecyclerView
        subPengajuanAdapter = new AdapterSubPengajuanSurat(getActivity(), subPengajuanArrayList, clickListener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(subPengajuanAdapter);

        // Initialize BottomNavigationView
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            // Handle bottom navigation item clicks here
            return true; // Return true to indicate the item was handled
        });

        return view; // Return the view for the fragment
    }
}