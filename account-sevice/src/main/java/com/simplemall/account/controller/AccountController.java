package com.simplemall.account.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.simplemall.micro.serv.common.bean.account.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.simplemall.account.service.IAccountService;
import com.simplemall.micro.serv.common.bean.account.Account;
import com.simplemall.micro.serv.common.constant.SystemConstants;

import javax.print.Doc;
import java.util.List;
import java.util.Map;

/**
 * 个人账户
 * 
 * @author guooo
 *
 */
@RestController
@RequestMapping("/acc")
public class AccountController {

	@Autowired
	IAccountService accountService;

	/**
	 * 登陆
	 *
	 * @param phone
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
	public Account login(@RequestParam("phone") String phone, @RequestParam("password") String password) {
		Account result = accountService.login(phone, password);
		return result;
	}

	/**
	 * 通过手机号查询用户
	 *
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "getAccount", method = {RequestMethod.GET, RequestMethod.POST})
	public Account login(@RequestParam("phone") String phone) {
		Account result = accountService.selectByPhone(phone);
		return result;
	}
	/**
	 * 得到账户信息
	 *
	 * @param userId
	 * @return
	 */
	@RequestMapping(value ="getMessage", method = {RequestMethod.GET, RequestMethod.POST})
	public Message selectByUserId(@RequestParam("userId") String userId){
		Message result = accountService.selectByUserId(userId);
		System.out.println(result);
		return result;
	}
	/**
	 * 注册
	 *
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "signup", method = {RequestMethod.GET, RequestMethod.POST})
	public Account signup(@RequestBody Account account) {
		boolean result = accountService.signup(account);
		if (result) {
			return accountService.selectByPhone(account.getPhone());
		}
		return null;
	}

	/**
	 * 完善资料
	 *
	 * @param message
	 * @return
	 */
	@RequestMapping(value = "improveMessage", method = {RequestMethod.GET, RequestMethod.POST})
	public int improveMessage(@RequestBody Message message) {
		return accountService.improveMessage(message);
	}

	/**
	 * 获取用户列表信息
	 * @return
	 */
	@RequestMapping(value = "list",method = RequestMethod.POST)
	public PageInfo<Map<Object,Object>> getAccList(Message message,@RequestParam("currentPage") Integer currentPage,@RequestParam("pageSize") Integer pageSize){
		PageHelper.startPage(currentPage, pageSize);
		List<Map<Object,Object>> mapList = accountService.getAccList(message);
		PageInfo<Map<Object,Object>> pageInfo = new PageInfo<>(mapList);
		return pageInfo;
	}
	/**
	 * 获取用户详情
	 * @return
	 */
	@RequestMapping(value = "detail",method = {RequestMethod.GET, RequestMethod.POST})
	public Map<Object,Object>  getAccDetail(@RequestParam("tid")String tid){
		return  accountService.getAccDetail(tid);
	}
}
