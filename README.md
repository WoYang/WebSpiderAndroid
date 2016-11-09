# WebSpiderAndroid
 阶段1：
spider了某直播平台的video源,auto Inflate成View,使用bilibili player播放出视频,仅个人随意练手而已,并没有很细致的优化实际效果,有时间会把弹幕和界面优化下. 声明:本人不承担任何责任

Spider a broadcast platform video source, auto Inflate View, using BiliBili player to play video, only random person around, and no optimization of actual effect is very detailed, time will the barrage and interface optimization. Disclaimer: I do not bear any responsibility

阶段2：
对斗鱼直播、bilibili都实现了多线程spider, 在android上可以通过应用展示处理,目前只对斗鱼中的信息完成处理并可以播放视频,其他网站的是一样的处理.AllenMedia是对爬虫数据的展示并使用了ijkPlayer播放视频,SpiderX是多线程爬虫服务,把spider的数据保存到apk数据目录, Aidl接口提供了client
数据抓取和回调.
