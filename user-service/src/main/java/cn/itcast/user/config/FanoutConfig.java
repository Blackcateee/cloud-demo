package cn.itcast.user.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

    @Bean
    public FanoutExchange fanOutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Queue fanOutQueue() {
        return new Queue("fanOut.queue");
    }

    @Bean
    public Queue fanOutQueue2() {
        return new Queue("fanOut.queue2");
    }

    @Bean
    public Binding fanOutBinding(Queue fanOutQueue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanOutQueue).to(fanoutExchange);
    }

    @Bean
    public Binding fanOutBinding2(Queue fanOutQueue2, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(fanOutQueue2).to(fanoutExchange);
    }
}
