package es.uv.andoni.process.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Value("${rabbitmq.queue.worker}")
  private String qWorker;

  @Value("${rabbitmq.routing.key.all}")
  private String kAll;

  @Value("${rabbitmq.exchange.name}")
  private String exchange;

  @SuppressWarnings("unused")
  @Autowired
  private AmqpAdmin amqpAdmin;

  @Bean
  public void Configure() {
    Queue qa = new Queue(this.qWorker, true, false, false);

    amqpAdmin.declareQueue(qa);

    TopicExchange e = new TopicExchange(this.exchange);
    amqpAdmin.declareExchange(e);

    amqpAdmin.declareBinding(BindingBuilder.bind(qa).to(e).with(kAll));
  }

  @Bean
  public Jackson2JsonMessageConverter converter() {
    return new Jackson2JsonMessageConverter();
  }
}
