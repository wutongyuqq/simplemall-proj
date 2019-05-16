package com.simplemall.micro.serv.common.bean.friend;

import com.simplemall.micro.serv.common.bean.account.Account;
import com.simplemall.micro.serv.common.bean.account.Message;

import java.util.Date;
import java.util.List;

public class TbFriendsCircle {

  private  String  id;
  private  String  userId;
  private  String  content;
  private  String  pictures;
  private  int     likenum;
  private  String  address;
  private  Date    createDate;
  private  Date    updateDate;
  private  String  remarks;
  private Message message;
  private List<TbComment> commentList;
  private boolean dzFlag;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getPictures() {
    return pictures;
  }

  public void setPictures(String pictures) {
    this.pictures = pictures;
  }

  public int getLikenum() {
    return likenum;
  }

  public void setLikenum(int likenum) {
    this.likenum = likenum;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public List<TbComment> getCommentList() {
    return commentList;
  }

  public void setCommentList(List<TbComment> commentList) {
    this.commentList = commentList;
  }

  public boolean isDzFlag() {
    return dzFlag;
  }

  public void setDzFlag(boolean dzFlag) {
    this.dzFlag = dzFlag;
  }
}
