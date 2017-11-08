package com.atoz.akkaratanapat.findpharmacy;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Altear on 10/8/2016.
 */

public class APIHandle {

    private APIHandle.ApiHandlerListener apiHandlerListener;
    private OkHttpClient okHttpClient;

    public enum APIName {
        Nearby,
        Detail

    }

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";
    private static final String API_PLACE_KEY = "AIzaSyB0xNxIUHp_g96xbFgltA9PrfqupqiEurU";


    public APIHandle(Context context) {
        okHttpClient = new OkHttpClient();
    }

    public String getBaseURL() {
        return BASE_URL;
    }

    public void setApiHandlerListener(APIHandle.ApiHandlerListener listener) {
        this.apiHandlerListener = listener;
    }

//    public void setDataPreferences(DataPreferences dataPreferences) {
//        this.dataPreferences = dataPreferences;
//    }

    public void requestNearbyPlace(double lat, double lng, int rad) {
        JSONObject params = new JSONObject();
        String url = BASE_URL + "nearbysearch/json?location=" + lat +
                "," + lng + "&radius=" + rad + "&types=pharmacy&keyword=cruise&language=th&key=" + API_PLACE_KEY;
        try {

            postRequest(APIName.Nearby.toString(), url, params.toString(), false);
        } catch (Exception e) {
            if (apiHandlerListener != null) {
                apiHandlerListener.onFailure(APIName.Nearby.toString(), url, params.toString(), e);
            }
        }
    }

    public void requestPlaceDetail(int index, String id) {
        JSONObject params = new JSONObject();
        String url = BASE_URL + "details/json?placeid=" + id + "&language=th&key=" + API_PLACE_KEY;
        try {

            postRequest(index,APIName.Detail.toString(), url, params.toString(), false);
        } catch (Exception e) {
            if (apiHandlerListener != null) {
                apiHandlerListener.onFailure(APIName.Detail.toString(), url, params.toString(), e);
            }
        }
    }

    private void postRequest(final String name, final String url, final String params, final boolean showLoading) {
        postRequest(0,name,url,params,showLoading);
    }

    private void postRequest(final int index, final String name, final String url, final String params, final boolean showLoading) {
        final RequestBody body = RequestBody.create(JSON, params);
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        if (showLoading) {
            apiHandlerListener.onStartLoading();
        }

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody responseBody = response.body();
                if (response.isSuccessful()) {

                    if (apiHandlerListener != null) {
                        if (showLoading) {
                            apiHandlerListener.onFinishLoading();
                        }
                        try {
                            Log.d("request" + name, "success");
                            apiHandlerListener.onSuccess(name,index, new JSONObject(responseBody.string()));
                        } catch (JSONException e) {
                            Log.d("request" + name, "fail" + e.toString());
                            apiHandlerListener.onFailure(name, url, params, e);
                        }
                    } else {
                        Log.d("listener", "null");
                    }
                } else {
                    if (showLoading) {
                        apiHandlerListener.onFinishLoading();
                    }
                    if (responseBody != null) {
                        apiHandlerListener.onBodyError(responseBody);
                    } else {
                        apiHandlerListener.onBodyErrorIsNull();
                    }
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (showLoading) {
                    apiHandlerListener.onFinishLoading();
                }

                if (apiHandlerListener != null) {
                    apiHandlerListener.onFailure(name, url, params, e);
                }
            }
        });
    }

//    private void getRequest(final APIHandle.APIName name, String url, final Dialog loadingDialog) {
//        final Request request = new Request.Builder()
//                .url(url)
//                .build();
//
//        if (loadingDialog != null)
//            loadingDialog.show();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                if (loadingDialog != null)
//                    loadingDialog.dismiss();
//
//                if (listener != null) {
//                    listener.onFailure(name, e);
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                if (listener != null) {
//                    try {
//                        listener.onSuccess(name, new JSONObject(response.body().string()));
//                    } catch (JSONException e) {
//                        listener.onFailure(name, e);
//                    }
//                }
//
//                if (loadingDialog != null)
//                    loadingDialog.dismiss();
//            }
//        });
//    }

    public interface ApiHandlerListener {

        void onSuccess(String name,int index, JSONObject json) throws JSONException;

        void onBodyError(ResponseBody responseBodyError);

        void onBodyErrorIsNull();

        void onFailure(String name, String url, String param, Exception e);

        void onStartLoading();

        void onFinishLoading();
    }
}
