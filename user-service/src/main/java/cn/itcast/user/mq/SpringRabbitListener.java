package cn.itcast.user.mq;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Component
public class SpringRabbitListener {

    @RabbitListener(queues = "simple.queue")
    public void onMessage(String msg) throws InterruptedException {
        System.out.println("ms:[" + msg + "]" + LocalTime.now());
        Thread.sleep(20);
    }

    @RabbitListener(queues = "simple.queue")
    public void onMessageWork(String msg) throws InterruptedException {
        System.err.println("msg......:[" + msg + "]" + LocalDateTime.now());
        Thread.sleep(200);
    }

    @RabbitListener(queues = "fanOut.queue")
    public void onMessageFanOut(String msg) {
        System.out.println("msg fanOut.queue......[" + msg + "]");
    }

    @RabbitListener(queues = "fanOut.queue2")
    public void onMessageFanOut2(String msg) {
        System.out.println("msg fanOut.queue2......[" + msg + "]");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue"),
            exchange = @Exchange(name = "directExchange", type = ExchangeTypes.DIRECT),
            key = {"red"}
    ))
    public void onMessageDirectRed(String msg) {
        System.out.println("msg direct.queue......[" + msg + "]" + "red");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "direct.queue2"),
            exchange = @Exchange(name = "directExchange", type = ExchangeTypes.DIRECT),
            key = {"blue"}
    ))
    public void onMessageDirectBlue(String msg) {
        System.out.println("msg direct.queue2......[" + msg + "]" + "blue");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.news"),
            exchange = @Exchange(name = "topicExchange", type = ExchangeTypes.TOPIC),
            key = {"china.news"}
    ))
    public void onMessageTopicNews(String msg) {
        System.out.println("msg topic.queue.....[" + msg + "]" + "news");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.people"),
            exchange = @Exchange(name = "topicExchange", type = ExchangeTypes.TOPIC),
            key = {"china.people"}
    ))
    public void onMessageTopicPeople(String msg) {
        System.out.println("msg topic.queue.....[" + msg + "]" + "people");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "topic.china"),
            exchange = @Exchange(name = "topicExchange", type = ExchangeTypes.TOPIC),
            key = {"china.#"}
    ))
    public void onMessageTopicChina(String msg) {
        System.out.println("msg topic.queue.....[" + msg + "]" + "all");
    }

}
