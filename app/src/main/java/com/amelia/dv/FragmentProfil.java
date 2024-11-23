package com.amelia.dv;

import static android.app.Activity.RESULT_OK;
import static com.amelia.dv.DB_contract.CONNECT.ip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FragmentProfil extends Fragment {

    private TextView t_editnotelp, t_notelp, tNik, tNkk, tNama;
    private View popupContainer;
    private Button saveButton, cancelButton, btnlogout, btnupload;
    private EditText editPhoneNumber;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private AppConfig appConfig;
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialize UI elements
        t_editnotelp = view.findViewById(R.id.t_editnotelp);
        t_notelp = view.findViewById(R.id.t_notelp);
        tNkk = view.findViewById(R.id.t_nkk);
        tNik = view.findViewById(R.id.t_nik);
        tNama = view.findViewById(R.id.t_nama);
        popupContainer = view.findViewById(R.id.popupContainer);
        saveButton = view.findViewById(R.id.b_simpan);
        cancelButton = view.findViewById(R.id.b_batal);
        btnlogout = view.findViewById(R.id.btnlogout);
        editPhoneNumber = view.findViewById(R.id.editPhoneNumber);
        btnupload = view.findViewById(R.id.btnupload);
        imageView = view.findViewById(R.id.imageviewadd);

        // Disable text fields initially
        tNkk.setEnabled(false);
        tNik.setEnabled(false);
        tNama.setEnabled(false);
        t_notelp.setEnabled(false);

        // Initialize AppConfig and RequestQueue
        appConfig = new AppConfig(requireContext());
        requestQueue = Volley.newRequestQueue(requireContext());

        // Initialize SharedPreferences and retrieve user_id (NIK)
        sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String nik = sharedPreferences.getString("niklogin", null);

        if (nik != null) {
            tNik.setText(nik);
            fetchProfileData(nik);
        } else {
            Toast.makeText(getActivity(), "NIK not found", Toast.LENGTH_SHORT).show();
        }

        // Edit phone number popup
        t_editnotelp.setOnClickListener(v -> showPopup());

        // Save new phone number
        saveButton.setOnClickListener(v -> {
            String newPhoneNumber = editPhoneNumber.getText().toString();
            if (newPhoneNumber.isEmpty()) {
                Toast.makeText(getActivity(), "No HP harus diisi", Toast.LENGTH_SHORT).show();
            } else {
                updatePhoneNumber(nik, newPhoneNumber);
                t_notelp.setText(newPhoneNumber);
                popupContainer.setVisibility(View.GONE);
            }
        });

        // Handle image upload button click
        btnupload.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        // Cancel phone number edit
        cancelButton.setOnClickListener(v -> popupContainer.setVisibility(View.GONE));

        // Logout button
        btnlogout.setOnClickListener(v -> {
            appConfig.UpdateUserLoginStatus(false);
            startActivity(new Intent(requireActivity(), Login.class));
            requireActivity().finish();
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Use Glide to load the image into the ImageView as a circle
            Glide.with(this)
                    .load(imageUri)
                    .circleCrop()
                    .into(imageView);

            // Upload the image to the server
            try {
                String imageBase64 = encodeImageToBase64(imageUri);
                String nik = tNik.getText().toString(); // Get NIK from the TextView
                uploadImageToServer(nik, imageBase64);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Failed to encode image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updatePhoneNumber(final String nik, final String Email) {
        String url = "http://" + ip + "/CRUDVolley/CRUDVolley/EditNoTelepon.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(getActivity(), "Nomer Telepon Berhasil Diperbarui", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(getActivity(), "Gagal: " + error.toString(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik", nik);
                params.put("email", Email);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void fetchProfileData(String nik) {
        String url = "http://" + ip + "/CRUDVolley/CRUDVolley/GetDataProfil.php?nik=" + nik;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        if (jsonObject.has("error")) {
                            Toast.makeText(getActivity(), jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                        } else {
                            String noKk = jsonObject.optString("no_kk", "");
                            String nikResponse = jsonObject.optString("nik", "");
                            String nama = jsonObject.optString("nama_lengkap", "");
                            String notelp = jsonObject.optString("email", "");
                            String fotoProfil = jsonObject.optString("foto_profil", "");

                            tNkk.setText(noKk);
                            tNik.setText(nikResponse);
                            tNama.setText(nama);
                            t_notelp.setText(notelp);
                            imageView.setImageURI(Uri.parse(fotoProfil));

                            // Menampilkan gambar ke ImageView
                            if (fotoProfil != null && !fotoProfil.isEmpty()) {
                                if (isBase64(fotoProfil)) {
                                    // Jika berupa Base64
                                    byte[] decodedBytes = Base64.decode(fotoProfil, Base64.DEFAULT);
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);

                                    if (bitmap != null) {
                                        Glide.with(requireActivity())
                                                .load(bitmap)
                                                .circleCrop()
                                                .into(imageView);
                                    } else {
                                        imageView.setImageResource(R.drawable.ic_profile); // Fallback
                                    }
                                } else {
                                    // Jika berupa URL
                                    Glide.with(requireActivity())
                                            .load(fotoProfil)
                                            .circleCrop()
                                            .error(R.drawable.ic_profile) // Fallback
                                            .into(imageView);
                                }
                            } else {
                                // Gambar default jika fotoProfil kosong
                                imageView.setImageResource(R.drawable.ic_profile);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Error parsing data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show());

        requestQueue.add(stringRequest);
    }


    private void showPopup() {
        String ambilnotelp = t_notelp.getText().toString();
        editPhoneNumber.setText(ambilnotelp);
        popupContainer.setVisibility(View.VISIBLE);
    }

    private String encodeImageToBase64(Uri imageUri) throws IOException {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void uploadImageToServer(final String nik, final String imageBase64) {
        String url = "http://" + ip + "/CRUDVolley/CRUDVolley/fotoProfile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(getActivity(), "Image upload successful", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(getActivity(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik", nik);
                params.put("foto_profil", imageBase64);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private boolean isBase64(String str) {
        try {
            byte[] decodedBytes = Base64.decode(str, Base64.DEFAULT);
            return decodedBytes.length > 0;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
