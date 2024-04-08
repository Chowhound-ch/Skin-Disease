package edu.hfut.innovate.third.wiki;

import edu.hfut.innovate.common.jackson.JacksonUtil;
import edu.hfut.innovate.common.util.CollectionUtil;
import edu.hfut.innovate.third.info.entity.DiseaseInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

/**
 * @author : Chowhound
 * @since : 2024/4/7 - 13:37
 */
@Slf4j
public class DiseaseInfoStorage {
    public static Map<String, DiseaseInfo> INFO_MAP_EN;
    public static Map<String, DiseaseInfo> INFO_MAP_CN;

    public static void reload() {
        try (InputStream stream = DiseaseInfoStorage.class.getClassLoader().getResourceAsStream("disease.json")) {
            assert stream != null;
            reload(stream, false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void reload(InputStream stream, boolean rewrite) {
        byte[] bytes;
        try {
            bytes = stream.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (rewrite) {
            URL resource = DiseaseInfoStorage.class.getClassLoader().getResource("disease.json");
            if (resource == null) {
                throw new RuntimeException("找不到disease.json文件");
            }
            try {
                Path path = Paths.get(resource.toURI());
                Files.write(path, bytes);
            } catch (URISyntaxException | IOException e) {
                throw new RuntimeException(e);
            }
        }

        List<DiseaseInfo> infos = JacksonUtil.readListValue(new String(bytes), DiseaseInfo.class);

        INFO_MAP_CN = CollectionUtil.getMap(infos, DiseaseInfo::getName);
        INFO_MAP_EN = CollectionUtil.getMap(infos, DiseaseInfo::getNameEn);
        log.info("皮肤病信息初始化成功");
    }

}
