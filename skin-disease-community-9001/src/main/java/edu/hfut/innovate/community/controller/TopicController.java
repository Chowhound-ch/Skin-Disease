package edu.hfut.innovate.community.controller;

import edu.hfut.innovate.common.domain.dto.community.TopicDto;
import edu.hfut.innovate.common.domain.entity.UserAuth;
import edu.hfut.innovate.common.domain.vo.community.TopicVo;
import edu.hfut.innovate.common.renren.R;
import edu.hfut.innovate.common.util.BeanUtil;
import edu.hfut.innovate.community.entity.TopicEntity;
import edu.hfut.innovate.community.entity.TopicTagRelationEntity;
import edu.hfut.innovate.community.service.TopicService;
import edu.hfut.innovate.community.service.TopicTagRelationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 
 *
 * @author Chowhound
 */
@Api(tags = "话题相关接口")
@RestController
@RequestMapping("topic")
public class TopicController {
    @Autowired
    private TopicService topicService;
    @Autowired
    private TopicTagRelationService topicTagRelationService;

    /**
     * 分页查询话题
     */
    @ApiOperation("分页查询话题")
    @GetMapping("/page/")
    public R list(
            @ApiParam("分页查询参数")
            @RequestParam Integer page,
            @RequestParam Integer limit,
            @RequestParam(required = false) Long locationId,
            @RequestParam(required = false) Integer sort,
            UserAuth auth) {

        return R.ok(topicService.queryPageByUserId(page, limit, auth.getUserId(), locationId, sort));
    }

    @ApiOperation("根据话题id查询话题(详细信息)")
    @GetMapping("/{topicId}")
    public R getTopic(@PathVariable("topicId") Long topicId, UserAuth auth) {
        TopicVo topicVo = topicService.getTopicByIdWithLikeInfo(topicId, auth.getUserId());
        if (topicVo == null) {
            return R.error(HttpStatus.NOT_FOUND.value(), "话题不存在");
        }

        return R.ok(topicVo);
    }

    /**
     * 保存
     */
    @ApiOperation("新增话题")
    @PostMapping("/save")
    public R save(@RequestBody TopicDto topic){
        TopicEntity topicEntity = BeanUtil.copyProperties(topic, new TopicEntity());
        if (topic.getIsAnonymous() == 1) {
            topicEntity.setAnonymousName("匿名用户");
        }
        topicService.save(topicEntity);

        List<TopicTagRelationEntity> tagRelationEntities = topic.getTagIds().stream().map(tagId -> {
            TopicTagRelationEntity topicTagRelationEntity = new TopicTagRelationEntity();
            topicTagRelationEntity.setTagId(tagId);
            topicTagRelationEntity.setTopicId(topicEntity.getTopicId());

            return topicTagRelationEntity;
        }).toList();

        topicTagRelationService.saveBatch(tagRelationEntities);



        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("修改话题 content 和 title")
    @PostMapping("/update")
    public R update(@RequestBody TopicEntity topic){
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setTopicId(topic.getTopicId());
        topicEntity.setContent(topic.getContent());
        topicEntity.setTitle(topic.getTitle());

		topicService.updateById(topicEntity);

        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("删除话题")
    @Transactional
    @PostMapping("/delete/{topicId}")
    public R delete(@PathVariable Long topicId){
        topicService.removeTopicById(topicId);
        return R.ok();
    }


    @ApiOperation("模糊查询")
    @GetMapping("/search")
    public R search(@RequestParam("keyword") String keyword, UserAuth auth) {

        return R.ok(topicService.search(keyword, auth.getUserId()));
    }

}
