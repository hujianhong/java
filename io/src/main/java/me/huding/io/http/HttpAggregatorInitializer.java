package me.huding.io.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("codec", new HttpServerCodec());  //2
        pipeline.addLast("aggegator", new HttpObjectAggregator(512 * 1024));  //3
        pipeline.addLast("compressor",new HttpContentCompressor()); //4
    }
}