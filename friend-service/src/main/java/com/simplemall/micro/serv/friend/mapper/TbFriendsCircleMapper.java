package com.simplemall.micro.serv.friend.mapper;

import com.simplemall.micro.serv.common.bean.friend.TbFriendsCircle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Mapper
public interface TbFriendsCircleMapper {


    public List<TbFriendsCircle> findList();
    public List<TbFriendsCircle> queryFindList(String friendUserId);

    public int updateLikeNum(@Param("articleId") String articleId,@Param("type") String type);

}
