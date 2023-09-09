package edu.hfut.innovate.wiki.jsoup;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import edu.hfut.innovate.wiki.entity.WikiItemEntity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author : Chowhound
 * @since : 2023/9/9 - 15:41
 */
public class WikiSearch {

    public static final String WIKI_URL = "https://baike.baidu.com/item/";
    public static final String WIKI_BASIC_URL = "https://baike.baidu.com";

    public static final String DES_TYPE_NAME = "皮肤科";

    public static final Map<String, String> FIELD_MAP = Map.of(
            "病因", "etiology",
            "临床表现", "clinical",
            "诊断", "diagnosis",
            "治疗", "treatment",
            "预防", "prevention");

    public static final String IMAGE_FORMAT = """
            <image class="back-pre" src="%s"></image>""";



    public static WikiItemEntity getWikiItem(String name) {
        String encodedUrl = URLUtil.encode(WIKI_URL + name);

        try {
            Document desDoc = null;
            Document document = Jsoup.connect(encodedUrl).get();
            String url = document.location();
            if (!StrUtil.isNumeric(url.substring(url.lastIndexOf('/') + 1))){// 多义
                Elements els = document.getElementsByClass("para");
                for (Element element : els) {
                    String desUrl = element.getElementsByTag("a").attr("href");
                    Document docItem = Jsoup.connect(WIKI_BASIC_URL + desUrl).get();
                    if (checkSkinDisease(docItem)){
                        desDoc = docItem;
                    }
                }
            } else if (checkSkinDisease(document)){
                desDoc = document;
            }

            if (desDoc != null){
                WikiItemEntity wikiItem = analysisWikiItem(desDoc);
                wikiItem.setName(name);
                Element descriptionEl = desDoc.head().getElementsByAttributeValue("name", "description").first();
                if (descriptionEl != null){
                    wikiItem.setDescription(descriptionEl.attr("content"));
                }

                return wikiItem;
            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return null;
    }
    private static WikiItemEntity analysisWikiItem(Document doc) {
        WikiItemEntity wikiItem = new WikiItemEntity();
        String title = doc.title();
        wikiItem.setName(title.substring(title.indexOf("（")));

        Element descriptionEl = doc.head().getElementsByAttributeValue("name", "description").first();
        if (descriptionEl != null){
            wikiItem.setDescription(descriptionEl.attr("content"));
        }

        /*desDoc.body().getElementsByClass("para-title level-2  J-chapter ").get(1).getElementsByClass("title-text").get(0).childNodes().get(1).toString();*/
        Elements elements = doc.body().getElementsByClass("para-title level-2  J-chapter ");
        Class<? extends WikiItemEntity> clazz = wikiItem.getClass();

        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            Node node = element.getElementsByClass("title-text").get(0).childNodes().get(1);
            if (node instanceof TextNode){

                try {
                    Field field = clazz.getDeclaredField(FIELD_MAP.get(node.toString()));
                    field.setAccessible(true);
                    StringBuilder text = new StringBuilder();
                    Element tempEl = element;

                    do {
                        tempEl = tempEl.nextElementSibling();

                        if (tempEl != null){
                            Elements imgEls = tempEl.getElementsByAttribute("alt");
                            List<String> imgTagList = new ArrayList<>();
                            for (Element imgEl : imgEls) {
                                imgEl.append("%s");
                                imgTagList.add(String.format(IMAGE_FORMAT, imgEl.attr("data-src")));
                            }

                            text.append(String.format(tempEl.text().replaceAll("%[^s]", "%%"), imgTagList)).append("\n\n");
                        } else {
                            break;
                        }

                    } while ((i + 1) != elements.size() && !Objects.equals(tempEl.nextElementSibling(), elements.get(i + 1)));

                    field.set(wikiItem, text.toString());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return wikiItem;
//        Element infoEl = doc.body().getElementsByClass("catalog-list column-1").first();
//        if (infoEl != null){
//            Elements infoEls = infoEl.getElementsByTag("li");
//        }
//
    }

    private static Boolean checkSkinDisease(Document doc) {

        Elements elements = doc.getElementsByClass("expandContainer");

        for (Element element : elements) {
            String itemName = element.getElementsByTag("dd").text().trim();
            if (itemName.equals(DES_TYPE_NAME)) {
                return true;
            }
        }
        return false;
    }

//    public static void main(String[] args) {
//        WikiItemEntity wikiItem = getWikiItem("湿疹");
//        System.out.println(wikiItem);
//    }
}
