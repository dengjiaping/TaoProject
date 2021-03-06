package com.crazy.taolove.net.request;

import com.crazy.taolove.CSApplication;
import com.crazy.taolove.R;
import com.crazy.taolove.manager.AppManager;
import com.crazy.taolove.net.base.ResultPostExecute;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * @author Cloudsoar(wangyb)
 * @datetime 2016-05-03 17:28 GMT+8
 * @email 395044952@qq.com
 */
public class ModifyPwdRequest extends ResultPostExecute<String> {
    public void request(final String newPassword){
        Call<ResponseBody> call = AppManager.getUserService().modifyPwd(AppManager.getClientUser().sessionId, newPassword);
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

    private void parseJson(String json) {
        try {
            JsonObject obj = new JsonParser().parse(json).getAsJsonObject();
            int code = obj.get("code").getAsInt();
            if (code == 1) {
                onErrorExecute(CSApplication.getInstance().getString(R.string.modify_faiure));
                return;
            } else if(code == 0){
                onPostExecute(CSApplication.getInstance().getString(R.string.modify_success));
            }
        } catch (Exception e) {
            onErrorExecute(CSApplication.getInstance().getString(R.string.modify_faiure));
        }
    }
}
