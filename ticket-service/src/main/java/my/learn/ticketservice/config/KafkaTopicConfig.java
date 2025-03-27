package my.learn.ticketservice.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.topic.name.ticket.approved}")
    private String ticketApprovedTopic;
    @Value("${kafka.topic.name.ticket.cancelled}")
    private String ticketCancelledTopic;

    @Qualifier("ticketApprovedTopic")
    @Bean
    public NewTopic ticketApprovedTopic() {
        return TopicBuilder.name(ticketApprovedTopic).build();
    }

    @Qualifier("ticketCancelledTopic")
    @Bean
    public NewTopic ticketCancelledTopic() {
        return TopicBuilder.name(ticketCancelledTopic).build();
    }
}