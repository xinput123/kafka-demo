package ch1;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @Author: xinput
 * @Date: 2020-04-09 23:26
 */
public class ConsumerDemo01 {
    public static final String brokerList = "localhost:9092";

    public static final String topic = "topic-demo";

    public static final String groupId = "group.demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        // key反序列化
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // value反序列化
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        // 指定broker连接地址，为kafka中配置的 advertised.listeners 参数
        properties.put("bootstrap.servers", brokerList);
        // 设置消费组的名称
        properties.put("group.id", groupId);

        // 创建一个消费者客户端实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        // 订阅主题
        consumer.subscribe(Collections.singletonList(topic));

        // 循环消费消息
        while (true) {
            // timeout 阻塞时间，从kafka中取出1000毫秒的数据，有可能一次取出0到N条
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                System.out.println(record.value());
            }
        }
    }
}
