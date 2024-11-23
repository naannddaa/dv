package com.amelia.dv;

import static android.content.Context.CONNECTIVITY_SERVICE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.amelia.dv.DB_contract.CONNECT;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Login extends Fragment {

    private EditText etx_nik, etx_password;
    private TextView tlupapass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false); // Use the fragment's layout

        // Initialize UI elements
        etx_nik = view.findViewById(R.id.input_nik);
        etx_password = view.findViewById(R.id.input_pass);
        Button btn_lgn = view.findViewById(R.id.btn_login);
        TextView tx_register = view.findViewById(R.id.tv_activate);
        tlupapass = view.findViewById(R.id.t_lupapassword);

        // Login button click event
        btn_lgn.setOnClickListener(v -> {
            String nik = etx_nik.getText().toString().trim();
            String password = etx_password.getText().toString().trim();
            Log.d("LoginData", "nik: " + nik + ", Password: " + password);

            if (!nik.isEmpty() && !password.isEmpty()) {
                if (isNetworkAvailable()) {
                    String url = CONNECT.urlLogin;  // URL for login request
                    RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
                    Log.d("LoginURL", "URL: " + url);

                    // Create POST request
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            response -> {
                                Log.d("LoginResponse", "Response: " + response);
                                if (response.equals("Selamat Datang")) {
                                    Toast.makeText(getActivity(), "Login Berhasil!", Toast.LENGTH_SHORT).show();

                                    // Save NIK to SharedPreferences after successful login
                                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("niklogin", nik);  // Save NIK to SharedPreferences
                                    editor.apply();

                                    // Navigate to MainActivityRt and send NIK
                                    Intent intent = new Intent(getActivity(), MainActivityRt.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra("nik", nik);  // Pass NIK to next activity
                                    startActivity(intent);
                                    getActivity().finish(); // Close current activity
                                } else if (response.equals("Silahkan melakukan aktivasi akun terlebih dahulu")) {
                                    Toast.makeText(getActivity(), "Silahkan melakukan aktivasi akun terlebih dahulu", Toast.LENGTH_SHORT).show();
                                } else if (response.equals("Password salah")) {
                                    Toast.makeText(getActivity(), "Password salah", Toast.LENGTH_SHORT).show();
                                } else if (response.equals("NIK tidak ditemukan")) {
                                    Toast.makeText(getActivity(), "NIK tidak ditemukan", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Login Gagal! " + response, Toast.LENGTH_SHORT).show();
                                }
                            },
                            error -> {
                                Log.e("Volley Error", error.toString());
                                Toast.makeText(getActivity(), "Terjadi kesalahan koneksi: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("nik", nik);  // Pass NIK parameter
                            params.put("password", password);
                            Log.d("LoginParams", "Params: " + params.toString());
                            return params;
                        }
                    };

                    // Set retry policy
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            10000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                    // Add request to request queue
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(getActivity(), "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Username dan Password tidak boleh kosong.", Toast.LENGTH_SHORT).show();
            }
        });

        // Register link click event
        tx_register.setOnClickListener(v -> {
            // Replace current fragment with RegisterFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Aktivasi()) // Use the appropriate container ID
                    .addToBackStack(null) // Optional: adds the transaction to the back stack
                    .commit();
        });

        tlupapass.setOnClickListener(v -> {
            // Replace current fragment with RegisterFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new ForgotPassword()) // Use the appropriate container ID
                    .addToBackStack(null) // Optional: adds the transaction to the back stack
                    .commit();
        });
        return view;
    }

    // Method to retrieve values from SharedPreferences
    public String getSharedPreferenceValue(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);  // Return value with the given key, or null if not found
    }

    // Method to check if device is connected to the internet
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
