package com.github.nmyphp.mytomcat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Http请求的封装
 * <p>
 * 实现了一部分ServletRequest的方法
 * </p>
 *
 * @author nmyphp
 */
@Getter
@Slf4j
public class HttpRequest {

    private String uri;
    private HttpMethod method;
    private String charsetName;
    private ContentType contentType;
    private Map<String, String> parameters = new HashMap<>(10);

    /**
     * 解析Http请求的内容
     *
     * @param inputStream 输入流
     */
    public HttpRequest(InputStream inputStream) throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String httpHead = br.readLine();
        if (null != httpHead && httpHead.trim().length() > 0) {
            String[] heads = httpHead.split("\\s");
            this.method = HttpMethod.match(heads[0]);
            if (heads.length > 0) {
                uri = heads[1];
            }
            while ((line = br.readLine()).length() > 0) {
                String[] entry = line.split(":\\s*");
                String value = null;
                if (entry.length > 0) {
                    value = entry[1];
                }
                parameters.put(entry[0], value);
            }
            charsetName = parameters.getOrDefault("Accept-Charset", "UTF-8");
            contentType = ContentType.match(parameters.getOrDefault("Accept", "text/html"));
            log.debug("Receive a http request:\n\tMethod:{}\n\tURI:{}", method, uri);
        }
    }
}
