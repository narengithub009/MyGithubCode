package com.java.springboot.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class TopicService {
	
	
	private List<Topic> topics= new ArrayList<>(Arrays.asList(
			new Topic(1,"springboot","spring boot description"),
			new Topic(2, "javascript", "Javascript Description"),
			new Topic(3,"Angular","Angular description")
			));
	
	public List<Topic> getAllTopics(){
		
		return topics;
	}
	
	public Topic getTopic(int id) {
		return topics.stream().filter(t-> t.getId()==id).findFirst().get();
	}

	public void addTopic(Topic topic) {
		topics.add(topic);
		
	}

	public void updateTopic(int id, Topic topic) {

		for(int i=0;i<=topics.size();i++) {
			Topic t=topics.get(i);
			if(t.getId()==id) {
				topics.set(i, topic);
				return;
			}
		}
		
	}

	public void deleteTopic(int id) {

		topics.removeIf(t->t.getId()==id);
		
	}
}
