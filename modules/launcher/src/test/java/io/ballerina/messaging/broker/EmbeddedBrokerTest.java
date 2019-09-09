package io.ballerina.messaging.broker;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class EmbeddedBrokerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmbeddedBrokerTest.class);

    @Test
    public void testOpenConnection() throws Exception {
        EmbeddedBroker broker = new EmbeddedBroker();
        broker.start();

        ConnectionFactory factory = new ConnectionFactory();

        factory.setPort(5672);
        Connection connection = factory.newConnection("test-connection1");
        LOGGER.info("\n\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n\n");
        connection.close(12, "Test close");
        broker.stop();
    }
}
