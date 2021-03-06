package com.allen.bili;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePageModelPipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class BiliPageProcesser implements PageProcessor {
	public final static String REQUEST_URL = "http://www.bilibili.com";
	@Override
	public void process(Page page) {
		List<String> links = page.getHtml().xpath("//a[@class='i-link']" ).links().regex(".*bilibili.*").all();
		page.addTargetRequests(links);
		page.putField("conntent", page.getHtml().xpath("//div//div[@class='v-item'] | //div//div[@class='v']").all());
	}

	@Override
	public Site getSite() {
		return Site.me();
	}
}