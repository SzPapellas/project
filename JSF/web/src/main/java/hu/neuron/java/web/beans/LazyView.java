package hu.neuron.java.web.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.LazyDataModel;
import org.springframework.data.domain.Sort;

import hu.neuron.java.service.UserService;
import hu.neuron.java.service.vo.UserVO;

@ManagedBean(name = "dtLazyView")
@ViewScoped
public class LazyView implements Serializable {

	private static final long serialVersionUID = 1L;

	private LazyDataModel<UserVO> lazyModel;

	private UserVO selectedUser;

	private List<String> genders = Arrays.asList("male", "female", "other");
	private List<String> roles = Arrays.asList("User", "Manager", "Admin");

	private Long selectedUserToDelete;

	@ManagedProperty("#{userService}")
	private UserService service;

	@ManagedProperty("#{registrationBean}")
	private RegistrationBean registration;

	@PostConstruct
	public void init() {
		lazyModel = new LazyUserDataModel(service.getAllUser(new Sort(Sort.Direction.ASC, "id")));
	}

	public LazyDataModel<UserVO> getLazyModel() {
		return lazyModel;
	}

	public UserVO getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(UserVO selectedUser) {
		this.selectedUser = selectedUser;
	}

	public void setService(UserService service) {
		this.service = service;
	}

	public RegistrationBean getRegistration() {
		return registration;
	}

	public void setRegistration(RegistrationBean registration) {
		this.registration = registration;
	}

	public void onRowEdit(RowEditEvent event) {
		UserVO updatedUser = ((UserVO) event.getObject());
		service.saveUser(updatedUser);
		createFacesMessage("User edited!", "Id: " + updatedUser.getId() + ", Username: " + updatedUser.getUsername());
	}

	public void onRowCancel(RowEditEvent event) {
		createFacesMessage("Edit Cancelled!", "");
	}

	public void deleteUser() {
		service.removeById(selectedUserToDelete);
		init();
		selectedUser = null;
		createFacesMessage("User Deleted!", "");

	}

	public void addNewUser() {

	}

	public void createFacesMessage(String summary, String detail) {

		FacesMessage msg = new FacesMessage(summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public List<String> getGenders() {
		return genders;
	}

	public Long getSelectedUserToDelete() {
		return selectedUserToDelete;
	}

	public void setSelectedUserToDelete(Long selectedUserToDelete) {
		System.out.println("selected = " + selectedUserToDelete);
		this.selectedUserToDelete = selectedUserToDelete;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void validate() {

		boolean success = false;
		if (success) {

			RequestContext rc = RequestContext.getCurrentInstance();
			rc.execute("PF('newUserDialog').hide()");
			init();
		}
	}

}