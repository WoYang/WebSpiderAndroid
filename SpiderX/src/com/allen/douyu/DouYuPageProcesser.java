package com.allen.douyu;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class DouYuPageProcesser implements PageProcessor{

	@Override
	public void process(Page page) {
		page.putField("content", page.getHtml().xpath("//ul[@id='live-list-contentbox']//li").all());
	}

	@Override
	public Site getSite() {
		return Site.me().setUserAgent("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
	}
}
