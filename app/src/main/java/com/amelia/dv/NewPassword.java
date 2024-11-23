package com.amelia.dv;

import static android.content.Intent.getIntent;
import static com.amelia.dv.DB_contract.CONNECT.ip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class NewPassword extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.new_password, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        String email = (args != null) ? args.getString("email") : "";
        EditText editTextNewpassword =  view.findViewById(R.id.new_password);
        EditText editTextOTP =  view.findViewById(R.id.otp);
        Button button =  view.findViewById(R.id.btnSubmit);
        ProgressBar progressBar = view.findViewById(R.id.proggres);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);

                RequestQueue queue = Volley.newRequestQueue(requireContext());
                String url ="http://" + ip + "/CRUDVolley/CRUDVolley/new-password.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                progressBar.setVisibility(View.GONE);

                                if (response.equals("success")) {
                                    Toast.makeText(getActivity().getApplicationContext(), "new password set", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(requireContext(), Login.class);
                                    startActivity(intent);
                                    requireActivity().finish();
                                }else
                                    Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        progressBar.setVisibility(View.GONE);

                        error.printStackTrace();
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("email", email);
                        paramV.put("otp", editTextOTP.getText().toString());
                        paramV.put("new-password", editTextNewpassword.getText().toString());

                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

    }
}
