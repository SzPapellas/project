package hu.neuron.java.web.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import hu.neuron.java.service.MessageService;
import hu.neuron.java.service.UserService;
import hu.neuron.java.service.vo.UserVO;

@ManagedBean
@ViewScoped
public class MessagesBean implements Serializable {

	@ManagedProperty(value = "#{messageService}")
	MessageService messageService;

	@ManagedProperty(value = "#{userService}")
	UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	private static final long serialVersionUID = 1L;

	List<UserVO> users;

	@PostConstruct
	public void init() {
		users = userService.getUsers();

	}

	public void setUsers(List<UserVO> users) {

		this.users = users;

	}

	public List<UserVO> getUsers() {
		return users;
	}

}
