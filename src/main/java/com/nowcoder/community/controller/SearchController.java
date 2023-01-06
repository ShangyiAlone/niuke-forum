package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.service.ElasticsearchService;
import com.nowcoder.community.service.LikeService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class SearchController implements CommunityConstant {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    // search?keyword=xxx
    @RequestMapping(path = "/search", method = RequestMethod.GET)
    public String search(String keyword, Page page, Model model,
                         @RequestParam(name = "orderMode", defaultValue = "3") int orderMode) {
        // 搜索帖子
        org.springframework.data.domain.Page<DiscussPost> searchResult =
                elasticsearchService.searchDiscussPost(keyword, page.getCurrent() - 1, page.getLimit());
        // 聚合数据
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (searchResult != null) {
            for (DiscussPost post : searchResult) {
                Map<String, Object> map = new HashMap<>();
                // 帖子
                map.put("post", post);
                // 作者
                map.put("user", userService.findUserById(post.getUserId()));
                // 点赞数量
                map.put("likeCount", likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId()));
                // 评论数量
                map.put("commentCount",post.getCommentCount());
                // 热度
                map.put("score",post.getScore());
                // 创建时间
                map.put("createTime",post.getCreateTime());
                discussPosts.add(map);
            }

        }

        //判断排序模式
        switch (orderMode){
            case 0:
                //最新排序，按照创建时间数量
                Collections.sort(discussPosts, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        Date aTime = (Date) o1.get("createTime");
                        Date bTime = (Date) o2.get("createTime");

                        if(aTime.before(bTime))
                        {
                            return 1;
                        }else {
                            return -1;
                        }
                    }
                });
                break;
            case 1:
                // 按热度排序展示
                Collections.sort(discussPosts, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        double a =(double)o1.get("score");
                        double b =(double)o2.get("score");
                        if (a> b) {
                            return -1;
                        }
                        else if (a < b) {
                            return 1;
                        }
                        else{
                            return 0;
                        }
                    }
                });
                break;
            case 2:
                // 按点赞数排序展示
                Collections.sort(discussPosts, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        long a =(long)o1.get("likeCount");
                        long b =(long)o2.get("likeCount");
                        if (a> b) {
                            return -1;
                        }
                        else if (a < b) {
                            return 1;
                        }
                        else{
                            return 0;
                        }
                    }
                });
                break;
            case 3:
                // 按回帖数排序展示
                Collections.sort(discussPosts, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        int a =(int)o1.get("commentCount");
                        int b =(int)o2.get("commentCount");
                        if (a> b) {
                            return -1;
                        }
                        else if (a < b) {
                            return 1;
                        }
                        else{
                            return 0;
                        }
                    }
                });
                break;
            default:
                break;
        }



        model.addAttribute("discussPosts", discussPosts);
        model.addAttribute("orderMode", orderMode);
        model.addAttribute("keyword", keyword);

        // 分页信息
        page.setPath("/search?keyword=" + keyword);
        page.setRows(searchResult == null ? 0 : (int) searchResult.getTotalElements());

        return "/site/search";
    }

}
