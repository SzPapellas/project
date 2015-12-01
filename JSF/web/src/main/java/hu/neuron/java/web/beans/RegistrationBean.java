package hu.neuron.java.web.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import hu.neuron.java.service.UserService;
import hu.neuron.java.service.vo.UserVO;

@ManagedBean(name = "registrationBean")
@ViewScoped
public class RegistrationBean implements Serializable {

	@ManagedProperty("#{userService}")

	private UserService userService;

	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String passwordConfirm;
	
	private String fullname;
	private String phone;
	private String gender;

	public void registration() {
		FacesContext currentInstance = FacesContext.getCurrentInstance();

		UserVO userVO = new UserVO();
		userVO.setUsername(username);
		userVO.setFullname(fullname);
		userVO.setGender(gender);
		userVO.setPhone(phone);

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		userVO.setPassword(bCryptPasswordEncoder.encode(password));

		if (password == null || passwordConfirm == null || !password.equals(passwordConfirm)) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erorr!", "Wrong passwords!");
			currentInstance.addMessage(null, facesMessage);
			return;
		}
		try {
			getUserService().registrationUser(userVO);
		} catch (Exception e) {
			FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erorr!", "Erorr in saveaction!");
			currentInstance.addMessage(null, facesMessage);
			e.printStackTrace();
		}

		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Succes!", "Succes registration!");
		currentInstance.addMessage(null, facesMessage);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
