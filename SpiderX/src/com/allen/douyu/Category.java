package com.allen.douyu;

public class Category {
	/**
	 * 分类名称
	 */
	private String title;
	/**
	 * 分类海报图片地址
	 */
	private String img;
	/**
	 * 分类页面跳转地址
	 */
	private String url;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
}
