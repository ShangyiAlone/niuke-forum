package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.service.CollectService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static com.nowcoder.community.util.CommunityConstant.*;
import static java.io.FileDescriptor.out;

@Controller
public class CollectController {
    @Autowired
    private CollectService CollectService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/collect", method = RequestMethod.POST)
    @ResponseBody
    public String Collect(int entityType, int entityId, int entityUserId, int postId) {
//        System.out.println(entityType);
//        System.out.println(entityId);
//        System.out.println(entityUserId);
//        System.out.println(postId);

        User user = hostHolder.getUser();

        // 收藏
        CollectService.Collect(user.getId(), entityType, entityId, entityUserId);

        // 数量
        long CollectCount = CollectService.findEntityCollectCount(entityType, entityId);
        // 状态
        int CollectStatus = CollectService.findEntityCollectStatus(user.getId(), entityType, entityId);
        // 返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("CollectCount", CollectCount);
        map.put("CollectStatus", CollectStatus);

        // 触发点赞事件
        if (CollectStatus == 1) {
            Event event = new Event()
                    .setTopic(TOPIC_COLLECT)
                    .setUserId(hostHolder.getUser().getId())
                    .setEntityType(entityType)
                    .setEntityId(entityId)
                    .setEntityUserId(entityUserId)
                    .setData("postId", postId);
            eventProducer.fireEvent(event);
        }

//        if(entityType == ENTITY_TYPE_POST) {
//            // 计算帖子分数
//            String redisKey = RedisKeyUtil.getPostScoreKey();
//            redisTemplate.opsForSet().add(redisKey, postId);
//        }

        return CommunityUtil.getJSONString(0, null, map);
    }
}
