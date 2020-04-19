package ch2;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * 生产者示例
 * 使用自定义序列化器示例
 */
public class ProducerSelfSerializerDemo {

    public static final String brokerList = "localhost:9092";

    public static final String topic = "topic-demo";

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        // 指定broker连接地址，为kafka中配置的 advertised.listeners 参数
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
        // key的序列化方式不变
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 使用自定义序列化
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, CompanySerializer.class.getName());

        // 配置生产者客户端参数并创建KafkaProducer实例
        KafkaProducer<String, Company> producer = new KafkaProducer(props);
        Company company = Company.builder().name("hiddenkafka")
                .address("China").build();

        // 构建所需要发送的消息
        ProducerRecord<String, Company> record = new ProducerRecord(topic, company);
        producer.send(record).get();
    }

}
