package com.amelia.dv.FormSurat;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.amelia.dv.R;

public class FormAktaKematian extends Fragment {

//    private static final int PICK_PDF_REQUEST = 1;
//    private static final int STORAGE_PERMISSION_CODE = 123;
//
//    private Uri pdfUri;
//    private Button btnChoosePDF, btnUploadPDF;
//    private TextView tvSelectedPDF;
//    private ProgressBar progressBar;
//
//    public FormAktaKematian() {
//        // Required empty public constructor
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_form_aktakematian, container, false);
//
//        // Initialize views
//        btnChoosePDF = view.findViewById(R.id.btn_choose_pdf);
//        btnUploadPDF = view.findViewById(R.id.btn_upload_pdf);
//        tvSelectedPDF = view.findViewById(R.id.tv_selected_pdf);
//        progressBar = view.findViewById(R.id.progressBar);
//
//        // Check and request storage permission
//        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
//        }
//
//        // Set onClick listeners
//        btnChoosePDF.setOnClickListener(v -> choosePDF());
//        btnUploadPDF.setOnClickListener(v -> {
//            if (pdfUri != null) {
//                uploadPDF();
//            } else {
//                Toast.makeText(getActivity(), "Please choose a PDF first", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return view;
//    }
//
//    // Function to open file chooser for PDF
//    private void choosePDF() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(Intent.createChooser(intent, "Choose PDF"), PICK_PDF_REQUEST);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
//            pdfUri = data.getData();
//            tvSelectedPDF.setText("PDF Selected: " + getFileName(pdfUri));
//        }
//    }
//
//    // Function to get the file name from the URI
//    private String getFileName(Uri uri) {
//        String result = uri.getPath();
//        int cut = result.lastIndexOf('/');
//        if (cut != -1) {
//            result = result.substring(cut + 1);
//        }
//        return result;
//    }
//
//    // Function to upload PDF (implement the upload logic here)
//    private void uploadPDF() {
//        progressBar.setVisibility(View.VISIBLE);
//        // Add your upload logic here (e.g., using Volley or other methods to upload the PDF)
//        progressBar.setVisibility(View.GONE);
//        Toast.makeText(getActivity(), "PDF uploaded successfully", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getActivity(), "Permission granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}


        // Set onClickListener for Choose PDF button
//        btnChoosePDF.setOnClickListener(v -> choosePDF());

        // Set onClickListener for Upload PDF button
//        btnUploadPDF.setOnClickListener(v -> {
//            if (pdfUri != null) {
//                uploadPDF();
//            } else {
//                Toast.makeText(getActivity(), "Please select a PDF file first", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        return view;
//    }

        // Method to open file chooser
//    private void choosePDF() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("application/pdf");
//        intent.addCategory(Intent.CATEGORY_OPENABLE);
//        startActivityForResult(Intent.createChooser(intent, "Select PDF"), PICK_PDF_REQUEST);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_PDF_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
//            pdfUri = data.getData();
//            String fileName = pdfUri.getLastPathSegment();
//            tvSelectedPDF.setText(fileName != null ? fileName : "PDF Selected");
//        }
//    }

        // Method to upload PDF to server
//    private void uploadPDF() {
//        progressBar.setVisibility(View.VISIBLE);
//
//        try {
//            InputStream inputStream = getActivity().getContentResolver().openInputStream(pdfUri);
//            byte[] pdfBytes = new byte[inputStream.available()];
//            inputStream.read(pdfBytes);
//
//            String uploadUrl = "http://" + ip + "/CRUDVolley/insert_sktm.php"; // Ganti dengan URL server Anda
//
//            // Volley request to upload PDF
//            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, uploadUrl,
//                    response -> {
//                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(), "PDF Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                    },
//                    error -> {
//                        progressBar.setVisibility(View.GONE);
//                        Toast.makeText(getActivity(), "Upload Failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }) {
//                @Override
//                protected Map<String, DataPart> getByteData() {
//                    return mByteData;
//                }
//
//            };

        // Add the request to the request queue
//            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//            requestQueue.add(volleyMultipartRequest);
//
//        } catch (IOException e) {
//            progressBar.setVisibility(View.GONE);
//            Toast.makeText(getActivity(), "Failed to read the file", Toast.LENGTH_SHORT).show();
//        }
//    }

        // Handle permission request result
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getActivity(), "Storage Permission Granted", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(getActivity(), "Storage Permission Denied", Toast.LENGTH_SHORT).show();
//            }
//        }

