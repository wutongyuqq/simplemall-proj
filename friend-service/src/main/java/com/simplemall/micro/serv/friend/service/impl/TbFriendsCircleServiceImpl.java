package com.simplemall.micro.serv.friend.service.impl;


import com.simplemall.micro.serv.common.bean.friend.TbFriendsCircle;
import com.simplemall.micro.serv.friend.mapper.TbFriendsCircleMapper;
import com.simplemall.micro.serv.friend.service.TbFriendsCircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TbFriendsCircleServiceImpl implements TbFriendsCircleService {
    @Autowired
    private TbFriendsCircleMapper tbFriendsCircleMapper;

    public List<TbFriendsCircle> findList(){
        return tbFriendsCircleMapper.findList();
    }

    public List<TbFriendsCircle> queryFindList(String friendUserId){
        return tbFriendsCircleMapper.queryFindList(friendUserId);
    }
}
