package com.simplemall.account.service.impl;

import java.util.List;
import java.util.Map;

import com.simplemall.micro.serv.common.bean.account.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplemall.account.dal.AccAddressMapper;
import com.simplemall.account.dal.AccountMapper;
import com.simplemall.account.service.IAccountService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Service
@Transactional
public class AccountServiceImpl implements IAccountService {
	
	private Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

	@Autowired
	AccountMapper accountMapper;

	@Autowired
	AccAddressMapper addressMapper;

	@Override
	public AccAddress getAccAddress(String tid) {
		return null;
	}

	@Override
	public Account login(String phone, String password) {
		AccountCriteria criteria = new AccountCriteria();
		criteria.createCriteria().andPhoneEqualTo(phone).andPasswordEqualTo(password);
		List<Account> list = accountMapper.selectByExample(criteria);
		logger.info("{}登陆成功!8080",phone);
		return !CollectionUtils.isEmpty(list)?list.get(0):new Account();
	}
	@Override
	public Account selectByPhone(String phone){
		AccountCriteria criteria = new AccountCriteria();
		criteria.createCriteria().andPhoneEqualTo(phone);
		List<Account> list = accountMapper.selectByExample(criteria);
		logger.info("{}登陆成功!8080",phone);
		return !CollectionUtils.isEmpty(list)?list.get(0):new Account();
	}
	/**
	 * 得到账户信息
	 *
	 * @param userId
	 * @return
	 */
	@Override
	public Message selectByUserId(String userId){
		return accountMapper.selectByUserId(userId);
	}
	@Override
	public boolean signup(Account account) {
		AccountCriteria example = new AccountCriteria();
		example.createCriteria().andPhoneEqualTo(account.getPhone());
		List<Account> list = accountMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			logger.warn("{}-用户已存在，请选择其它用户名!",account.getPhone());
			return false;
		}
		int result = accountMapper.insertSelective(account);
		Message message = new Message();
		message.setUserId(account.getTid());
		accountMapper.insertMessage(message);
		logger.info("{}注册成功！",account.getPhone());
		return result > 0 ? true : false;
	}

	@Override
	public List<AccAddress> getAddressList(String accountId) {
		AccAddressCriteria criteria = new AccAddressCriteria();
		criteria.createCriteria().andAccountIdEqualTo(accountId);
		List<AccAddress> list = addressMapper.selectByExample(criteria);
		return list;
	}
	@Override
	/**
	 * @param message
	 * @return
	 */
	public int  improveMessage(Message message){
		return accountMapper.improveMessage(message);
	}
	@Override
	/**
	 * 获取用户列表信息
	 * @return
	 */
	public List<Map<Object,Object>> getAccList(Message message){
		return accountMapper.getAccList(message);
	}
	/**
	 * 获取用户详情
	 * @return
	 */
	public Map<Object,Object>  getAccDetail(String tid){
		return   accountMapper.getAccDetail(tid);
	}
}
