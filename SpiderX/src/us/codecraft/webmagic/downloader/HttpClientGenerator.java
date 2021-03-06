package us.codecraft.webmagic.downloader;

import org.apache.commons.lang3.StringUtils;
import perfer.org.apache.http.auth.AuthScope;
import perfer.org.apache.http.client.CredentialsProvider;


import perfer.org.apache.http.protocol.HttpContext;
import perfer.org.apache.http.HttpException;
import perfer.org.apache.http.HttpRequest;
import perfer.org.apache.http.HttpRequestInterceptor;
import perfer.org.apache.http.auth.UsernamePasswordCredentials;
import perfer.org.apache.http.client.CookieStore;
import perfer.org.apache.http.config.Registry;
import perfer.org.apache.http.config.RegistryBuilder;
import perfer.org.apache.http.config.SocketConfig;
import perfer.org.apache.http.conn.socket.ConnectionSocketFactory;
import perfer.org.apache.http.conn.socket.PlainConnectionSocketFactory;
import perfer.org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import perfer.org.apache.http.impl.client.*;
import perfer.org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import perfer.org.apache.http.impl.cookie.BasicClientCookie;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.proxy.Proxy;

import java.io.IOException;
import java.util.Map;

public class HttpClientGenerator {

    private PoolingHttpClientConnectionManager connectionManager;

    public HttpClientGenerator() {
        Registry<ConnectionSocketFactory> reg = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        connectionManager = new PoolingHttpClientConnectionManager(reg);
        connectionManager.setDefaultMaxPerRoute(100);
    }

    public HttpClientGenerator setPoolSize(int poolSize) {
        connectionManager.setMaxTotal(poolSize);
        return this;
    }

    public CloseableHttpClient getClient(Site site, Proxy proxy) {
        return generateClient(site, proxy);
    }

    private CloseableHttpClient generateClient(Site site, Proxy proxy) {
        CredentialsProvider credsProvider = null;
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        
        if(proxy!=null && StringUtils.isNotBlank(proxy.getUser()) && StringUtils.isNotBlank(proxy.getPassword()))
        {
            credsProvider= new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(proxy.getHttpHost().getAddress().getHostAddress(), proxy.getHttpHost().getPort()),
                    new UsernamePasswordCredentials(proxy.getUser(), proxy.getPassword()));
            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }

        if(site!=null&&site.getHttpProxy()!=null&&site.getUsernamePasswordCredentials()!=null){
            credsProvider = new BasicCredentialsProvider();
            credsProvider.setCredentials(
                    new AuthScope(site.getHttpProxy()),site.getUsernamePasswordCredentials());
            httpClientBuilder.setDefaultCredentialsProvider(credsProvider);
        }
        
        httpClientBuilder.setConnectionManager(connectionManager);
        if (site != null && site.getUserAgent() != null) {
            httpClientBuilder.setUserAgent(site.getUserAgent());
        } else {
            httpClientBuilder.setUserAgent("");
        }
        if (site == null || site.isUseGzip()) {
            httpClientBuilder.addInterceptorFirst(new HttpRequestInterceptor() {

                public void process(
                        final HttpRequest request,
                        final HttpContext context) throws HttpException, IOException {
                    if (!request.containsHeader("Accept-Encoding")) {
                        request.addHeader("Accept-Encoding", "gzip");
                    }
                }
            });
        }


        SocketConfig socketConfig = SocketConfig.custom().setSoKeepAlive(true).setTcpNoDelay(true).build();
        httpClientBuilder.setDefaultSocketConfig(socketConfig);
        if (site != null) {
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(site.getRetryTimes(), true));
        }
        generateCookie(httpClientBuilder, site);
        return httpClientBuilder.build();
    }

    private void generateCookie(HttpClientBuilder httpClientBuilder, Site site) {
        CookieStore cookieStore = new BasicCookieStore();
        for (Map.Entry<String, String> cookieEntry : site.getCookies().entrySet()) {
            BasicClientCookie cookie = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
            cookie.setDomain(site.getDomain());
            cookieStore.addCookie(cookie);
        }
        for (Map.Entry<String, Map<String, String>> domainEntry : site.getAllCookies().entrySet()) {
            for (Map.Entry<String, String> cookieEntry : domainEntry.getValue().entrySet()) {
                BasicClientCookie cookie = new BasicClientCookie(cookieEntry.getKey(), cookieEntry.getValue());
                cookie.setDomain(domainEntry.getKey());
                cookieStore.addCookie(cookie);
            }
        }
        httpClientBuilder.setDefaultCookieStore(cookieStore);
    }

}
