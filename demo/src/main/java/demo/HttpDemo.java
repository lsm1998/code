package demo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HttpDemo
{
    private static final String HOST = "xxx";

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, InterruptedException
    {
        String url = String.format("https://%s/xxx", HOST);
        String accessId = "xxx";
        String secretKey = "xxx";

        String contentType = "application/x-www-form-urlencoded";

        String param = "phone=17774582028&thr_order_id=1111111111jingpinke2";
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        String md5String = bytesToHexString(md5.digest(param.getBytes()));

        MessageDigest sha1 = MessageDigest.getInstance("SHA");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        String postDate = sdf.format(new Date());
        String encryptString = bytesToHexString(sha1.digest((secretKey + md5String + contentType + postDate).getBytes()));

        HttpClient client = HttpClient.newHttpClient();
        // 建立一个请求对象，指定uri和请求类型（默认为GET）
        var request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .setHeader("Date", postDate)
                .setHeader("Content-MD5", md5String)
                .setHeader("Content-Type", contentType)
                .setHeader("Authorization", String.format("%s:%s", accessId, encryptString))
                .POST(HttpRequest.BodyPublishers.ofString(param))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println(response.body());
    }

    public static String bytesToHexString(byte[] src)
    {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0)
        {
            return null;
        }
        for (byte b : src)
        {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2)
            {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
}
