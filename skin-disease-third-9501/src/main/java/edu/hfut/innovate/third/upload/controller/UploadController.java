package edu.hfut.innovate.third.upload.controller;

import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import edu.hfut.innovate.common.jackson.JacksonUtil;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.third.upload.config.QiniuProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : Chowhound
 * @since : 2023/10/9 - 17:02
 */
@SuppressWarnings("HttpUrlsUsage")
@RequestMapping("upload")
@RestController
public class UploadController {
    @Autowired
    private UploadManager uploadManager;
    @Autowired
    private QiniuProperties properties;

    @PostMapping("form")
    public R upload(@RequestParam MultipartFile[] files){
        Auth auth = Auth.create(properties.accessKey, properties.secretKey);
        StringMap putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        long expireSeconds = 3600;
        String upToken = auth.uploadToken(properties.bucket, null, expireSeconds, putPolicy);
        try {
            List<String> urls = new ArrayList<>();
            for (MultipartFile file : files) {
                Response response = uploadManager.put(file.getBytes(), null, upToken);
                urls.add("http://" + properties.domain  + "/" + JacksonUtil.readTree(response.bodyString()).get("key").asText());
            }
            return R.ok(Map.of("urls", urls));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


