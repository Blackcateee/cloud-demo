package cn.itcast.order;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PublisherTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testFanOut() {
        String fanOutExchange = "fanoutExchange";
        String message = "hello fanOut";
        rabbitTemplate.convertAndSend(fanOutExchange, "", message);
    }

    @Test
    public void testDirectRed() {
        String directExchange = "directExchange";
        String message = "hello direct";
        rabbitTemplate.convertAndSend(directExchange, "red", message);
    }

    @Test
    public void testDirectBlue() {
        String directExchange = "directExchange";
        String message = "hello direct";
        rabbitTemplate.convertAndSend(directExchange, "blue", message);
    }

    @Test
    public void testDirect() {
        String directExchange = "directExchange";
        String message = "hello direct";
        rabbitTemplate.convertAndSend(directExchange, "", message);
    }

    @Test
    public void testTopicNews() {
        String exchangeName = "topicExchange";
        String message = "hello china.news";
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);
    }

    @Test
    public void testTopicPeople() {
        String exchangeName = "topicExchange";
        String message = "hello china.people";
        rabbitTemplate.convertAndSend(exchangeName, "china.people", message);
    }

    @Test
    public void testTopicChina() {
        String exchangeName = "topicExchange";
        String message = "hello china.#";
        rabbitTemplate.convertAndSend(exchangeName, "china.#", message);
    }

    @Test
    public void testAMQP() {
        String queue = "simple.queue";
        String message = "hello world";
        for (int i = 0; i < 50; i++) {
            rabbitTemplate.convertAndSend(queue, message + i );
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() throws Exception {
        //1.建立链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //1.1 设置参数
        connectionFactory.setHost("121.40.137.246");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("root");
        connectionFactory.setPassword("root");

        //1.2 创建连接对象
        Connection connection = connectionFactory.newConnection();

        //2.创建channel
        Channel channel = connection.createChannel();

        //3.创建队列
        String queueName = "simple.queue";
        channel.queueDeclare(queueName, false, false, false, null);

        //4.消息发送
        String message = "hello rabbitmq";
        channel.basicPublish("", queueName, null, message.getBytes());
        System.out.println("消息发送成功[" + message + "]");

        channel.close();
        connection.close();
    }
}
