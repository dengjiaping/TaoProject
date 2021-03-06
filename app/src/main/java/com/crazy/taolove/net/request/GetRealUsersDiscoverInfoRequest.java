package com.crazy.taolove.net.request;

import android.support.v4.util.ArrayMap;

import com.crazy.taolove.CSApplication;
import com.crazy.taolove.R;
import com.crazy.taolove.entity.PictureModel;
import com.crazy.taolove.manager.AppManager;
import com.crazy.taolove.net.base.ResultPostExecute;
import com.crazy.taolove.utils.AESOperator;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Administrator on 2016/4/30.
 */
public class GetRealUsersDiscoverInfoRequest extends ResultPostExecute<List<PictureModel>> {
    public void request(final int pageNo, final int pageSize){
        ArrayMap<String, String> params = new ArrayMap<>();
        params.put("pageNo", String.valueOf(pageNo));
        params.put("pageSize", String.valueOf(pageSize));
        Call<ResponseBody> call = AppManager.getPictureService().getRealUserDiscoverPics(
                AppManager.getClientUser().sessionId, params);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        parseJson(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        response.body().close();
                    }
                } else {
                    onErrorExecute(CSApplication.getInstance()
                            .getResources()
                            .getString(R.string.network_requests_error));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onErrorExecute(CSApplication.getInstance()
                        .getResources()
                        .getString(R.string.network_requests_error));
            }
        });
    }

    private void parseJson(String json){
        try {
            String decryptData = AESOperator.getInstance().decrypt(json);
            JsonObject obj = new JsonParser().parse(decryptData).getAsJsonObject();
            int code = obj.get("code").getAsInt();
            if (code != 0) {
                onErrorExecute(CSApplication.getInstance().getResources()
                        .getString(R.string.data_load_error));
                return;
            }
            String result = obj.get("data").getAsString();
            Type listType = new TypeToken<ArrayList<PictureModel>>() {
            }.getType();
            Gson gson = new Gson();
            ArrayList<PictureModel> pictureModels = gson.fromJson(result, listType);
            onPostExecute(pictureModels);
        } catch (Exception e) {
            onErrorExecute(CSApplication.getInstance().getResources()
                    .getString(R.string.data_parser_error));
        }
    }
}
