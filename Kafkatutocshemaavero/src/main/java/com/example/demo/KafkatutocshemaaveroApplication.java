package com.example.demo;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.example.demo.model.personne;

import io.confluent.kafka.serializers.KafkaAvroSerializer;

@SpringBootApplication
public class KafkatutocshemaaveroApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkatutocshemaaveroApplication.class, args);
		personne p;
		
	}

	@Bean
	public KafkaTemplate<String, personne> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	}
	
	@Bean
	  public Map<String, Object> producerConfigs() {
	    Map<String, Object> props = new HashMap<>();
	    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
	      "localhost:9092");
	    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
	      StringSerializer.class);
	    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
	    		KafkaAvroSerializer.class.getName());
	    props.put("schema.registry.url", "http://localhost:8081");
	    return props;
	  }

	  @Bean
	  public ProducerFactory<String, personne> producerFactory() {
	    return new DefaultKafkaProducerFactory<>(producerConfigs());
	  }

	  @Bean
	    public ApplicationRunner runner(KafkaTemplate<String, personne> template) {
	        return args -> {
	        	
	            personne p=personne.newBuilder().setFirstName("moi").setLastName("toi").build();
				template.send("mytopic", p);
	        };
	    }
}
