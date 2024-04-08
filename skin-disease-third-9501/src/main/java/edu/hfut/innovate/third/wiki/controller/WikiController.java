package edu.hfut.innovate.third.wiki.controller;

import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.third.info.entity.DiseaseInfo;
import edu.hfut.innovate.third.wiki.DiseaseInfoStorage;
import edu.hfut.innovate.third.wiki.entity.WikiItemEntity;
import edu.hfut.innovate.third.wiki.jsoup.WikiSearch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : Chowhound
 * @since : 2023/9/9 - 15:19
 */
@RequestMapping("wiki")
@RestController
public class WikiController {

    public WikiController() {
        DiseaseInfoStorage.reload();
    }

    @GetMapping("/en/{keyword}")
    public R getDiseaseInfoEn(@PathVariable String keyword) {
        DiseaseInfo info = DiseaseInfoStorage.INFO_MAP_EN.get(keyword);
        if (info == null) {
            return R.error(404, "找不到该皮肤病(EN)，请确认该关键词是否属于皮肤病或关键词准确性");
        }

        return R.ok(info);
    }

    @GetMapping("/cn/{keyword}")
    public R getDiseaseInfoCN(@PathVariable String keyword) {
        DiseaseInfo info = DiseaseInfoStorage.INFO_MAP_CN.get(keyword);
        if (info == null) {
            return R.error(404, "找不到该皮肤病(CN)，请确认该关键词是否属于皮肤病或关键词准确性");
        }
        return R.ok(info);
    }

    @PostMapping("/reload")
    public R reload(MultipartFile file) throws IOException {
        DiseaseInfoStorage.reload(file.getInputStream(), true);

        return R.ok(DiseaseInfoStorage.INFO_MAP_CN);
    }


    @GetMapping("/search/{keyword}")
    public R getWikiItem(@PathVariable String keyword) {
        WikiItemEntity wikiItem = WikiSearch.getWikiItem(keyword);

        return wikiItem == null ? R.error(404, "找不到该皮肤病，请确认该关键词是否属于皮肤病或关键词准确性") : R.ok(wikiItem);
    }

}
