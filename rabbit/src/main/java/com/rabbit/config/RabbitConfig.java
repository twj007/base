package com.rabbit.config;


import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/***
 **@project: base
 **@description: config of rabbit
 **@Author: twj
 **@Date: 2019/06/11
 **/
@Configuration
public class RabbitConfig {

    private static final Logger logger = LoggerFactory.getLogger(RabbitConfig.class);


    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    public static final String EXCHANGE_A = "my-mq-exchange_A";
    public static final String EXCHANGE_B = "my-mq-exchange_B";
    public static final String EXCHANGE_C = "my-mq-exchange_C";


    public static final String QUEUE_A = "QUEUE_A";
    public static final String QUEUE_B = "QUEUE_B";
    public static final String QUEUE_C = "QUEUE_C";

    public static final String ROUTINGKEY_A = "spring-boot-routingKey_A";
    public static final String ROUTINGKEY_B = "spring-boot-routingKey_B";
    public static final String ROUTINGKEY_C = "spring-boot-routingKey_C";


    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }



    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    //必须是prototype类型
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        return template;
    }

    /***
     * 广播方式
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(RabbitConfig.EXCHANGE_B);
    }

    /***
     * 交换机通信方式为直连
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange(RabbitConfig.EXCHANGE_A);
    }

    @Bean
    public Queue queue(){
        return new Queue(RabbitConfig.QUEUE_A, true);//持久化队列
    }

    @Bean
    public Queue queueB(){
        return new Queue(RabbitConfig.QUEUE_B, true);
    }

    @Bean
    public Queue queueC(){
        return new Queue(RabbitConfig.QUEUE_C, true);
    }

    /***
     * 绑定队列到交换机（默认）
     * @return
     */
    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitConfig.ROUTINGKEY_A);
    }

    @Bean
    public Binding bindingB(){
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    @Bean
    public Binding bindingC(){
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }

    /***
     * 第二种处理消息的方式
     * @return
     */
//    @Bean
//    public SimpleMessageListenerContainer simpleMessageListenerContainer(){
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
//        container.setQueues(queue(), queueB(), queueC());
//        container.setExposeListenerChannel(true);
//        container.setMaxConcurrentConsumers(10);
//        container.setConcurrentConsumers(1);
//        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        container.setMessageListener(new ChannelAwareMessageListener(){
//            @Override
//            public void onMessage(Message message, Channel channel) throws Exception {
//                /**通过basic.qos方法设置prefetch_count=1，这样RabbitMQ就会使得每个Consumer在同一个时间点最多处理一个Message，
//                 换句话说,在接收到该Consumer的ack前,它不会将新的Message分发给它 */
//                channel.basicQos(1);
//                byte[] body = message.getBody();
//               logger.info("【msg】:{}"+body);
//                /**为了保证永远不会丢失消息，RabbitMQ支持消息应答机制。
//                 当消费者接收到消息并完成任务后会往RabbitMQ服务器发送一条确认的命令，然后RabbitMQ才会将消息删除。*/
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            }
//        });
//        return container;
//    }
}
