package com.allen.bili;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import perfer.org.apache.commons.codec.digest.DigestUtils;

import com.alibaba.fastjson.JSON;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;

public class VideoParse extends JsonFilePipeline {

	public VideoParse() {
		// TODO Auto-generated constructor stub
		super();
	}

	public VideoParse(String path) {
		// TODO Auto-generated constructor stub
		super(path);
	}

	@Override
	public void process(ResultItems resultItems, Task task) {
		String requestUrl = resultItems.getRequest().getUrl();
		System.out.println("get page: " + requestUrl);
		try {
			boolean dataAvilable = false;
			for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
				for(String item : (List<String>) entry.getValue()){
					if( item !=null && !item.equals("")){
						dataAvilable = true;
						break;
					}
				}
			}
			if(!dataAvilable){
				return;
			}
			String file_name = requestUrl.substring(requestUrl.lastIndexOf("=")+1);
			PrintWriter printWriter = new PrintWriter(new FileWriter(
					getFile(path + file_name + ".json")));
			printWriter.write("{");
			printWriter.write("\"info\":");
			printWriter.write("[");
			for (Map.Entry<String, Object> entry : resultItems.getAll()
					.entrySet()) {
				List<String> nodes = (List<String>) entry.getValue();
				for (int i=0;i< nodes.size(); i++) {
					String item = nodes.get(i);
					String img = item.substring(item.indexOf("src=\"") + 5,
							item.indexOf("\"", item.indexOf("src=\"") + 5));
					String url = item.substring(item.indexOf("href=\"") + 6,
							item.indexOf("\"", item.indexOf("href=\"") + 6));
					String title = item.substring(
							item.indexOf("class=\"t\">") + 10,
							item.indexOf("</div>",
									item.indexOf("class=\"t\">") + 10));
					
					System.out.println("img:" + img.replace("\n", ""));
					System.out.println("url:" + url.replace("\n", ""));
					System.out.println("title:" + title.replace("\n", ""));

					Video source = new Video();
					source.setImg(img.replace("\n", ""));
					source.setUrl(url.replace("\n", ""));
					source.setTitle(title.replace("\n", ""));

					printWriter.write(JSON.toJSONString(source));
					if(i != nodes.size() - 1){
						printWriter.write(",");
					}
				}
			}
			printWriter.write("]");
			printWriter.write("}");
			printWriter.close();
		} catch (Exception e) {
			System.out.println("write file error" + e);
		}
	}
}
