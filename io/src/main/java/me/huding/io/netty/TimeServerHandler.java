package me.huding.io.netty;

import io.netty.buffer.ByteBuf;
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
        final ByteBuf buf = ctx.alloc().buffer(4);
        buf.writeInt((int) (System.currentTimeMillis() / 1000L));

        final ChannelFuture f = ctx.writeAndFlush(buf);

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
