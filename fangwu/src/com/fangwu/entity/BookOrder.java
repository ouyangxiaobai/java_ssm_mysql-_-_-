package com.fangwu.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

/**
 * ���޶���ʵ����
 * @author Administrator
 *
 */
@Component
public class BookOrder {
	private Long id;//���޶���id
	private Long accountId;//�ͻ�id
	private Long roomTypeId;//����id
	private String name;//����������
	private String idCard;//���֤����
	private String mobile;//�ֻ���
	private int status;//״̬��0�������У�1������ס,2:�ѽ���
	private String arriveDate;//��ס����
	private String leaveDate;//��������
	private Date createTime;//��������
	private String remark;
   private String price;
   private String yajin;
	public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public String getYajin() {
	return yajin;
}
public void setYajin(String yajin) {
	this.yajin = yajin;
}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getRoomTypeId() {
		return roomTypeId;
	}
	public void setRoomTypeId(Long roomTypeId) {
		this.roomTypeId = roomTypeId;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "BookOrder [id=" + id + ", accountId=" + accountId + ", roomTypeId=" + roomTypeId + ", name=" + name
				+ ", idCard=" + idCard + ", mobile=" + mobile + ", status=" + status + ", arriveDate=" + arriveDate
				+ ", leaveDate=" + leaveDate + ", createTime=" + createTime + ", remark=" + remark + ", price=" + price
				+ ", yajin=" + yajin + "]";
	}
	
	
	
}
