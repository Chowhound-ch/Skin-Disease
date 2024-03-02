package edu.hfut.innovate.identify.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.LineHandler;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpDownloader;
import edu.hfut.innovate.identify.entity.IdentifyRes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Chowhound
 * @since : 2024/3/2 - 16:05
 */
@Slf4j
@Component
public class IdentifierHelper {
    @Value("${custom.python-location}")
    public String PYTHON_LOCATION;

    public IdentifyRes doGetIdentify(String imgUrl) {
        String resourcesPath = getResourcesPath();
        byte[] bytes = HttpDownloader.downloadBytes(imgUrl);
        String imgPath = resourcesPath + "Resnet/img.jpg";
        FileUtil.del(imgPath);
        File file = FileUtil.newFile(imgPath);
        try {
            Files.write(file.toPath(), bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Runtime runtime = Runtime.getRuntime();

            Process exec = runtime.exec(new String[]{PYTHON_LOCATION, resourcesPath + "Resnet/predict.py"}
            ,null, new File(resourcesPath + "Resnet"));
            exec.waitFor();

            Map<String, Double> strMap = new HashMap<>();
            IoUtil.readUtf8Lines(exec.getInputStream(), (LineHandler) line -> {
                String[] strings = line.split(":");
                strMap.put(strings[1].substring(1, strings[1].length() - "   prob".length()), Double.parseDouble(strings[2].substring(1)));
            });

            Map.Entry<String, Double> entry = strMap.entrySet().stream().max(Map.Entry.comparingByValue()).get();
            IdentifyRes identifyRes = new IdentifyRes();
            identifyRes.setMost(entry.getKey());
            identifyRes.setProb(entry.getValue());
            return identifyRes;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("识别过程出错" + e.getMessage());
        }
        return null;
    }

    public String getResourcesPath() {
        URL res = this.getClass().getClassLoader().getResource("");
        String decode = URLUtil.decode(res.getPath());

        return decode.contains(":") ? decode.substring(1) : decode;
    }




}
