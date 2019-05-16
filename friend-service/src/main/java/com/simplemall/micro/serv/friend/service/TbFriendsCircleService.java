package com.simplemall.micro.serv.friend.service;

import com.simplemall.micro.serv.common.bean.friend.TbFriendsCircle;

import java.util.List;
import java.util.Map;

public interface TbFriendsCircleService {


    public List<TbFriendsCircle> findList();

    public List<TbFriendsCircle> queryFindList(String friendUserId);
}
