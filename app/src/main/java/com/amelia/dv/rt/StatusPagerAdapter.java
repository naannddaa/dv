package com.amelia.dv.rt;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import status.PengajuanSuratFragment;
import status.SuratDitolakFragment;
import status.SuratSelesaiFragment;

public class StatusPagerAdapter extends FragmentStateAdapter {

    public StatusPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new PengajuanSuratFragment();
        } else if (position == 1) {
            return new SuratSelesaiFragment();
        } else if (position == 2) {
            return new SuratDitolakFragment();
        } else {
            return new PengajuanSuratFragment(); // Default jika posisi tidak valid
        }
    }

    @Override
    public int getItemCount() {
        return 3; // Jumlah Tab
    }
}
