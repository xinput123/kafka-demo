package ch1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 生产者示例
 *
 * @Author: xinput
 * @Date: 2020-04-09 23:11
 */
public class ProducerDemo01 {

    public static final String brokerList = "localhost:9092";

    public static final String topic = "topic-demo";

    public static void main(String[] args) {
        Properties properties = new Properties();
        // key序列化
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // 指定broker连接地址，为kafka中配置的 advertised.listeners 参数
        properties.put("bootstrap.servers", brokerList);

        // 配置生产者客户端参数并创建KafkaProducer实例
        KafkaProducer<String, String> producer = new KafkaProducer(properties);

        // 构建所需要发送的消息
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hi,kafka");

        // 发送消息
        try {
            producer.send(record);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 关闭生产者客户端示例
        producer.close();
    }
}
