package model;

import java.util.List;

public class User {
	private long id;
	private String name;
	private String email;
	private String password;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean validate(List<String> message) {
		if (password.length() <6){
			message.add("Password demasiado corta");
			return false;
		}else if(!email.contains("@")) {
			message.add("Email incorrecto");
			return false;
		}
		return true;
	}

}
