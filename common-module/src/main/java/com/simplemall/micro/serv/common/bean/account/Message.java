package com.simplemall.micro.serv.common.bean.account;

import java.util.Date;

public class Message {
    /**
     * 
     * 表 : tb_acc_account
     * 对应字段 : tid
     */
    private String tid;

    /**
     * 手机号
     * 表 : tb_acc_account
     * 对应字段 : phone
     */
    private String userId;
    /**
     * 用户姓名
     * 表 : tb_acc_account
     * 对应字段 : user_name
     */
    private String realName;
    /**
     * 用户昵称
     * 表 : tb_acc_account
     * 对应字段 : user_avatar
     */
    private String nick;
    /**
     * 用户头像
     * 表 : tb_acc_account
     * 对应字段 : user_avatar
     */
    private String avatar;
    /**
     * 用户图片展示
     * 表 : tb_acc_account
     * 对应字段 : user_avatar
     */
    private String photoShow;
    /**
     * 用户学历
     * 表 : tb_acc_account
     * 对应字段 : education
     */
    private String signature;
    /**
     * 性别（0：男  1：女  2：未知）
     * 表 : tb_acc_account
     * 对应字段 : sex
     */
    private String sex;
    /**
     * 常居住地
     * 表 : tb_acc_account
     * 对应字段 : email
     */
    private String address;

    /**
     * 户籍所在地
     * 表 : tb_acc_account
     * 对应字段 : password
     */
    private String addressSfz;

    /**
     *年龄
     */
    private String age;
    /**
     * 职业
     */
    private String profession;
    /**
     * 体重
     */
    private String weight;
    /**
     * 身高
     */
    private String height;
    /**
     * salary
     */
    private String salary;
    /**
     * 是否已购房
     */
    private String house;

    /**
     * 婚姻状况
     */
    private String marriage;

    /**
     * 学历
     */
    private String education;
    /**
     * 爱好
     */
    private String hobby;
    /**
     * 对另一半要求
     */
    private String require;
    /**
     * 
     * 表 : tb_acc_account
     * 对应字段 : create_by
     */
    private String createBy;

    /**
     * 
     * 表 : tb_acc_account
     * 对应字段 : create_date
     */
    private Date createDate;

    /**
     * 
     * 表 : tb_acc_account
     * 对应字段 : update_by
     */
    private String updateBy;

    /**
     * 
     * 表 : tb_acc_account
     * 对应字段 : update_date
     */
    private Date updateDate;

    /**
     * 
     * 表 : tb_acc_account
     * 对应字段 : remark
     */
    private String remark;

    /**
     * 
     * 表 : tb_acc_account
     * 对应字段 : version
     */
    private Integer version;

    /**
     * 
     * 表 : tb_acc_account
     * 对应字段 : state
     */
    private Integer state;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhotoShow() {
        return photoShow;
    }

    public void setPhotoShow(String photoShow) {
        this.photoShow = photoShow;
    }

    /**
     * get method 
     *
     * @return tb_acc_account.tid：
     */


    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressSfz() {
        return addressSfz;
    }

    public void setAddressSfz(String addressSfz) {
        this.addressSfz = addressSfz;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    /**
     * get method 
     *
     * @return tb_acc_account.create_by：
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * set method 
     *
     * @param createBy  
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    /**
     * get method 
     *
     * @return tb_acc_account.create_date：
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * set method 
     *
     * @param createDate  
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * get method 
     *
     * @return tb_acc_account.update_by：
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * set method 
     *
     * @param updateBy  
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    /**
     * get method 
     *
     * @return tb_acc_account.update_date：
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * set method 
     *
     * @param updateDate  
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * get method 
     *
     * @return tb_acc_account.remark：
     */
    public String getRemark() {
        return remark;
    }

    /**
     * set method 
     *
     * @param remark  
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * get method 
     *
     * @return tb_acc_account.version：
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * set method 
     *
     * @param version  
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * get method 
     *
     * @return tb_acc_account.state：
     */
    public Integer getState() {
        return state;
    }

    /**
     * set method 
     *
     * @param state  
     */
    public void setState(Integer state) {
        this.state = state;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
    }
}