package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.dto.community.CollectionRecordDto;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.vo.community.CollectionRecordVo;
import edu.hfut.innovate.community.entity.CollectionRecord;
import edu.hfut.innovate.community.service.CollectionRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author : Chowhound
 * @since : 2023/7/31 - 14:58
 */
@Api(tags = "收藏相关接口")
@RequestMapping("collection")
@RestController
public class CollectionRecordController {
    @Autowired
    private CollectionRecordService collectionRecordService;

    @Transactional
    @ApiOperation(value = "收藏")
    @PostMapping("/save")
    public R like(
            @ApiParam(value = "收藏记录", required = true)
            @RequestBody CollectionRecordDto collectionRecordDto){

        CollectionRecord collectionRecord = BeanUtil.copyProperties(collectionRecordDto, new CollectionRecord());

        collectionRecordService.saveCollectionRecord(collectionRecord);

        return R.ok();
    }

    @ApiOperation(value = "取消收藏")
    @PostMapping("/delete/{collection_id}")
    public R delete(
            @ApiParam(value = "收藏记录id", required = true)
            @PathVariable("collection_id") Long collectionId){

        collectionRecordService.removeCollectionRecord(collectionId);

        return R.ok();
    }

    @ApiOperation(value = "查询收藏记录")
    @GetMapping("/{user_id}")
    public R get(
            @ApiParam(value = "用户id", required = true)
            @PathVariable("user_id") Long userId){

        List<CollectionRecordVo> collectionRecordVoList =
                collectionRecordService.listTopicCollectedByUserId(userId);

        return R.ok(collectionRecordVoList);
    }

    @ApiOperation("分页查询收藏记录")
    @GetMapping("/page/{user_id}")
    public R list(
            @ApiParam("分页查询参数")
            @RequestParam Map<String, Object> params, @PathVariable("user_id") Long userId) {
        return R.ok(collectionRecordService.queryPageByUserId(params, userId));
    }


}
