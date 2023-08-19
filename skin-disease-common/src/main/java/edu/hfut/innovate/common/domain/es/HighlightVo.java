package edu.hfut.innovate.common.domain.es;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author : Chowhound
 * @since : 2023/8/19 - 22:45
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class HighlightVo<T> {

    private T content;

    private Map<String, List<String>> highlightFields;

}
