package com.gasolimetro.gasolineraservice.entity;

public class ResponseGeneric<T> {
	private String msg;
	private T object;
	
	public ResponseGeneric() {
		super();
	}

	public ResponseGeneric(String msg, T object) {
		super();
		this.msg = msg;
		this.object = object;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}


	@Override
	public String toString() {
		return "ResponseGeneric [msg=" + msg + ", object=" + object + "]";
	}

}
