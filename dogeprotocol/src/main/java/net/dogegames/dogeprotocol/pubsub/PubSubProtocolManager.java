package net.dogegames.dogeprotocol.pubsub;

import com.google.common.base.Charsets;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.pubsub.RedisPubSubAdapter;
import com.lambdaworks.redis.pubsub.api.async.RedisPubSubAsyncCommands;
import net.dogegames.dogeprotocol.DogePacketHandler;
import net.dogegames.dogeprotocol.packet.DogePacket;

public class PubSubProtocolManager {
    private DogePacketHandler handler;
    private RedisPubSubAsyncCommands<String, String> subscriberConnection;
    private RedisPubSubAsyncCommands<String, String> publisherConnection;
    private RedisFuture<Void> subscriberFuture;

    public PubSubProtocolManager(RedisClient client) {
        this.subscriberConnection = client.connectPubSub().async();
        subscriberConnection.addListener(new RedisPubSubAdapter<String, String>() {
            @Override
            public void message(String channel, String message) {
                try {
                    ByteArrayDataInput input = ByteStreams.newDataInput(message.getBytes(Charsets.UTF_8));
                    DogePacket packet = PubSubPacketManager.readPacket(input);
                    if (packet != null && handler != null) packet.handle(handler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        this.publisherConnection = client.connectPubSub().async();
        this.subscriberFuture = subscriberConnection.subscribe("minecraft");
    }

    public synchronized void writePacket(DogePacket packet) {
        try {
            ByteArrayDataOutput output = ByteStreams.newDataOutput();
            PubSubPacketManager.writePacket(output, packet);
            publisherConnection.publish("minecraft", new String(output.toByteArray(), Charsets.UTF_8));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DogePacketHandler getHandler() {
        return handler;
    }

    public void setHandler(DogePacketHandler handler) {
        this.handler = handler;
    }

    public void close() {
        publisherConnection.close();
        subscriberFuture.cancel(true);
        subscriberConnection.close();
    }
}

