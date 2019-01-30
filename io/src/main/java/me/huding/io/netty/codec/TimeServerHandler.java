package me.huding.io.netty.codec;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created By hujianhong
 * Date: 2018/11/24
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    private final static Logger LOG = LoggerFactory.getLogger(TimeServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("received a request...");
        final ChannelFuture f = ctx.writeAndFlush(new UnixTime());

        f.addListener((ChannelFutureListener) future -> {
            assert f == future;
            ctx.close();
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("", cause);
        ctx.close();
    }
}
