package com.javamultiplex.messanger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.javamultiplex.messanger.database.DatabaseClass;
import com.javamultiplex.messanger.model.Comment;
import com.javamultiplex.messanger.model.ErrorMessage;
import com.javamultiplex.messanger.model.Message;

public class CommentService {

	
	private Map<Long,Message> messages=DatabaseClass.getMessages();

	public List<Comment> getAllComments(long messageId){
		
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		return new ArrayList<>(comments.values());
		
	}
	
	
	public Comment getComment(long messageId,long commentId){
		
		ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "http://www.javamultiplex.com");
		Response response = Response.status(Status.NOT_FOUND).entity(errorMessage).build();
		Message message = messages.get(messageId);
		if(message==null){
			throw new WebApplicationException(response);
		}
		Map<Long, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if(comment==null){
			throw new WebApplicationException(response);
		}
		return comment;
	}
	
	
	public Comment addComment(long messageId, Comment comment){
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		comment.setId(comments.size()+1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment){
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		long id=comment.getId();
		if(id<=0){
			return null;
		}
		comments.put(id, comment);
		return comment;
	}
	
	public Comment removeComment(long messageId,long commentId){
		
		Map<Long,Comment> comments=messages.get(messageId).getComments();
		return comments.remove(commentId);
		
	}
	
}
