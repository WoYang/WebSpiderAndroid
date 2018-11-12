package com.allen.spiderx.aidl;
import com.allen.spiderx.aidl.SpiderCallBack;
interface SpiderX{
	void setSpiderCallBack(SpiderCallBack callbcak);
	int startProcess(int type,String url,String ext);

}
