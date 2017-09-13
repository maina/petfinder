package com.honeacademy.petfinder.util;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by jmaina on 8/30/17.
 */

public class JsonConverterFactory extends Converter.Factory {

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new JsonConverter();
    }

    class JsonConverter implements Converter<ResponseBody, JSONObject> {
       // final JsonConverter INSTANCE = new JsonConverter();

        @Override
        public JSONObject convert(ResponseBody responseBody) throws IOException {
            try {
                JSONObject jsonObject=new JSONObject(responseBody.string());
                return jsonObject;
            } catch (Exception e) {
                try {
                    String msg=e.getMessage();
                    Log.e("",msg);
                    throw new Exception("Failed to parse JSON", e);
                } catch (Exception e1) {
                    Log.e("",e1.getMessage());
                }
            }
            return null;
        }
    }
}
