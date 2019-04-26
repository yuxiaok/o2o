package com.o2o.dto;

/**
 * @Author yukai
 * @Date 2018年9月5日
 */
public class Result<T> {
	/**
	 * 是否成功的标识
	 */
	private boolean jg;
	/**
	 * 返回的JSON数据
	 */
	private T data;
	/**
	 * 错误码
	 */
	private int errCode;
	/**
	 * 错误信息
	 */
	private String errMsg;
	public boolean isJg() {
		return jg;
	}
	public void setJg(boolean jg) {
		this.jg = jg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	
}
