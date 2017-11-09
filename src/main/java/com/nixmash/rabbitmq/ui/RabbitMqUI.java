package com.nixmash.rabbitmq.ui;

import com.google.inject.Inject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import io.bootique.rabbitmq.client.channel.ChannelFactory;
import io.bootique.rabbitmq.client.connection.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMqUI implements IRabbitMqUI {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqUI.class);
    private static final String QUEUE_NAME = "bqQueue";
    private static final String CONNECTION_NAME = "bqConnection";
    private static final String EXCHANGE_NAME = "bqExchange";

    private ConnectionFactory connectionFactory;
    private ChannelFactory channelFactory;

    @Inject
    public RabbitMqUI(ConnectionFactory connectionFactory, ChannelFactory channelFactory) {
        this.connectionFactory = connectionFactory;
        this.channelFactory = channelFactory;
    }

    @Override
    public void init() throws IOException, TimeoutException {
        Connection connection = connectionFactory.forName(CONNECTION_NAME);
        Channel channel = channelFactory.openChannel(connection, EXCHANGE_NAME, QUEUE_NAME, "hello");
//        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueDeclare();
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
