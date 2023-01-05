package com.nowcoder.community.service;

import com.nowcoder.community.dao.*;
import com.nowcoder.community.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostDraftService {
    @Autowired
    private PostDraftMapper postDraftMapper;

    public PostDraft findDiscussPostByUserId(int userid) {
        return postDraftMapper.selectPostDraftByUserId(userid);
    }
    public int addPostDraft(PostDraft draft) {
        if (postDraftMapper.selectPostDraftByUserId(draft.getUserId()) == null) {
            return postDraftMapper.insertPostDraft(draft);
        }else{
            return postDraftMapper.updatePostDraft(draft);
        }
    }
    public int deletePostDraft(int userid){
        return postDraftMapper.deletePostDraft(userid);
    }



}
