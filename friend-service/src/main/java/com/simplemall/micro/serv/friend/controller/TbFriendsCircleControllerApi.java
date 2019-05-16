package com.simplemall.micro.serv.friend.controller;

import com.github.pagehelper.PageHelper;
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
import com.simplemall.micro.serv.friend.service.TbCommentService;
import com.simplemall.micro.serv.friend.service.TbFriendsCircleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "tbFriendsCircle")
public class TbFriendsCircleControllerApi {
   @Autowired
   private TbFriendsCircleService tbFriendsCircleService;

   @Autowired
   private TbCommentService tbCommentService;

   @RequestMapping(value = "findPage")
   public PageInfo findPage(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize,String userId) {
      PageHelper.startPage(pageNo, pageSize);
      List<TbFriendsCircle> list = tbFriendsCircleService.findList();
      for (TbFriendsCircle tbFriendsCircle : list){
         boolean flag = tbCommentService.checkIsDz(tbFriendsCircle.getId(),userId);
         tbFriendsCircle.setDzFlag(flag);
         List<TbComment> commentList = tbCommentService.findCommentsByArticleId(tbFriendsCircle.getId());
         tbFriendsCircle.setCommentList(commentList);
      }
      PageInfo pageInfo = new PageInfo(list);
      return pageInfo;
   }

   @RequestMapping(value = "queryFindPage")
   public PageInfo queryFindPage(@RequestParam("pageNo") int pageNo, @RequestParam("pageSize") int pageSize,@RequestParam("friendUserId") String friendUserId,String userId) {
      PageHelper.startPage(pageNo, pageSize);
      List<TbFriendsCircle> list = tbFriendsCircleService.queryFindList(friendUserId);
      for (TbFriendsCircle tbFriendsCircle : list){
         boolean flag = tbCommentService.checkIsDz(tbFriendsCircle.getId(),userId);
         tbFriendsCircle.setDzFlag(flag);
         List<TbComment> commentList = tbCommentService.findCommentsByArticleId(tbFriendsCircle.getId());
         tbFriendsCircle.setCommentList(commentList);
      }
      PageInfo pageInfo = new PageInfo(list);
      return pageInfo;
   }




   public void uploadFile(MultipartFile file) throws IOException {
      InputStream inputStream = file.getInputStream();
      //构造一个带指定Zone对象的配置类
     //构造一个带指定Zone对象的配置类
      Configuration cfg = new Configuration(Zone.zone0());
//...其他参数参考类注释
      UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
      String accessKey = "your access key";
      String secretKey = "your secret key";
      String bucket = "your bucket name";
//默认不指定key的情况下，以文件内容的hash值作为文件名
      String key = null;
      try {
         Auth auth = Auth.create(accessKey, secretKey);
         String upToken = auth.uploadToken(bucket);
         try {
            Response response = uploadManager.put(inputStream, key, upToken, null, null);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            String url = putRet.key;
         } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
               System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
               //ignore
            }
         }
      } catch (Exception ex) {
         //ignore
      }


   }
}