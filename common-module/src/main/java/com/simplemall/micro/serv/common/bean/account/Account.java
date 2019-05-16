package com.simplemall.micro.serv.common.bean.account;


import java.util.Date;
public class Account {
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
    private String phone;
    /**
     * 用户姓名
     * 表 : tb_acc_account
     * 对应字段 : user_name
     */
    private String userName;
    /**
     * 用户头像
     * 表 : tb_acc_account
     * 对应字段 : user_avatar
     */
    private String userAvatar;
    /**
     * 用户学历
     * 表 : tb_acc_account
     * 对应字段 : education
     */
    private String education;
    /**
     * 性别（0：男  1：女  2：未知）
     * 表 : tb_acc_account
     * 对应字段 : sex
     */
    private String sex;
    /**
     * 邮件
     * 表 : tb_acc_account
     * 对应字段 : email
     */
    private String email;

    /**
     * 密码
     * 表 : tb_acc_account
     * 对应字段 : password
     */
    private String password;

    /**
     * 允许登录0允许1禁止
     * 表 : tb_acc_account
     * 对应字段 : allowLogin
     */
    private String allowLogin;
    /**
     * 推荐人
     * 表 : tb_acc_account
     * 对应字段 : tjrId
     */
    private String tjrId;
    /**
     * 用户邀请码
     * 表 : tb_acc_account
     * 对应字段 : yqCode
     */
    private String yqCode;
    /**
     * 用户类型
     * 表 : tb_acc_account
     * 对应字段 : user_type
     */
    private String userType;
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
    private long createDate;

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

    /**
     * get method 
     *
     * @return tb_acc_account.tid：
     */
    public String getTid() {
        return tid;
    }

    /**
     * set method 
     *
     * @param tid  
     */
    public void setTid(String tid) {
        this.tid = tid == null ? null : tid.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAllowLogin() {
        return allowLogin;
    }

    public void setAllowLogin(String allowLogin) {
        this.allowLogin = allowLogin;
    }

    public String getTjrId() {
        return tjrId;
    }

    public void setTjrId(String tjrId) {
        this.tjrId = tjrId;
    }

    public String getYqCode() {
        return yqCode;
    }

    public void setYqCode(String yqCode) {
        this.yqCode = yqCode;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}