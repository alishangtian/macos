package com.alishangtian.macos.processor;

import com.alishangtian.macos.remoting.ChannelEventListener;
import com.alishangtian.macos.remoting.XtimerCommand;
import com.alishangtian.macos.remoting.processor.NettyRequestProcessor;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * @Description ChannelEventService
 * @Date 2020/6/20 下午7:23
 * @Author maoxiaobing
 **/
@Service
@Log4j2
public class ClientChannelProcessor implements ChannelEventListener, NettyRequestProcessor {
    private Map<String, Channel> activeChannel = new ConcurrentHashMap<>();
    private Map<String, CountDownLatch> countDownLatchMap = new ConcurrentHashMap<>();

    public void addCountdownLatch(String hostAddr, CountDownLatch countDownLatch) {
        countDownLatchMap.put(hostAddr, countDownLatch);
    }

    @Override
    public void onChannelConnect(String remoteAddr, Channel channel) {
        log.info("channel connected address {}", remoteAddr);
        activeChannel.put(remoteAddr, channel);
        CountDownLatch countDownLatch;
        if ((countDownLatch = this.countDownLatchMap.get(remoteAddr)) != null) {
            countDownLatch.countDown();
        }
    }

    @Override
    public void onChannelClose(String remoteAddr, Channel channel) {
        log.info("channel closed address {}", remoteAddr);
        activeChannel.remove(remoteAddr);
    }

    @Override
    public void onChannelException(String remoteAddr, Channel channel) {
        log.info("channel exception address {}", remoteAddr);
        activeChannel.remove(channel);
    }

    @Override
    public void onChannelIdle(String remoteAddr, Channel channel) {
    }

    @Override
    public Channel getChannel(String address) {
        return activeChannel.get(address);
    }

    @Override
    public XtimerCommand processRequest(ChannelHandlerContext ctx, XtimerCommand request) throws Exception {
        XtimerCommand response = XtimerCommand.builder().result(1).build();
        return response;
    }

    @Override
    public boolean rejectRequest() {
        return false;
    }
}
