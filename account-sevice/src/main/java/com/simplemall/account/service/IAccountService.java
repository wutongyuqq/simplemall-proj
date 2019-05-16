package com.simplemall.account.service;

import java.util.List;
import java.util.Map;

import com.simplemall.micro.serv.common.bean.account.AccAddress;
import com.simplemall.micro.serv.common.bean.account.Account;
import com.simplemall.micro.serv.common.bean.account.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface IAccountService {

	/**
	 * @param phone
	 * @param password
	 * @return
	 */
	Account login(String phone, String password);

	/**
	 * @param phone
	 * @return
	 */
	Account selectByPhone(String phone);
	/**
	 * 得到账户信息
	 *
	 * @param userId
	 * @return
	 */
	 Message selectByUserId(String userId);
	
	/**
	 * @param account
	 * @return
	 */
	boolean signup(Account account);
	
	/**
	 * @param tid
	 * @return
	 */
	AccAddress getAccAddress(String tid);
	
	/**
	 * @param accountId
	 * @return
	 */
	List<AccAddress> getAddressList(String accountId);
	/**
	 * @param message
	 * @return
	 */
	int  improveMessage(Message message);

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
}
