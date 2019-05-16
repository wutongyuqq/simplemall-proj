package com.simplemall.micro.serv.friend.service.impl;

import com.simplemall.micro.serv.common.bean.friend.TbComment;
import com.simplemall.micro.serv.friend.mapper.TbCommentMapper;
import com.simplemall.micro.serv.friend.mapper.TbFriendsCircleMapper;
import com.simplemall.micro.serv.friend.service.TbCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class TbCommentImpl implements TbCommentService {

    @Autowired
    private TbCommentMapper tbCommentMapper;
    @Autowired
    private TbFriendsCircleMapper tbFriendsCircleMapper;

    public List<TbComment> findCommentsByArticleId(String articleId){
        return  tbCommentMapper.findCommentsByArticleId(articleId);
    }

    @Transactional(readOnly = false)
    public void dianzan(String articleId,String userId,String type){
        tbFriendsCircleMapper.updateLikeNum(articleId,type);
        tbCommentMapper.insertDz(articleId,userId);
        if ("1".equals(type)){
            tbCommentMapper.deleteDz(articleId,userId);
        }
    }

    public boolean checkIsDz(String articleId, String userId){
       List<String> list = tbCommentMapper.checkIsDz(articleId,userId);
       if(list.size() >=1){
           return false;
       }else{
           return  true;
       }
    }
    @Transactional(readOnly = false)
    public int comment(TbComment comment){
        return tbCommentMapper.insert(comment);
    }
}
