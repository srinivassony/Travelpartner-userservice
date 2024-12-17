package com.travelpartner.user_service.kafka;

import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class UserEmailProducer {

	private NewTopic topicName;

	private KafkaTemplate<?, ?> kafkaTemplate;

	public UserEmailProducer(NewTopic topicName, KafkaTemplate<?, ?> kafkaTemplate) {
		super();
		this.topicName = topicName;
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage( Map<String, Object> event) {
		System.out.println("user email evenet:" + " " + event.toString());
		
		// Extracting values from the map
	    String name = (String) event.get("name");
	    String id = (String) event.get("id");
	    String subject = (String) event.get("subject");
	    String status = (String) event.get("status");
	    String message = (String) event.get("message");
	    String toEmailId = (String) event.get("toEmailId");
//	    String topicNameInfo = (String) event.get("toEmailId");
	    
	    event.put("topicName", topicName.name());
	    
	    String topicNameInfo = (String) event.get("topicName");

	    // Example of printing the values
	    System.out.println("Name: " + name);
	    System.out.println("ID: " + id);
	    System.out.println("Subject: " + subject);
	    System.out.println("Status: " + status);
	    System.out.println("Message: " + message);
	    System.out.println("To Email ID: " + toEmailId);
	    System.out.println("topicName: " + topicNameInfo);
	    
	    
	
		Message<Map<String, Object>> messageInfo = MessageBuilder.withPayload(event)
				.setHeader(KafkaHeaders.TOPIC, topicName.name()).build();
		
		kafkaTemplate.send(messageInfo);
	}
}
