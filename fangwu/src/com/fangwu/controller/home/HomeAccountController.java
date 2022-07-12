package com.fangwu.controller.home;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fangwu.entity.Account;
import com.fangwu.entity.BookOrder;
import com.fangwu.entity.RoomType;
import com.fangwu.service.AccountService;
import com.fangwu.service.BookOrderService;
import com.fangwu.service.RoomTypeService;

/**
 * ǰ̨�û�������
 * @author Administrator
 *
 */
@RequestMapping("/home/account")
@Controller
public class HomeAccountController {
	
	@Autowired
	private RoomTypeService roomTypeService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private BookOrderService bookOrderService;
	
	/**
	 * ǰ̨�û�������ҳ
	 * @param model
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public ModelAndView list(ModelAndView model,HttpServletRequest request
			){
		Account account = (Account)request.getSession().getAttribute("account");
		Map<String,Object> queryMap = new HashMap<String, Object>();
		queryMap.put("accountId", account.getId());
		queryMap.put("offset", 0);
		queryMap.put("pageSize", 999);
		model.addObject("bookOrderList", bookOrderService.findList(queryMap));
		model.addObject("roomTypeList", roomTypeService.findAll());
		model.setViewName("home/account/index");
		return model;
	}
	
	/**
	 * ���ⷿ��ҳ��
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/book_order",method=RequestMethod.GET)
	public ModelAndView bookOrder(ModelAndView model,Long roomTypeId
			){
		model.addObject("roomType", roomTypeService.find(roomTypeId));
		model.setViewName("home/account/book_order");
		return model;
	}
	
	
	/**
	 * ������Ϣ�ύ
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/book_order",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> bookOrderAct(BookOrder bookOrder,HttpServletRequest request){
		Map<String, String> ret = new HashMap<String, String>();
		if(bookOrder == null){
			ret.put("type", "error");
			ret.put("msg", "����д��ȷ�ĳ��ⶩ����Ϣ!");
			return ret;
		}
		Account account = (Account)request.getSession().getAttribute("account");
		if(account == null){
			ret.put("type", "error");
			ret.put("msg", "�ͻ�����Ϊ��!");
			return ret;
		}
		bookOrder.setAccountId(account.getId());
		if(bookOrder.getRoomTypeId() == null){
			ret.put("type", "error");
			ret.put("msg", "���Ͳ���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getName())){
			ret.put("type", "error");
			ret.put("msg", "���ⶩ����ϵ�����Ʋ���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getMobile())){
			ret.put("type", "error");
			ret.put("msg", "���ⶩ����ϵ���ֻ��Ų���Ϊ��!");
			return ret;
		}
		if(StringUtils.isEmpty(bookOrder.getIdCard())){
			ret.put("type", "error");
			ret.put("msg", "��ϵ�����֤�Ų���Ϊ��!");
			return ret;
		}
//		if(StringUtils.isEmpty(bookOrder.getArriveDate())){
//			ret.put("type", "error");
//			ret.put("msg", "����ʱ�䲻��Ϊ��!");
//			return ret;
//		}
//		if(StringUtils.isEmpty(bookOrder.getLeaveDate())){
//			ret.put("type", "error");
//			ret.put("msg", "���ʱ�䲻��Ϊ��!");
//			return ret;
//		}
		bookOrder.setCreateTime(new Date());
		bookOrder.setStatus(0);
		if(bookOrderService.add(bookOrder) <= 0){
			ret.put("type", "error");
			ret.put("msg", "���ʧ�ܣ�����ϵ����Ա!");
			return ret;
		}
		RoomType roomType = roomTypeService.find(bookOrder.getRoomTypeId());
		//����ɹ���ȥ�޸ĸ÷��͵ĳ�����
		if(roomType != null){
			roomType.setBookNum(roomType.getBookNum() + 1);
			roomType.setAvilableNum(roomType.getAvilableNum() - 1);
			roomTypeService.updateNum(roomType);
			//������õķ�����Ϊ0�������ø÷���״̬����
			if(roomType.getAvilableNum() == 0){
				roomType.setStatus(0);
				roomTypeService.edit(roomType);
			}
		}
		ret.put("type", "success");
		ret.put("msg", "����ɹ�!");
		return ret;
	}
	
	/**
	 * �޸ĸ�����Ϣ�ύ
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/update_info",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updateInfoAct(Account account,HttpServletRequest request){
		Map<String,String> retMap = new HashMap<String, String>();
		if(account == null){
			retMap.put("type", "error");
			retMap.put("msg", "����д��ȷ���û���Ϣ��");
			return retMap;
		}
		if(StringUtils.isEmpty(account.getName())){
			retMap.put("type", "error");
			retMap.put("msg", "�û�������Ϊ�գ�");
			return retMap;
		}
		Account loginedAccount = (Account)request.getSession().getAttribute("account");
		if(isExist(account.getName(),loginedAccount.getId())){
			retMap.put("type", "error");
			retMap.put("msg", "���û����Ѿ����ڣ�");
			return retMap;
		}
		loginedAccount.setAddress(account.getAddress());
		loginedAccount.setIdCard(account.getIdCard());
		loginedAccount.setMobile(account.getMobile());
		loginedAccount.setName(account.getName());
		loginedAccount.setRealName(account.getRealName());
		if(accountService.edit(loginedAccount) <= 0){
			retMap.put("type", "error");
			retMap.put("msg", "�޸�ʧ�ܣ�����ϵ����Ա��");
			return retMap;
		}
		request.getSession().setAttribute("account", loginedAccount);
		retMap.put("type", "success");
		retMap.put("msg", "�޸ĳɹ���");
		return retMap;
	}
	
	/**
	 * �޸������ύ
	 * @param account
	 * @return
	 */
	@RequestMapping(value="/update_pwd",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> updatePwdAct(String oldPassword,String newPassword,HttpServletRequest request){
		Map<String,String> retMap = new HashMap<String, String>();
		if(StringUtils.isEmpty(oldPassword)){
			retMap.put("type", "error");
			retMap.put("msg", "����дԭ�������룡");
			return retMap;
		}
		if(StringUtils.isEmpty(newPassword)){
			retMap.put("type", "error");
			retMap.put("msg", "����д�����룡");
			return retMap;
		}
		Account loginedAccount = (Account)request.getSession().getAttribute("account");
		if(!oldPassword.equals(loginedAccount.getPassword())){
			retMap.put("type", "error");
			retMap.put("msg", "ԭ�������");
			return retMap;
		}
		loginedAccount.setPassword(newPassword);
		if(accountService.edit(loginedAccount) <= 0){
			retMap.put("type", "error");
			retMap.put("msg", "�޸�ʧ�ܣ�����ϵ����Ա��");
			return retMap;
		}
		retMap.put("type", "success");
		retMap.put("msg", "�޸�����ɹ���");
		return retMap;
	}
	
	/**
	 * �ж��û��Ƿ����
	 * @param name
	 * @param id
	 * @return
	 */
	private boolean isExist(String name,Long id){
		Account account = accountService.findByName(name);
		if(account == null)return false;
		if(account != null && account.getId().longValue() == id)return false;
		return true;
	}
}
