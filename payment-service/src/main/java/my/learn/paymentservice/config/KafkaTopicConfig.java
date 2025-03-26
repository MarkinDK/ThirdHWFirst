package my.learn.paymentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Value("${kafka.topic.name.payment.approved}")
    private String paymentApprovedTopic;
    @Value("${kafka.topic.name.payment.rejected}")
    private String paymentRejectedTopic;

    @Qualifier("paymentApprovedTopic")
    @Bean
    public NewTopic paymentApprovedTopic() {
        return TopicBuilder.name(paymentApprovedTopic).build();
    }

    @Qualifier("paymentRejectedTopic")
    @Bean
    public NewTopic paymentRejectedTopic() {
        return TopicBuilder.name(paymentApprovedTopic).build();
    }
}
