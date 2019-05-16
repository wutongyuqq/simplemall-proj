package com.simplemall.micro.serv.friend.service;

import com.simplemall.micro.serv.common.bean.friend.TbComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TbCommentService {


    public List<TbComment> findCommentsByArticleId(String articleId);

    public void dianzan(String articleId,String userId,String type);

    public boolean checkIsDz(String articleId, String userId);

    public int comment(TbComment comment);


}
