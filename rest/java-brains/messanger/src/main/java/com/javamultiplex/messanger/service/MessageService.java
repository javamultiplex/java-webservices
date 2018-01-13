package com.javamultiplex.messanger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.javamultiplex.messanger.database.DatabaseClass;
import com.javamultiplex.messanger.exception.DataNotFoundException;
import com.javamultiplex.messanger.model.Message;

public class MessageService {

	private static Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {

		messages.put(1L, new Message(1, "Learning REST WebServices", "Rohit Agarwal"));
		messages.put(2L, new Message(2, "Learning Soap WebServices", "Rahul Agarwal"));

	}

	public List<Message> getAllMessages() {

		return new ArrayList<>(messages.values());
	}

	public List<Message> getMessageByYear(int year) {

		List<Message> list = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		for (Message message : messages.values()) {
			calendar.setTime(message.getCreated());
			if (calendar.get(Calendar.YEAR) == year) {
				list.add(message);
			}
		}

		return list;
	}

	public List<Message> getMassageByPagination(int start, int size) {
		List<Message> list = new ArrayList<>(messages.values());
		if ((start + size) > list.size()) {
			return new ArrayList<>();
		}
		return list.subList(start, start + size);
	}

	public Message getMessage(long id) {
		Message message=messages.get(id);
		if(message==null){
			throw new DataNotFoundException("Message with id = "+id+" Not found.");
		}
		return message;
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}

	public Message removeMessage(long id) {

		return messages.remove(id);
	}

}
