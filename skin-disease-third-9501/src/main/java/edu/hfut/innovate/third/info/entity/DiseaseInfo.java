package edu.hfut.innovate.third.info.entity;

import lombok.Data;

import java.util.List;

/**
 * @author : Chowhound
 * @since : 2024/4/6 - 20:23
 */
@Data
public class DiseaseInfo {
    private Long id;
    private String name;
    private String nameEn;
    private String alias;
    private List<DiseaseItem> items;
}
