package me.huding.io.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

/**
 * Created By hujianhong
 * Date: 2018/11/24
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {


    private static final Logger LOG = LoggerFactory.getLogger(DiscardServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        LOG.info("received a message...");
        ByteBuf buf = (ByteBuf)msg;
        try {
//            while (buf.isReadable()){
//                System.out.print((char)buf.readByte());
//            }
//            System.out.println();
            System.out.println(buf.toString(StandardCharsets.UTF_8));
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.error("", cause);
        ctx.close();
    }
}
