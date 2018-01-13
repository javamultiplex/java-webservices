package com.javamultiplex.messanger.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.javamultiplex.messanger.bean.MessageFilterBean;
import com.javamultiplex.messanger.model.Message;
import com.javamultiplex.messanger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(value={MediaType.APPLICATION_JSON,MediaType.TEXT_XML})
public class MessageResource {

	MessageService messageService = new MessageService();

	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {

		if (filterBean.getYear() > 0)
			return messageService.getMessageByYear(filterBean.getYear());
		if (filterBean.getStart() > 0 && filterBean.getSize() > 0)
			return messageService.getMassageByPagination(filterBean.getStart(), filterBean.getSize());
		return messageService.getAllMessages();
	}

	@GET
	@Path("/{messageId}")
	public Message getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(id);
		message.addLink(getUriForSelf(message, uriInfo), "self");
		message.addLink(getUriForProfile(message, uriInfo), "profile");
		message.addLink(getUriForComments(message, uriInfo), "comments");
		return message;
	}

	private String getUriForComments(Message message, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
				   .path(MessageResource.class)
				   .path(MessageResource.class, "getCommentResource")
				   .path(CommentResource.class)
				   .resolveTemplate("messageId", message.getId())
				   .build()
				   .toString();
	}

	private String getUriForProfile(Message message, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
				   .path(MessageResource.class)
				   .path(ProfileResource.class)
				   .path(message.getAuthor())
				   .build()
				   .toString();
	}

	private String getUriForSelf(Message message, UriInfo uriInfo) {
		return uriInfo.getBaseUriBuilder()
			   .path(MessageResource.class)
			   .path(Long.toString(message.getId()))
			   .build()
			   .toString();
	}

	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo){
		Message newMessage=messageService.addMessage(message);
		String newMessageId=String.valueOf(newMessage.getId());
		URI uri=uriInfo.getAbsolutePathBuilder().path(newMessageId).build();
		return Response.created(uri)
				.entity(newMessage).build();
	}

	@PUT
	@Path("/{messageId}")
	public Message updateMessage(@PathParam("messageId") long id, Message message) {
		message.setId(id);
		return messageService.updateMessage(message);
	}

	@DELETE
	@Path("/{messageId}")
	public void deleteMessage(@PathParam("messageId") long id) {
		messageService.removeMessage(id);
	}
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource(){
		return new CommentResource();
	}
}
