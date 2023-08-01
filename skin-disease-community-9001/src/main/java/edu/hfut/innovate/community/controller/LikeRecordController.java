package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.dto.community.LikeRecordDto;
import edu.hfut.innovate.common.renren.PageUtils;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.common.vo.community.LikeRecordVo;
import edu.hfut.innovate.community.entity.LikeRecord;
import edu.hfut.innovate.community.service.LikeRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author : Chowhound
 * @since : 2023/7/31 - 14:58
 */
@Api(tags = "点赞相关接口")
@RequestMapping("like")
@RestController
public class LikeRecordController {
    @Autowired
    private LikeRecordService likeRecordService;

    @ApiOperation(value = "点赞")
    @PostMapping("/save")
    public R like(
            @ApiParam(value = "点赞记录", required = true)
            @RequestBody LikeRecordDto likeRecordDto){

        LikeRecord likeRecord = BeanUtil.copyProperties(likeRecordDto, new LikeRecord());

        likeRecordService.saveLikeRecord(likeRecord);

        return R.ok();
    }

    @ApiOperation(value = "取消点赞")
    @PostMapping("/delete/{like_id}")
    public R delete(
            @ApiParam(value = "点赞记录id", required = true)
            @PathVariable("like_id") Long likeId){

        likeRecordService.removeLikeRecord(likeId);

        return R.ok();
    }

    @ApiOperation(value = "查询点赞记录")
    @GetMapping("/{user_id}")
    public R get(
            @ApiParam(value = "用户id", required = true)
            @PathVariable("user_id") Long userId){

        List<LikeRecordVo> likeRecordList = likeRecordService.listTopicLikedByUserId(userId);

        return R.ok(likeRecordList);
    }

    @ApiOperation("分页查询点赞记录")
    @GetMapping("/page/{user_id}")
    public R list(
            @ApiParam("分页查询参数")
            @RequestParam Map<String, Object> params, @PathVariable("user_id") Long userId) {
        PageUtils<LikeRecordVo> page = likeRecordService.queryPageByUserId(params, userId);
        List<LikeRecordVo> likeRecordVos = page.getList().stream()
                .map(likeRecord -> BeanUtil.copyProperties(likeRecord, new LikeRecordVo()))
                .toList();
        return R.ok(new PageUtils<>(likeRecordVos, page.getTotalCount(), page.getPageSize(), page.getCurrPage()));
    }


}
