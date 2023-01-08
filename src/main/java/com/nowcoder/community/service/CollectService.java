package com.nowcoder.community.service;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CollectService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private DiscussPostService DiscussPostService;

    // 收藏
    public void Collect(int userId, int entityType, int entityId, int entityUserId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                String entityLikeKey = RedisKeyUtil.getEntityCollectKey(entityType, entityId);
                String userLikeKey = RedisKeyUtil.getUserCollectKey(entityUserId);
                String userKey = RedisKeyUtil.getCollectKey( userId);

                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);

                operations.multi();

                if (isMember) {
                    operations.opsForSet().remove(entityLikeKey, userId);
                    operations.opsForZSet().remove(userKey,entityId);

                    operations.opsForValue().decrement(userLikeKey);
                } else {
                    operations.opsForSet().add(entityLikeKey, userId);
                    operations.opsForZSet().add(userKey,entityId,System.currentTimeMillis());

                    operations.opsForValue().increment(userLikeKey);
                }


                return operations.exec();
            }
        });
        findentity(userId, 0, 5);
    }

    // 查询某实体点赞的数量
    public long findEntityCollectCount(int entityType, int entityId) {
        String entityCollectKey = RedisKeyUtil.getEntityCollectKey(entityType, entityId);
        return redisTemplate.opsForSet().size(entityCollectKey);
    }

    // 查询某人对某实体的收藏状态
    public int findEntityCollectStatus(int userId, int entityType, int entityId) {
        String entityCollectKey = RedisKeyUtil.getEntityCollectKey(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityCollectKey, userId) ? 1 : 0;
    }

    // 查询某个用户获得的收藏数
    public int findUserCollectCount(int userId) {
        String userCollectKey = RedisKeyUtil.getUserCollectKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userCollectKey);
        return count == null ? 0 : count.intValue();
    }

    // 查询某用户的收藏文章
    public List<Map<String, Object>> findentity(int userId, int offset, int limit) {
        String userKey = RedisKeyUtil.getCollectKey( userId);

        Set<Integer> targetIds = redisTemplate.opsForZSet().reverseRange(userKey, offset, offset + limit - 1);

        if (targetIds == null) {
            return null;
        }

        List<Map<String, Object>> list = new ArrayList<>();
        for (Integer targetId : targetIds) {
            System.out.println("id");
            System.out.println(targetId);
            Map<String, Object> map = new HashMap<>();
            DiscussPost user = DiscussPostService.findDiscussPostById(targetId);
            map.put("DiscussPost", user);
            Double score = redisTemplate.opsForZSet().score(userKey, targetId);
            map.put("collectTime", new Date(score.longValue()));
            list.add(map);
        }

        return list;
    }
}
