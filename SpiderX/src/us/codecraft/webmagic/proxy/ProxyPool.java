package us.codecraft.webmagic.proxy;

import perfer.org.apache.http.HttpHost;

/**
 * Created by edwardsbean on 15-2-28.
 */
public interface ProxyPool {
    public void returnProxy(HttpHost host, int statusCode);
    public Proxy getProxy();
    public boolean isEnable();
}
