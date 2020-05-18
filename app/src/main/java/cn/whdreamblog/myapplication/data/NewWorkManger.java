package cn.whdreamblog.myapplication.data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Date;
import java.util.function.Consumer;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import cn.whdreamblog.mockhelper.mock.interceptor.IMockService;
import okhttp3.Call;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author wanghao <a href="blackJuly@outlook.com">Contact me.</a>
 * @version 1.0
 * @since 2020/5/18 21:31
 * desc : The class is used for .....
 */
public class NewWorkManger {
    private OkHttpClient okHttpClient;
    private static NewWorkManger manger;

    public NewWorkManger() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder();
        IMockService mockService = IMockService.defaultMock;
        mockService.getMockInterceptors().forEach(new Consumer<Interceptor>() {
            @Override
            public void accept(Interceptor interceptor) {
                builder.addInterceptor(interceptor);
            }
        });
        okHttpClient = builder.build();
    }

    public static void init(){
        manger = new NewWorkManger();
    }
    public static NewWorkManger get(){
        return manger;
    }
    private String TIANQI_DAILY_WEATHER_URL = "https://api.seniverse.com/v3/weather/daily.json";

    private String TIANQI_API_SECRET_KEY = "ST-8-qfa3D9uRDJZ9"; //

    private String TIANQI_API_USER_ID = "PF87Q0bmpBlIr3PoF"; //

    /**
     * Generate HmacSHA1 signature with given data string and key
     * @param data
     * @param key
     * @return
     * @throws SignatureException
     */
    private String generateSignature(String data, String key) throws SignatureException {
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);
            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));
            result = new sun.misc.BASE64Encoder().encode(rawHmac);
        }
        catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
    public String getDiaryWeatherInfo() throws IOException, SignatureException {
        String url = generateGetDiaryWeatherURL(
                "shanghai",
                "zh-Hans",
                "c",
                "1",
                "1"
        );
        Request request = new Request.Builder().url(url).get().build();
        final Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        System.out.println(response.toString());
       return response.body().string();
    }
    /**
     * Generate the URL to get diary weather
     * @param location
     * @param language
     * @param unit
     * @param start
     * @param days
     * @return
     */
    public String generateGetDiaryWeatherURL(
            String location,
            String language,
            String unit,
            String start,
            String days
    )  throws SignatureException, UnsupportedEncodingException {
        String timestamp = String.valueOf(new Date().getTime());
        String params = "ts=" + timestamp + "&ttl=1800&uid=" + TIANQI_API_USER_ID;
        String signature = URLEncoder.encode(generateSignature(params, TIANQI_API_SECRET_KEY), "UTF-8");
        return TIANQI_DAILY_WEATHER_URL + "?" + params + "&sig=" + signature + "&location=" + location + "&language=" + language + "&unit=" + unit + "&start=" + start + "&days=" + days;
    }


}
