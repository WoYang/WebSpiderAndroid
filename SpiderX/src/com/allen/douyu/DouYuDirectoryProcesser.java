package com.allen.douyu;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class DouYuDirectoryProcesser implements PageProcessor{
	public final static String REQUEST_URL = "https://www.douyu.com/directory";
		
	@Override
	public void process(Page page) {
		if(page.getUrl().toString().equals(REQUEST_URL)){
			page.putField("category", page.getHtml().xpath("//li[@class='unit']").all());
		}
	}

	@Override
	public Site getSite() {
		return Site.me().setUserAgent("Mozilla/5.0 (Linux; U; Android 4.4.4; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30");
	}
}
