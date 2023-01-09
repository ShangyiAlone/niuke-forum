package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Event;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.service.CollectService;
import com.nowcoder.community.service.DataService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.nowcoder.community.util.CommunityConstant.*;

@Controller
public class CollectController {
    @Autowired
    private CollectService CollectService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private UserService userService;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DataService dataService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(path = "/collect", method = RequestMethod.POST)
    @ResponseBody
    public String Collect(int entityType, int entityId, int entityUserId, int postId) {

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

    @RequestMapping(path = "/collect/{userId}", method = RequestMethod.GET)
    public String getCollect(@PathVariable("userId") int userId, Page page, Model model) {
        User user = userService.findUserById(userId);
        if (user == null) {
            throw new RuntimeException("该用户不存在!");
        }
        model.addAttribute("user", user);

//        page.setLimit(5);
        page.setPath("/collect/" + userId);
        page.setRows((int) CollectService.findUserCollectCount(userId));

//        List<Map<String, Object>> discussPostList = CollectService.findentity(userId, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPostList = CollectService.findentity(userId, page.getOffset(), page.getLimit());

        List<Map<String, Object>> discussVOList = new ArrayList<>();
        for (Map<String, Object> post : discussPostList) {
            Map<String, Object> map = new HashMap<>();
            map.put("discussPost", post.get("DiscussPost"));
            discussVOList.add(map);

        }
        model.addAttribute("discussPostsList", discussVOList);
        System.out.println(discussVOList);

        return "/site/my-collect";
    }
}
