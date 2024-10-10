package com.planepockets.proton.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name="email_id")
	@Email(message = "Email is not valid")
	private String loginId;

	@Column(name = "full_name")
	@NotBlank(message = "Fullname shouldn't be blank")
	private String fullName;

	@Column(name = "password")
	@NotBlank(message = "Password shouldn't be blank")
	private String password;

	@Column(name = "contact_number")
	@NotBlank(message = "Contact Number can't be blank")
	private String contactNumber;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Set<Role> role;

	@Column(name = "account_created_at")
	private String createdAt;

	public @NotBlank(message = "Email Id shouldn't be blank") @Email String getLoginId() {
		return loginId;
	}

	public void setLoginId(@NotBlank(message = "Email Id shouldn't be blank") @Email String loginId) {
		this.loginId = loginId;
	}

	public @NotBlank(message = "Fullname shouldn't be blank") String getFullName() {
		return fullName;
	}

	public void setFullName(@NotBlank(message = "Fullname shouldn't be blank") String fullName) {
		this.fullName = fullName;
	}

	public @NotBlank(message = "Password shouldn't be blank") String getPassword() {
		return password;
	}

	public void setPassword(@NotBlank(message = "Password shouldn't be blank") String password) {
		this.password = password;
	}

	public @NotBlank(message = "Contact Number can't be blank") String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(@NotBlank(message = "Contact Number can't be blank") String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public Set<Role> getRole() {
		return role;
	}

	public void setRole(Set<Role> role) {
		this.role = role;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "User{" +
				"loginId='" + loginId + '\'' +
				", fullName='" + fullName + '\'' +
				", password='" + password + '\'' +
				", contactNumber='" + contactNumber + '\'' +
				", role=" + role +
				", createdAt=" + createdAt +
				'}';
	}

	public User() {

	}

	public User(String loginId, String fullName, String password, String contactNumber, Set<Role> role, String createdAt) {
		this.loginId = loginId;
		this.fullName = fullName;
		this.password = password;
		this.contactNumber = contactNumber;
		this.role = role;
		this.createdAt = createdAt;
	}
}
