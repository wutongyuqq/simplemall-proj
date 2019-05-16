package com.simplemall.micro.serv.page.client;

import com.github.pagehelper.PageInfo;
import com.simplemall.micro.serv.common.bean.friend.TbComment;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name="friend-service")
public interface FriendFeignClient {

    @RequestMapping(value = "tbFriendsCircle/findPage")
    public PageInfo findPage(@RequestParam("pageNo")int pageNo, @RequestParam("pageSize")int pageSize);

    @RequestMapping(value = "tbFriendsCircle/queryFindPage")
    public PageInfo queryFindPage(@RequestParam("pageNo")int pageNo, @RequestParam("pageSize")int pageSize ,@RequestParam("friendUserId")String friendUserId);

    @RequestMapping(value = "tbComment/dianzan")
    public Map<String,String> dianzan(@RequestParam("articleId") String articleId, @RequestParam("userId") String userId, @RequestParam("type") String type);

    @RequestMapping(value = "tbComment/comment")
    public int comment(@RequestBody TbComment tbComment);
}
