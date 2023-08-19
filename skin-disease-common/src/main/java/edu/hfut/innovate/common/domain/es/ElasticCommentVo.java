package edu.hfut.innovate.common.domain.es;

import edu.hfut.innovate.common.domain.vo.community.ReplyVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author : Chowhound
 * @since : 2023/7/23 - 13:26
 */
@ApiModel("评论")
@Data
public class ElasticCommentVo implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @Field(type = FieldType.Long, index = false)
    private Long commentId;
    /**
     *
     */
    @Field(type = FieldType.Object)
    private ElasticUserVo user;
    /**
     *
     */
    @Field(type = FieldType.Keyword, index = false)
    private Long topicId;
    /**
     *
     */
    @Field(type = FieldType.Integer, index = false)
    private Integer likes;
    /**
     *
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    @Field(excludeFromSource = true)
    private Integer isLiked;

    /**
     * 性能问题，暂不对评论的具体回复纳入模糊查询考虑范围
     */
    @Field(excludeFromSource = true)
    private List<ReplyVo> replies;

    @Field(excludeFromSource = true)
    private List<ReplyVo> repliesByLikes;

    /**
     *
     */
    @Field(type = FieldType.Date,index = false)
    private Date updateTime;
    /**
     *
     */
    @Field(type = FieldType.Date,index = false)
    private Date createTime;
}
