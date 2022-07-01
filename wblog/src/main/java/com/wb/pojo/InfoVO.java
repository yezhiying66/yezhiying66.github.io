package com.wb.pojo;

public class InfoVO {
	
	//标题
	private String title;
	//地址
	private String addr;
	//时间
	private String now;
	//序号
	private String no;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getNow() {
		return now;
	}
	public void setNow(String now) {
		this.now = now;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public InfoVO() {
		super();
	}
	public InfoVO(String title, String addr, String now, String no) {
		super();
		this.title = title;
		this.addr = addr;
		this.now = now;
		this.no = no;
	}
	@Override
	public String toString() {
		return "InfoVO [title=" + title + ", addr=" + addr + ", now=" + now + ", no=" + no + "]";
	}
	
	
}
