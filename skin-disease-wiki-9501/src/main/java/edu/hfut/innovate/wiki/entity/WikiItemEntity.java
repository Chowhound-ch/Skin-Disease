package edu.hfut.innovate.wiki.entity;

import lombok.Data;

/**
 * @author : Chowhound
 * @since : 2023/9/9 - 15:22
 */

@Data
public class WikiItemEntity {

    // 疾病名称
    private String name;


    // 病因
    private String etiology;

    // 描述
    private String description;

    // 临床表现
    private String clinical;

    // 诊断
    private String diagnosis;

    // 治疗
    private String treatment;

    // 预防
    private String prevention;
}
