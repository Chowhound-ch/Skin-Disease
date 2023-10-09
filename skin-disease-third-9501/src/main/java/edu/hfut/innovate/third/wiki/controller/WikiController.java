package edu.hfut.innovate.third.wiki.controller;

import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.third.wiki.entity.WikiItemEntity;
import edu.hfut.innovate.third.wiki.jsoup.WikiSearch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Chowhound
 * @since : 2023/9/9 - 15:19
 */
@RequestMapping("wiki")
@RestController
public class WikiController {


    @GetMapping("/search/{keyword}")
    public R getWikiItem(@PathVariable String keyword) {
        WikiItemEntity wikiItem = WikiSearch.getWikiItem(keyword);

        return wikiItem == null ? R.error(404, "找不到该皮肤病，请确认该关键词是否属于皮肤病或关键词准确性") : R.ok(wikiItem);
    }

}
