package edu.hfut.innovate.common.jackson;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.NullNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;


/**
 * Jackson工具类,其中objectMapper是从容器中获取
 *
 * @author : Chowhound
 */
@Slf4j
@SuppressWarnings("unused")
public class JacksonUtil{

    public static ObjectMapper objectMapper;

    static {

        objectMapper = SpringUtil.getBean(ObjectMapper.class);

    }


    /**
     * 获得elementClasses类型的List对应的 {@link com.fasterxml.jackson.databind.JavaType}
     *
     * @author : Chowhound
     * @since : 2023/7/13 - 11:32
     */
    public static CollectionType getListOf(Class<?> elementClasses) {
        return objectMapper.getTypeFactory().constructCollectionType(List.class, elementClasses);
    }

    public static JavaType getMapOf(Class<?> keyClass, Class<?> valueClass) {
        return objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
    }
    /**
     * 将content转换为json字符串再调用{@code ObjectMapper。readTree(String)}方法
     *
     * @see  com.fasterxml.jackson.databind.ObjectMapper#readTree(String)
     *
     * @author : Chowhound
     * @since : 2023/7/13 - 11:45
     */
    public static JsonNode readTree(Object content){
        try {
            String realContent = toJsonString(content);
            return objectMapper.readTree(realContent);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return NullNode.getInstance();
    }

    /**
     * @see  ObjectMapper#readValue(String, Class)
     *
     * @author : Chowhound
     * @since : 2023/07/13 - 12:57
     */
    public static <T> T readValue(Object content, Class<T> valueType) {

        try {
            String  realContent = JacksonUtil.toJsonString(content);
            return StrUtil.isBlank(realContent)? null : objectMapper.readValue(realContent, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @see #readValue(Object, Class)
     *
     * @author : Chowhound
     * @since : 2023/07/13 - 12:58
     */
    public static <T> T readValue(Object content, JavaType valueType) {
        return readValue(content, valueType, JsonOperatePretreatment.emptyOperate());
    }


    /**
     * 在{@code readValue(Object, JavaType) }的基础上,在转换前对{@code content}内容进行预处理.
     *
     * <p>如下方法即可对{@code content}中"data"下的"list"对应的值按照{@code valueType}进行Json反序列化
     * <p><pre class="code">
     *     JacksonUtil.readValue(content, valueType,
     *          JsonOperatePretreatment.fromPath(JsonPath.of("data", "list")))
     * </pre>
     *
     * @param operate 对{@code content}内容进行预处理的函数式接口
     * @see #readValue(Object, JavaType)
     *
     * @author : Chowhound
     * @since : 2023/07/13 - 13:00
     */
    public static <T> T readValue(Object content, JavaType valueType, JsonOperatePretreatment operate) {

        try {
            String realContent = operate == null ? toJsonString(content) : toJsonString(operate.apply(readTree(content)));
            return StrUtil.isBlank(realContent)? null : objectMapper.readValue(realContent, valueType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 只序列化{@code content}中指定的{@code path}路径下的Json内容.
     *
     * <p>对{@code content}内容根据{@code path}预处理后,再调用{@code readValue(Object, Class)}方法
     *
     * @see #readValue(Object, JavaType, JsonOperatePretreatment)
     * @see #readValue(Object, Class)
     *
     * @author : Chowhound
     * @since : 2023/07/13 - 13:19
     */
    public static <T> T readValue(Object content, JavaType valueType, JsonPath path){
        return  readValue(content, valueType, JsonOperatePretreatment.fromPath(path));
    }

    /**
     * 根据{@code valueType}中类型的List类型的反序列化{@code content}
     * @see #readValue(Object, Class)
     *
     * @author : Chowhound
     * @since : 2023/07/13 - 13:24
     */
    public static <T> List<T> readListValue(Object content, Class<T> valueType) {
        return readValue(content, getListOf(valueType));
    }
    /**
     * 对{@code content}进行预处理之后再根据{@code valueType}中类型的List类型进行反序列化
     *
     * @see #readValue(Object, JavaType, JsonOperatePretreatment)
     *
     * @author : Chowhound
     * @since : 2023/07/13 - 13:25
     */
    public static <T> List<T> readListValue(Object content, Class<T> valueType, JsonOperatePretreatment operate) {
        return readValue(content, getListOf(valueType), operate);
    }

    /**
     * 对{@code content}根据{@code path}进行预处理之后再根据{@code valueType}中类型的List类型进行反序列化
     *
     * @see #readValue(Object, JavaType, JsonPath)
     *
     * @author : Chowhound
     * @since : 2023/07/13 - 13:27
     */
    public static <T> List<T> readListValue(Object content, Class<T> valueType, JsonPath path) {
        return readValue(content, getListOf(valueType), path);
    }


    /**
     * 将{@code object}转换为Json字符串
     * @see ObjectMapper#writeValueAsString(Object)
     *
     * @author : Chowhound
     * @since : 2023/07/13 - 13:28
     */
    public static String toJsonString(Object object) {

        if (object == null) {
            return StrUtil.EMPTY_JSON;
        }

        try {
            return object instanceof String ?(String) object : objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StrUtil.EMPTY_JSON;
    }
}
