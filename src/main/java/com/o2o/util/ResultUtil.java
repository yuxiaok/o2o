package com.o2o.util;

import com.o2o.dto.Result;

/**
 * @Author yukai
 * @Date 2018年9月5日
 */
public class ResultUtil {
	
	/**
	 * 成功的返回结果
	* @param data
	* @return
	 */
	public static <T> Result<T> success(T data){
		Result<T> r = new Result<>();
		r.setJg(true);
		r.setData(data);
		return r;
	}
	
	/**
	 * 失败的返回结果
	* @param errCode
	* @param errMsg
	* @return
	 */
	public static <T> Result<T> error(int errCode, String errMsg){
		Result<T> r = new Result<>();
		r.setJg(false);
		r.setErrCode(errCode);
		r.setErrMsg(errMsg);
		return r;
	}
}
