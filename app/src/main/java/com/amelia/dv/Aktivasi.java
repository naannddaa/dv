package com.amelia.dv;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Aktivasi extends Fragment {

    private EditText etEmail, etPassword, etConfirmPassword, inputNik;
    private Button btnRegister;
    private TextView textGoLogin; // Deklarasi TextView untuk navigasi ke halaman login
    private ImageView iconShowHidePassword; // Deklarasi ImageView untuk ikon show/hide password
    private boolean isPasswordVisible = false; // Status visibilitas password
    private FirebaseAuth auth; // Deklarasi FirebaseAuth

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Menginflasi layout untuk fragment
        View view = inflater.inflate(R.layout.aktivasi, container, false);

        // Mengikat elemen UI ke variabel
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
        btnRegister = view.findViewById(R.id.btnRegister);
        inputNik = view.findViewById(R.id.input_nik);
        textGoLogin = view.findViewById(R.id.text_go_login);
        iconShowHidePassword = view.findViewById(R.id.icon_show_hide_password);

        auth = FirebaseAuth.getInstance();

        // Mengatur listener untuk ikon show/hide password
        iconShowHidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePasswordVisibility(); // Memanggil metode untuk mengubah visibilitas password
            }
        });

        // Mengatur listener untuk tombol aktivasi
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleActivation(); // Memanggil metode untuk menangani aktivasi
            }
        });

        // Mengatur listener untuk navigasi ke halaman login
        textGoLogin.setOnClickListener(v -> {
            // Replace current fragment with RegisterFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Aktivasi()) // Use the appropriate container ID
                    .addToBackStack(null) // Optional: adds the transaction to the back stack
                    .commit();
        });

        return view; // Mengembalikan view untuk fragment
    }

    // Metode untuk mengubah visibilitas password
    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Menyembunyikan password (menampilkan titik)
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            iconShowHidePassword.setImageResource(R.drawable.baseline_visibility_24); // Mengganti ikon menjadi sembunyikan
        } else {
            // Menampilkan password (teks biasa)
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            iconShowHidePassword.setImageResource(R.drawable.baseline_visibility_off_24); // Mengganti ikon menjadi tampilkan
        }
        isPasswordVisible = !isPasswordVisible; // Mengubah status visibilitas
        etPassword.setSelection(etPassword.getText().length()); // Mengatur kursor ke akhir teks
    }

    // Metode untuk menangani logika aktivasi
    private void handleActivation() {
        String nik = inputNik.getText().toString().trim(); // Mengambil NIK dari input
        String email = etEmail.getText().toString().trim(); // Mengambil email dari input
        String password = etPassword.getText().toString().trim(); // Mengambil password dari input
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        // Validasi input
        if (nik.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(getActivity(), "Semua kolom harus diisi!", Toast.LENGTH_SHORT).show();
            return; // Menghentikan eksekusi jika ada kolom yang kosong
        }

        if (nik.length() != 16) {
            Toast.makeText(getActivity(), "NIK harus terdiri dari 16 digit!", Toast.LENGTH_SHORT).show();
            return; // Menghentikan eksekusi
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getActivity(), "Email tidak valid!", Toast.LENGTH_SHORT).show();
            return; // Menghentikan eksekusi
        }

        if (password.length() < 8) {
            Toast.makeText(getActivity(), "Password harus terdiri dari minimal 8 karakter!", Toast.LENGTH_SHORT).show();
            return; // Menghentikan eksekusi
        }

        if (!confirmPassword.equals(password)) {
            etConfirmPassword.setError("Password tidak sama");
            return;
        }

        // Registrasi dengan Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Mengirimkan email verifikasi
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getActivity(), "Daftar berhasil, silakan cek email anda", Toast.LENGTH_SHORT).show();
                                        navigateToLogin(); // Navigasi ke login setelah pendaftaran berhasil
                                    } else {
                                        Toast.makeText(getActivity(), "Verifikasi gagal dikirim", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            // Menangani kesalahan Firebase
                            Toast.makeText(getActivity(), "Registrasi gagal: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Metode untuk berpindah ke halaman login
    private void navigateToLogin() {
        // Membuat instance dari fragment login
        Login loginFragment = new Login();

        // Mengganti fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, loginFragment); // Pastikan R.id.container cocok dengan layout Anda
        transaction.addToBackStack(null); // Menambahkan ke back stack jika perlu
        transaction.commit(); // Melakukan commit transaksi
    }
}

