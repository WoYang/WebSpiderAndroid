package com.allen.douyu;

public class Poster {
	/**
	 * 海报图片地址
	 */
	private String img;
	/**
	 * 直播roomid
	 */
	private String rid;
	/**
	 * 海报页面地址
	 */
	private String url;
	/**
	 * 海报描述
	 */
	private String ellipsis;
	/**
	 * 海报分类
	 */
	private String tag;
	/**
	 * 视频提供者
	 */
	private String anchor;
	/**
	 * 视频点击量
	 */
	private String viewer;

	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getRid() {
		return rid;
	}
	public void setRid(String rid) {
		this.rid = rid;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getEllipsis() {
		return ellipsis;
	}
	public void setEllipsis(String ellipsis) {
		this.ellipsis = ellipsis;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public String getViewer() {
		return viewer;
	}
	public void setViewer(String viewer) {
		this.viewer = viewer;
	}
}
