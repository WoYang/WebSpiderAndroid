package com.allen.douyu;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.allen.spiderx.SpiderService;
import com.allen.spiderx.callback.ResponseCallback;
import com.allen.spiderx.utils.FileUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

public class Parse extends JsonFilePipeline {
	private ResponseCallback mCallBack;
	public Parse() {
		// TODO Auto-generated constructor stub
		super();
	}

	public Parse(String path) {
		// TODO Auto-generated constructor stub	
		super(path);
	}
	
	public Parse(String path,ResponseCallback callback) {
		// TODO Auto-generated constructor stub
		this(path);
		this.mCallBack = callback;
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		String requestUrl = resultItems.getRequest().getUrl();
		Log.d("YANG","get page: " + requestUrl);
		try {
			boolean dataAvilable = false;
			for (Map.Entry<String, Object> entry : resultItems.getAll()
					.entrySet()) {
				//data model
				if(entry.getKey().equals("object")){
					String file_name = requestUrl.substring(requestUrl.lastIndexOf("=")+1);
					FileUtils.checkDir2Make(path);
					PrintWriter printWriter = new PrintWriter(new FileWriter(getFile(path + file_name + ".json")));
					printWriter.write(entry.getValue().toString());
					printWriter.close();
					if(this.mCallBack != null){
						this.mCallBack.onResponse(SpiderService.TYPE_DOUYU_ITEM, requestUrl,path + file_name + ".json");
					}
					return;
				}
				for (String item : (List<String>) entry.getValue()) {
					if (item != null && !item.equals("")) {
						dataAvilable = true;
						break;
					}
				}
			}
			if (!dataAvilable) {
				return;
			}
			String path = this.path;
			String file_name = requestUrl.substring(requestUrl.lastIndexOf("/")+1);
			FileUtils.checkDir2Make(path);
			PrintWriter printWriter = new PrintWriter(new FileWriter(getFile(path + file_name + ".json")));
			printWriter.write("{");
			printWriter.write("\"info\":");
			printWriter.write("[");
			if (resultItems.getRequest().getUrl()
					.equals(DouYuDirectoryProcesser.REQUEST_URL)) {
				for (Map.Entry<String, Object> entry : resultItems.getAll()
						.entrySet()) {
					List<String> nodes = (List<String>) entry.getValue();
					for (int i = 0; i < nodes.size(); i++) {
						String item = nodes.get(i);
						String img = item.substring(
								item.indexOf("data-original=\"")
										+ "data-original=\"".length(),
								item.indexOf("\"",
										item.indexOf("data-original=\"")
												+ "data-original=\"".length()));
						String url = item.substring(
								item.indexOf("href=\"") + "href=\"".length(),
								item.indexOf("\"", item.indexOf("href=\"")
										+ "href=\"".length()));
						String title = item.substring(
								item.indexOf("class=\"title\">")
										+ "class=\"title\">".length(),
								item.indexOf("</p>",
										item.indexOf("class=\"title\">")
												+ "class=\"title\">".length()));

						Category source = new Category();
						source.setImg(img.replace("\n", ""));
						source.setUrl(url.replace("\n", ""));
						source.setTitle(title.replace("\n", ""));

						printWriter.write(JSON.toJSONString(source));
						if (i != nodes.size() - 1) {
							printWriter.write(",");
						}
					}
				}
				printWriter.write("]");
				printWriter.write("}");
				printWriter.close();
				if(this.mCallBack != null){
					this.mCallBack.onResponse(SpiderService.TYPE_DOUYU_DIRECTORY, requestUrl,path + file_name + ".json");
				}
			}else{
				for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
					List<String> nodes = (List<String>) entry.getValue();
					for (int i = 0; i < nodes.size(); i++) {
						String item = nodes.get(i);
						String img = item.substring(
								item.indexOf("data-original=\"")
										+ "data-original=\"".length(),
								item.indexOf("\"",
										item.indexOf("data-original=\"")
												+ "data-original=\"".length()));
						String rid = item.substring(
								item.indexOf("data-rid=\"")
										+ "data-rid=\"".length(),
								item.indexOf("\">",
										item.indexOf("data-rid=\"")
												+ "data-rid=\"".length()));
						String url = item.substring(
								item.indexOf("href=\"") + "href=\"".length(),
								item.indexOf("\"", item.indexOf("href=\"")
										+ "href=\"".length()));
						String ellipsis = item.substring(
								item.indexOf("class=\"ellipsis\">")
										+ "class=\"ellipsis\">".length(),
								item.indexOf("</h3>",
										item.indexOf("class=\"ellipsis\">")
												+ "class=\"ellipsis\">".length()));
						String tag = item.substring(
								item.indexOf("class=\"tag ellipsis\">")
										+ "class=\"tag ellipsis\">".length(),
								item.indexOf("</span>",
										item.indexOf("class=\"tag ellipsis\">")
												+ "class=\"tag ellipsis\">".length()));
						String anchor = item.substring(
								item.indexOf("class=\"dy-name ellipsis fl\">")
										+ "class=\"dy-name ellipsis fl\">".length(),
								item.indexOf("</span>",
										item.indexOf("class=\"dy-name ellipsis fl\">")
												+ "class=\"dy-name ellipsis fl\">".length()));
						String viewer = item.substring(
								item.indexOf("class=\"dy-num fr\">")
										+ "class=\"dy-num fr\">".length(),
								item.indexOf("</span>",
										item.indexOf("class=\"dy-num fr\">")
												+ "class=\"dy-num fr\">".length()));
						Poster source = new Poster();
						source.setImg(img.replace("\n", ""));
						source.setRid(rid.replace("\n", ""));
						source.setUrl(url.replace("\n", ""));
						source.setEllipsis(ellipsis.replace("\n", ""));
						source.setTag(tag.replace("\n", ""));
						source.setAnchor(anchor.replace("\n", ""));
						source.setViewer(viewer.replace("\n", ""));
						printWriter.write(JSON.toJSONString(source));
						if (i != nodes.size() - 1) {
							printWriter.write(",");
						}
					}
				}
				printWriter.write("]");
				printWriter.write("}");
				printWriter.close();
				if(this.mCallBack != null){
					this.mCallBack.onResponse(SpiderService.TYPE_DOUYU_PAGE, requestUrl,path + file_name + ".json");
				}
			}

		} catch (Exception e) {
			System.out.println("write file error" + e);
		}
	}
	
	
}
