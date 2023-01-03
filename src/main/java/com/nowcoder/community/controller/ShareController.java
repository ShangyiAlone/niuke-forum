package com.nowcoder.community.controller;

import com.nowcoder.community.entity.Event;
import com.nowcoder.community.event.EventProducer;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @BelongsProject: niuke-forum
 * @BelongsPackage: com.nowcoder.community.controller
 * @Author: lilei
 * @CreateTime: 2023-01-02  19:54
 * @Description: TODO
 * @Version: 1.0
 */
@Controller
public class ShareController implements CommunityConstant {
    private static final Logger logger= LoggerFactory.getLogger(ShareController.class);

    @Autowired
    private EventProducer eventProducer;

    @Value("${community.path.domain}")
    public String domain;

    @Value("${server.servlet.context-path}")
    public String contextPath;

    @Value("${wk.image.storage}")
    public String wkImageStorage;

    @RequestMapping(path = "/share",method = RequestMethod.GET)
    @ResponseBody
    public String share(String htmlUrl, Model model){
        //文件名
        String fileName= CommunityUtil.generateUUID();

        //异步生成长图
        Event event=new Event()
                .setTopic(TOPIC_SHARE)
                .setData("htmlUrl",htmlUrl)
                .setData("fileName",fileName)
                .setData("suffix",".png");
        eventProducer.fireEvent(event);

        //返回访问路径
        Map<String,Object> map=new HashMap<>();
        map.put("shareUrl",domain+contextPath+"/share/image/"+fileName);

        model.addAttribute("shareUrl",domain+contextPath+"/share/image/"+fileName);
        return CommunityUtil.getJSONString(0,null,map);
    }
    //获取长图
    @RequestMapping(path = "/share/image/{fileName}",method = RequestMethod.GET)
    public void getShareImage(@PathVariable("fileName") String fileName, HttpServletResponse response){
        if(StringUtils.isBlank(fileName)){
            throw new IllegalArgumentException("文件名不能为空!");
        }
        response.setContentType("image/png");
        File file=new File(wkImageStorage+"/"+fileName+".png");
        try{
            OutputStream os=response.getOutputStream();
            FileInputStream is=new FileInputStream(file);
            byte[] buffer=new byte[1024];
            int b=0;
            while ((b=is.read(buffer))!=-1){
                os.write(buffer,0,b);
            }
        }catch (IOException e){
            logger.error("获取长图失败"+e.getMessage());
        }
    }
}
