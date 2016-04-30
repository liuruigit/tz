package com.jhl.pojo.json;


/**
 * Account: xin.fang
 * Date: 14-3-10
 * Time: 下午4:46
 * json返回对象
 */
public class JsonBack {

	private int code;

	private String message;

	private Object obj;
	
	public JsonBack(){}
	
	public JsonBack(int code, String message){
	    this.code = code;
	    this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}


	public void exceptionJsonBack() {
		this.code = 102;
		this.message = "系统异常，联系管理员";
	}

    @Override
    public String toString() {
        return "JsonBack{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", obj=" + obj +
                '}';
    }
}
