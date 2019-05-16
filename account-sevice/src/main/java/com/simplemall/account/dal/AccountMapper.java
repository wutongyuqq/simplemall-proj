package com.simplemall.account.dal;

import java.util.List;
import java.util.Map;

import com.simplemall.micro.serv.common.bean.account.Message;
import org.apache.ibatis.annotations.Param;

import com.simplemall.micro.serv.common.bean.account.Account;
import com.simplemall.micro.serv.common.bean.account.AccountCriteria;

public interface AccountMapper {
    /**
     * 根据条件计数
     *
     * @param example
     */
    int countByExample(AccountCriteria example);

    /**
     *
     * @param example
     */
    int deleteByExample(AccountCriteria example);

    /**
     * 根据主键删除数据库的记录
     *
     * @param tid
     */
    int deleteByPrimaryKey(String tid);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insert(Account record);

    /**
     * 插入数据库记录
     *
     * @param record
     */
    int insertSelective(Account record);

    void insertMessage(Message message);
    /**
     * 根据条件查询列表
     *
     * @param example
     */
    List<Account> selectByExample(AccountCriteria example);

    /**
     * 得到账户信息
     *
     * @param userId
     * @return
     */
     Message selectByUserId(String userId);

    /**
     * 根据主键获取一条数据库记录
     *
     * @param tid
     */
    Account selectByPrimaryKey(String tid);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountCriteria example);

    /**
     * 选择性更新数据库记录
     *
     * @param record
     * @param example
     */
    int updateByExample(@Param("record") Account record, @Param("example") AccountCriteria example);

    int improveMessage(Message message);

    /**
     * 获取用户列表信息
     * @return
     */
     List<Map<Object,Object>> getAccList(Message message);
    /**
     * 获取用户详情
     * @return
     */
     Map<Object,Object>  getAccDetail(String tid);
    /**
     * 根据主键来更新部分数据库记录
     *
     * @param record
     */
    int updateByPrimaryKeySelective(Account record);

    /**
     * 根据主键来更新数据库记录
     *
     * @param record
     */
    int updateByPrimaryKey(Account record);
}