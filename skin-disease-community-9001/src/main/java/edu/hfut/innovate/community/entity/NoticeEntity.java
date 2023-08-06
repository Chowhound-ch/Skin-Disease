package edu.hfut.innovate.community.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import edu.hfut.innovate.common.util.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@TableName(value ="notice")
@Data
public class NoticeEntity extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long noticeId;

    private String name;

    private String title;

    private Long administratorId;

    private String content;


}