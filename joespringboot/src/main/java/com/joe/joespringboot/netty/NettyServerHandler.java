package com.joe.joespringboot.netty;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {
    private DispatcherServlet servlet;
    private ServletContext    servletContext;

    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /*public NettyServerHandler(DispatcherServlet servlet){
        this.servlet = servlet;
        this.servletContext = servlet.getServletConfig().getServletContext();
    }*/

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//        super.handlerAdded(ctx);
        Channel incoming = ctx.channel();

        //通知所有已经连接到服务器的客户端，有一个新的通道加入
        channels.writeAndFlush("[SERVER]-" + incoming.remoteAddress() + "加入\n");
        channels.add(incoming);

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
//        super.handlerRemoved(ctx);
        //获取连接的channel
        Channel incomming = ctx.channel();
        channels.writeAndFlush("[SERVER]-" + incomming.remoteAddress() + "离开\n");

        //从服务端的channelGroup中移除当前离开的客户端
//        channels.remove(incomming);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        Channel incoming = ctx.channel();
        System.out.println("[SERVER] - " + incoming.remoteAddress() + " 在线 \n" );
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        super.channelInactive(ctx);
        Channel incoming = ctx.channel();
        System.out.println("[SERVER] - " + incoming.remoteAddress() + " 掉线 \n" );
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        System.out.println("===============com.benlai.bicrm.netty handler exceptionCaught");
        Channel incoming = ctx.channel();
        System.out.println("[SERVER] - " + incoming.remoteAddress() + " 异常 \n" );

        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        Channel incoming = channelHandlerContext.channel();
        for (Channel channel : channels) {
            if (channel != incoming){
                channel.writeAndFlush("[" + incoming.remoteAddress() + "]" + s + "\n");
            } else {
                channel.writeAndFlush("[you]" + s + "\n");
            }
        }

        /*Map<String, String> parammap = getRequestParams(fullHttpRequest);

        if(channelHandlerContext.channel().isActive()) {
            MockHttpServletResponse servletResponse = new MockHttpServletResponse();
            MockHttpServletRequest  servletRequest  =new MockHttpServletRequest(servletContext);
            // headers
            for (String name : fullHttpRequest.headers().names()) {
                for (String value : fullHttpRequest.headers().getAll(name)) {
                    servletRequest.addHeader(name, value);
                }
            }
            String uri = fullHttpRequest.uri();
            uri = new String(uri.getBytes("ISO8859-1"), "UTF-8");
            uri = URLDecoder.decode(uri, "UTF-8");
            UriComponents uriComponents = UriComponentsBuilder.fromUriString(uri).build();
            String        path          = uriComponents.getPath();
            path = URLDecoder.decode(path, "UTF-8");
            servletRequest.setRequestURI(path);
            servletRequest.setServletPath(path);
            servletRequest.setMethod(fullHttpRequest.method().name());

            if (uriComponents.getScheme() != null) {
                servletRequest.setScheme(uriComponents.getScheme());
            }
            if (uriComponents.getHost() != null) {
                servletRequest.setServerName(uriComponents.getHost());
            }
            if (uriComponents.getPort() != -1) {
                servletRequest.setServerPort(uriComponents.getPort());
            }

            ByteBuf content = fullHttpRequest.content();
            content.readerIndex(0);
            byte[] data = new byte[content.readableBytes()];
            content.readBytes(data);
            servletRequest.setContent(data);

            try {
                if (uriComponents.getQuery() != null) {
                    String query = UriUtils.decode(uriComponents.getQuery(),"UTF-8");
                    servletRequest.setQueryString(query);
                }
                if(parammap!=null&&parammap.size()>0){
                    for (String key : parammap.keySet()) {
                        servletRequest.addParameter(UriUtils.decode(key,"UTF-8"), UriUtils.decode(parammap.get(key) == null ? "": parammap.get(key), "UTF-8"));
                    }
                }

            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            this.servlet.service(servletRequest,servletResponse);

            HttpResponseStatus status = HttpResponseStatus.valueOf(servletResponse.getStatus());
            String             result = servletResponse.getContentAsString();
            result = StringUtils.isEmpty(result) ? "" : result;

            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));
            response.headers().set("Content-Type", "text/json;charset=UTF-8");
            response.headers().set("Access-Control-Allow-Origin", "*");
            response.headers().set("Access-Control-Allow-Headers", "Content-Type,Content-Length, Authorization, Accept,X-Requested-With,X-File-Name");
            response.headers().set("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");
            response.headers().set("Content-Length", Integer.valueOf(response.content().readableBytes()));
            response.headers().set("Connection", "keep-alive");
            ChannelFuture writeFuture = channelHandlerContext.writeAndFlush(response);
            writeFuture.addListener(ChannelFutureListener.CLOSE);
        }*/
    }

    private Map<String, String> getRequestParams(HttpRequest req){
        Map<String, String>requestParams=new HashMap<String, String>();
        // 处理get请求
        if (req.method() == HttpMethod.GET) {
            QueryStringDecoder                        decoder  = new QueryStringDecoder(req.uri());
            Map<String, List<String>>                 parame   = decoder.parameters();
            Iterator<Map.Entry<String, List<String>>> iterator = parame.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, List<String>> next = iterator.next();
                requestParams.put(next.getKey(), next.getValue().get(0));
            }
        }
        // 处理POST请求
        if (req.method() == HttpMethod.POST) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(
                    new DefaultHttpDataFactory(false), req);
            List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();
            for(InterfaceHttpData data:postData){
                if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                    MemoryAttribute attribute = (MemoryAttribute) data;
                    requestParams.put(attribute.getName(), attribute.getValue());
                }
            }
        }
        return requestParams;
    }

}
