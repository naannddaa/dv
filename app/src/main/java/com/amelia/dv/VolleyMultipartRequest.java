//package com.amelia.dv.network;
//
//import com.android.volley.AuthFailureError;
//import com.android.volley.NetworkResponse;
//import com.android.volley.Request;
//import com.android.volley.Response;
//import com.android.volley.toolbox.HttpHeaderParser;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Map;
//public class VolleyMultipartRequest extends Request<NetworkResponse> {
//
//    private final Response.Listener<NetworkResponse> mListener;
//    private final Map<String, DataPart> mByteData;
//
//    public VolleyMultipartRequest(int method, String url, Map<String, DataPart> byteData,
//                                  Response.Listener<NetworkResponse> listener, Response.ErrorListener errorListener) {
//        super(method, url, errorListener);
//        this.mListener = listener;
//        this.mByteData = byteData;
//    }
//
//    @Override
//    protected Map<String, DataPart> getByteData() {
//        return mByteData;
//    }
//
//    @Override
//    protected void deliverResponse(NetworkResponse response) {
//        mListener.onResponse(response);
//    }
//
//    @Override
//    protected Response<NetworkResponse> parseNetworkResponse(NetworkResponse response) {
//        return Response.success(response, HttpHeaderParser.parseCacheHeaders(response));
//    }
//
//    public static class DataPart {
//        private final String fileName;
//        private final byte[] content;
//        private final String type;
//
//        public DataPart(String fileName, byte[] content, String type) {
//            this.fileName = fileName;
//            this.content = content;
//            this.type = type;
//        }
//
//        public String getFileName() {
//            return fileName;
//        }
//
//        public byte[] getContent() {
//            return content;
//        }
//
//        public String getType() {
//            return type;
//        }
//    }
//}
