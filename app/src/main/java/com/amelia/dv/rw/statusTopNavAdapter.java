package com.amelia.dv.rw;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import status.PengajuanSuratFragment;
import status.SuratSelesaiFragment;

public class statusTopNavAdapter extends FragmentStateAdapter {
    public statusTopNavAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new PengajuanSuratFragment();
        } else if (position == 1) {
            return new SuratSelesaiFragment();
        }  else {
            return new PengajuanSuratFragment(); // Default jika posisi tidak valid
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Jumlah Tab
    }
}
