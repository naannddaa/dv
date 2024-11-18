package com.amelia.dv;

import static com.amelia.dv.DB_contract.CONNECT.ip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

import com.amelia.dv.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Aktivasi extends Fragment {

    private EditText etNotelp, etPassword, inputNik;
    private Button btnaktivasi;
    private TextView textGoLogin;
    private ImageView iconShowHidePassword;
    private boolean isPasswordVisible = false;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.aktivasi, container, false);

        // Bind UI elements
        etNotelp = view.findViewById(R.id.input_notelp);
        etPassword = view.findViewById(R.id.input_pass);
        inputNik = view.findViewById(R.id.input_nik);
        btnaktivasi = view.findViewById(R.id.btn_aktivasi);
        textGoLogin = view.findViewById(R.id.tv_login);

        // Set listener for the "Aktivasi" button
        btnaktivasi.setOnClickListener(v -> registerUser());

        // Set listener for navigation to login screen
        textGoLogin.setOnClickListener(v -> {
            // Replace current fragment with LoginFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Login())
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    // Function to register user and send data to API
    private void registerUser() {
        // Get input values
        final String nik = inputNik.getText().toString().trim();
        final String noHp = etNotelp.getText().toString().trim();
        final String password = etPassword.getText().toString().trim();

        // Simple validation
        if (nik.isEmpty() || noHp.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Harap isi semua data", Toast.LENGTH_SHORT).show();
            return;
        }

        // API URL
        String url = "http://" + ip + "/CRUDVolley/Aktivasi.php";  // Aktivasi.php endpoint

        // Send request using Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Logging response
                        Log.d("Response", "Server response: " + response);

                        try {
                            // Check if response is empty
                            if (response == null || response.isEmpty()) {
                                Toast.makeText(getActivity(), "Respons kosong dari server", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Parse the JSON response
                            JSONObject jsonObject = new JSONObject(response);
                            boolean error = jsonObject.getBoolean("error");
                            String message = jsonObject.getString("message");

                            // Display the result to the user
                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                            if (!error) {
                                // Navigate to login page only if activation is successful
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.fragment_container, new Login())  // Switch to Login fragment
                                        .addToBackStack(null)  // Add to back stack
                                        .commit();
                            } // If error, just show the message and don't navigate
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), "Error parsing data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Log the error and display a message
                        Log.e("Error", "Volley Error: " + error.getMessage());
                        error.printStackTrace();
                        Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Send the NIK, no_hp, and password to the server
                Map<String, String> params = new HashMap<>();
                params.put("nik", nik);
                params.put("no_hp", noHp);
                params.put("password", password);
                return params;
            }
        };

        // Add request to the Volley queue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
