package ch2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 生产者示例
 *
 * @Author: xinput
 * @Date: 2020-04-09 23:11
 */
public class ProducerDemo02 {

    public static final String brokerList = "localhost:9092";

    public static final String topic = "topic-demo";

    public static void main(String[] args) {
        Properties properties = initConfig();

        // 配置生产者客户端参数并创建KafkaProducer实例
        KafkaProducer<String, String> producer = new KafkaProducer(properties);

        // 构建所需要发送的消息
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, "my kafka");

        // 发送消息
        try {
            // 发后即忘: 只负责往Kafka中发送消息，而不管消息是否正确到达
//            producer.send(record);

            // 同步
            producer.send(record).get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 关闭生产者客户端示例
        producer.close();
    }

    public static Properties initConfig() {
        Properties props = new Properties();
        // 指定broker连接地址，为kafka中配置的 advertised.listeners 参数
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);

        // key序列化、value序列化 修改前
//        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // key序列化、value序列化 修改后
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 设置kafkaProducer对应的客户端id，默认值是""。如果客户端不设置，则KafkaProducer会自动生成一个非空字符串
        props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");

        // 实现自定义的分区
//        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DemoPartitioner.class.getName());

        // 实现拦截器
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, ProducerInterceptorPrefix.class.getName());

        // 实现多个拦截器，使用逗号隔开即可
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG,
                ProducerInterceptorPrefix.class.getName() + ","
                        + ProducerInterceptorPrefix.class.getName());

        // 设置重试次数 retries 默认是0
        props.put(ProducerConfig.RETRIES_CONFIG, 10);

        return props;
    }
}
