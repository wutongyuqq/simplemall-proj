package com.simplemall.micro.serv.page.api;


import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import com.simplemall.micro.serv.common.bean.friend.TbComment;
import com.simplemall.micro.serv.common.bean.friend.TbFriendsCircle;
import com.simplemall.micro.serv.page.client.FriendFeignClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "tbFriendsCircle")
public class TbFriendsCircleControllerApi {
  /* @Autowired
   private TbFriendsCircleService  tbFriendsCircleService;*/

  @Autowired
  private FriendFeignClient  friendFeignClient;


   @RequestMapping(value = "findPage")
   public PageInfo findPage(@RequestParam("pageNo")int pageNo, @RequestParam("pageSize")int pageSize){
      PageInfo pageInfo = friendFeignClient.findPage(pageNo,pageSize);
      return  pageInfo;
   }

    @RequestMapping(value = "queryFindPage")
    public PageInfo queryFindPage(@RequestParam("pageNo")int pageNo, @RequestParam("pageSize")int pageSize, @RequestParam("friendUserId")String friendUserId){
        PageInfo pageInfo = friendFeignClient.queryFindPage(pageNo,pageSize,friendUserId);
        return  pageInfo;
    }

   @RequestMapping(value = "dianzan")
   public Map<String,String> dianzan(@RequestParam("articleId") String articleId, @RequestParam("userId") String userId,@RequestParam("type") String type){
      return friendFeignClient.dianzan(articleId,userId,type);
   }
    @ApiOperation(value = "评论")
    @RequestMapping(value = "comment")
    public int comment(TbComment tbComment){
        return friendFeignClient.comment(tbComment);
    }



   /*@RequestMapping(value = "publish")
   public void publish(MultipartFile[] files, TbFriendsCircle tbFriendsCircle, HttpServletRequest request) throws IOException {
      for(int i=0;i<files.size();i++){
         MultipartFile file = files.get(i);
         InputStream inputStream = file.getInputStream();



   }
*/



}
