package com.nowcoder.community.service;

import com.nowcoder.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class CollectService {

    @Autowired
    private RedisTemplate redisTemplate;

    // 收藏
    public void Collect(int userId, int entityType, int entityId, int entityUserId) {
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {

                String entityLikeKey = RedisKeyUtil.getEntityCollectKey(entityType, entityId);
                String userLikeKey = RedisKeyUtil.getUserCollectKey(entityUserId);


                boolean isMember = operations.opsForSet().isMember(entityLikeKey, userId);

                operations.multi();

                if (isMember) {
                    operations.opsForSet().remove(entityLikeKey, userId);
                    operations.opsForValue().decrement(userLikeKey);
                } else {
                    operations.opsForSet().add(entityLikeKey, userId);
                    operations.opsForValue().increment(userLikeKey);
                }

                return operations.exec();
            }
        });
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
}
