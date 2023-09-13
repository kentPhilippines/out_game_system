package com.lt.win.service.base.websocket;

import com.lt.win.service.cache.redis.ConfigCache;
import com.xxl.job.core.log.XxlJobLogger;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import java.net.URI;
import java.util.function.BiConsumer;

/**
 * <p>
 * Netty : WebSocketClient
 * </p>
 *
 * @author andy
 * @since 2020/10/21
 */
@Component
@Slf4j
public class WebSocketClient {
    @Resource
    private ConfigCache configCache;
    private static final EventLoopGroup group = new NioEventLoopGroup();

    /**
     * 连接
     */
    public synchronized Channel open() {
        Channel ch = null;
        try {
            URI uri = URI.create(configCache.getWsServer());
            Bootstrap b = new Bootstrap();
            String protocol = uri.getScheme();
            XxlJobLogger.log("uri=" + uri);
            if (!("ws".equals(protocol) || "wss".equals(protocol))) {
                throw new IllegalArgumentException("Unsupported protocol: " + protocol);
            }

            final WebSocketClientHandler handler =
                    new WebSocketClientHandler(
                            WebSocketClientHandshakerFactory.newHandshaker(
                                    uri, WebSocketVersion.V13, null, false, EmptyHttpHeaders.INSTANCE, 1280000)
                    );

            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            if ("wss".equals(protocol)) {
                                SSLEngine sslEngine = SSLContext.getDefault().createSSLEngine();
                                sslEngine.setUseClientMode(true);
                                pipeline.addLast("ssl", new SslHandler(sslEngine));
                            }
                            pipeline.addLast("http-codec", new HttpClientCodec());
                            pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                            pipeline.addLast("ws-handler", handler);
                        }
                    });
            XxlJobLogger.log("host=" + uri.getHost() + ";port=" + uri.getPort());
            ch = b.connect(uri.getHost(), uri.getPort()).sync().channel();
            handler.handshakeFuture().sync();

        } catch (Exception e) {
            biConsumer.accept(" WebSocketClient打开异常 ", e);
        }
        return ch;
    }

    /**
     * 关闭
     *
     */
    public synchronized void close(Channel ch) throws InterruptedException {
        ch.writeAndFlush(new CloseWebSocketFrame());
        ch.closeFuture().sync();
    }

    BiConsumer<String, Exception> biConsumer = (message, exception) -> {
        XxlJobLogger.log(message + exception);
        StackTraceElement[] stackTrace = exception.getStackTrace();
        for (StackTraceElement element : stackTrace) {
            XxlJobLogger.log(element.toString());
        }
    };

    /**
     * 发送
     *
     * @param msgBody 消息
     */
    public void sendMessage(String msgBody) {
        Channel ch = null;
        try {
            ch = open();
            ch.writeAndFlush(new TextWebSocketFrame(msgBody));
            XxlJobLogger.log("推送成功 ==> {}", msgBody);
        } catch (Exception e) {
            biConsumer.accept("推送异常 ", e);
        } finally {
            try {
                if (ch != null) {
                    close(ch);
                }
            } catch (Exception e1) {
                biConsumer.accept("关闭异常 ", e1);
            }
        }
    }
}
