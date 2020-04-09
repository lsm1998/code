package com.lsm1998.tomcat;

import com.lsm1998.tomcat.annotaion.WebServlet;
import com.lsm1998.tomcat.exception.ConfigException;
import com.lsm1998.tomcat.http.HttpServletRequest;
import com.lsm1998.tomcat.http.HttpServletResponse;
import com.lsm1998.tomcat.http.HttpServlet;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class Tomcat
{
    private int port;

    private static final String CONFIG_NAME="tomcat.yml";

    private Map<String, HttpServlet> servletMapping = new HashMap<>();

    private void init() throws ConfigException {
        try
        {
            //加载配置文件,同时初始化 ServletMapping对象
            Yaml yaml = new Yaml();
            String configPath=this.getClass().getResource("/").getPath() + CONFIG_NAME;
            Map<String,Object> configMap = yaml.loadAs(new FileInputStream(configPath),Map.class);
            Map<String,Object> serverMap= (Map<String, Object>) configMap.get("server");
            port=(Integer)serverMap.get("port");
            Map<String,Object> servletMap= (Map<String, Object>) configMap.get("servlet");
            String servletPath = (String) servletMap.get("path");
            String classPath = servletPath.replace(".", "\\");
            File file=new File(this.getClass().getResource("/").getPath()+classPath);
            File[] files=file.listFiles();
            List<Class<?>> classList=new ArrayList<>();
            assert files != null;
            for (File f:files)
            {
                int len=f.getName().length();
                classList.add(Class.forName(servletPath +"."+f.getName().substring(0,len-6)));
            }
            for (Class<?> c:classList)
            {
                WebServlet webServlet = c.getAnnotation(WebServlet.class);
                HttpServlet obj = (HttpServlet)c.getConstructor().newInstance();
                servletMapping.put(webServlet.url(), obj);
            }
        } catch (Exception e)
        {
            e.printStackTrace();
            throw new ConfigException("配置文件解析错误:"+e.getMessage());
        }
    }

    public void start() throws Exception
    {
        init();
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {

            ServerBootstrap server = new ServerBootstrap();
            // 链路式编程
            server.group(bossGroup, workerGroup)
                    // 主线程处理类,看到这样的写法，底层就是用反射
                    .channel(NioServerSocketChannel.class)
                    // 子线程处理类 , Handler
                    .childHandler(new ChannelInitializer<SocketChannel>()
                    {
                        // 客户端初始化处理
                        protected void initChannel(SocketChannel client) throws Exception
                        {
                            // 无锁化串行编程
                            //Netty对HTTP协议的封装，顺序有要求
                            // HttpResponseEncoder 编码器
                            // 责任链模式，双向链表Inbound OutBound
                            client.pipeline().addLast(new HttpResponseEncoder());
                            // HttpRequestDecoder 解码器
                            client.pipeline().addLast(new HttpRequestDecoder());
                            // 业务逻辑处理
                            client.pipeline().addLast(new TomcatHandler());
                        }
                    })
                    // 针对主线程的配置 分配线程最大数量 128
                    .option(ChannelOption.SO_BACKLOG, 128)
                    // 针对子线程的配置 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true);
            //3、启动服务器
            ChannelFuture f = server.bind(port).sync();
            System.out.println("Tomcat 已启动，监听的端口是：" + port);
            f.channel().closeFuture().sync();
        } catch (Exception e)
        {
            e.printStackTrace();
        } finally
        {
            // 关闭线程池
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public class TomcatHandler extends ChannelInboundHandlerAdapter
    {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
        {
            if (msg instanceof HttpRequest)
            {
                HttpRequest req = (HttpRequest) msg;
                HttpServletRequest request = new HttpServletRequest(ctx, req);
                HttpServletResponse response = new HttpServletResponse(ctx, req);
                String url = request.getUrl();
                if (servletMapping.containsKey(url))
                {
                    servletMapping.get(url).service(request, response);
                } else
                {
                    response.getWrite().write("<h1>404 - Not Found</h1>");
                }
            }
        }
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
        {
        }
    }
}
