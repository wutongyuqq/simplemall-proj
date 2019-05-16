package com.simplemall.micro.serv.friend.mapper;

import com.simplemall.micro.serv.common.bean.friend.TbComment;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TbCommentMapper {

    public List<TbComment> findCommentsByArticleId(@Param("articleId") String articleId);

    public int dianzan(@Param("articleId")String articleId,@Param("userId")String userId);

    public List<String> checkIsDz(@Param("articleId")String articleId,@Param("userId") String userId);

    public int deleteDz(@Param("articleId") String articleId,@Param("userId") String userId);

    public int insert(@RequestBody TbComment comment);

    public int insertDz(@Param("articleId") String articleId,@Param("userId") String userId);
}