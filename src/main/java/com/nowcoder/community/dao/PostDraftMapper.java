package com.nowcoder.community.dao;

import com.nowcoder.community.entity.*;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostDraftMapper {
    PostDraft selectPostDraftByUserId(int userId);
    int insertPostDraft(PostDraft postDraft);
    int updatePostDraft(PostDraft postDraft);

    int deletePostDraft(int userId);

}
