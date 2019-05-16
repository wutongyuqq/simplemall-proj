package com.simplemall.micro.serv.friend.controller;

import com.simplemall.micro.serv.common.bean.friend.TbComment;
import com.simplemall.micro.serv.friend.service.TbCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/tbComment")
public class TbCommentControllerApi {
    @Autowired
    private TbCommentService  tbCommentService;



    @RequestMapping(value="findCommentListByArticleId")
    public List<TbComment> findCommentsByArticleId(@RequestParam("articleId") String articleId){
      return  tbCommentService.findCommentsByArticleId(articleId);
    }

    @RequestMapping(value="comment")
    public int comment(@RequestBody TbComment comment){
        return  tbCommentService.comment(comment);
    }

    @RequestMapping(value="dianzan")
    public Map<String,String> dianzan(@RequestParam("articleId") String articleId, @RequestParam("userId") String userId,@RequestParam("type") String type){
        Map<String,String> retMap = new HashMap<String,String>();
        retMap.put("code","1");
        retMap.put("code","操作失败");
        boolean flag = tbCommentService.checkIsDz(articleId,userId);
        if("0".equals(type)){
            if (flag){
                tbCommentService.dianzan(articleId,userId,type);
                retMap.put("code","0");
                retMap.put("code","操作成功");
            }
        }else{
            if (!flag){
                tbCommentService.dianzan(articleId,userId,type);
                retMap.put("code","0");
                retMap.put("code","操作成功");
            }
        }
        return  retMap;
    }

}
