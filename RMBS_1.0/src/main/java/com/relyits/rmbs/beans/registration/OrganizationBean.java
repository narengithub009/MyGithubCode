package com.relyits.rmbs.beans.registration;

import java.sql.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public class OrganizationBean {
	
	private Integer id;
	private String userName;
	private String initial;
	private String gender;
	private String firstName;
	private String lastName;
	private String password;
	private String dob;
	private String name;
	private String tinNo;
	private Date registeredDateTime;
	private Date lastLoginedDateTime;
	private Date updatedDateTime;
	private Integer daysCount;
	private Integer expiryDays;
	private Integer licenseValidDays;
	private String licenseKey;
	private ResourceBean resourceBean;
	private Integer noOfBranches;
	private String imageName;
   	private List<MultipartFile> imageData;
    private String imagePath;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTinNo() {
		return tinNo;
	}
	public void setTinNo(String tinNo) {
		this.tinNo = tinNo;
	}
	public Date getRegisteredDateTime() {
		return registeredDateTime;
	}
	public void setRegisteredDateTime(Date registeredDateTime) {
		this.registeredDateTime = registeredDateTime;
	}
	public Date getLastLoginedDateTime() {
		return lastLoginedDateTime;
	}
	public void setLastLoginedDateTime(Date lastLoginedDateTime) {
		this.lastLoginedDateTime = lastLoginedDateTime;
	}
	public Date getUpdatedDateTime() {
		return updatedDateTime;
	}
	public void setUpdatedDateTime(Date updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}
	public Integer getDaysCount() {
		return daysCount;
	}
	public void setDaysCount(Integer daysCount) {
		this.daysCount = daysCount;
	}
	public Integer getExpiryDays() {
		return expiryDays;
	}
	public void setExpiryDays(Integer expiryDays) {
		this.expiryDays = expiryDays;
	}
	public Integer getLicenseValidDays() {
		return licenseValidDays;
	}
	public void setLicenseValidDays(Integer licenseValidDays) {
		this.licenseValidDays = licenseValidDays;
	}
	public String getLicenseKey() {
		return licenseKey;
	}
	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}
	public ResourceBean getResourceBean() {
		return resourceBean;
	}
	public void setResourceBean(ResourceBean resourceBean) {
		this.resourceBean = resourceBean;
	}
	public Integer getNoOfBranches() {
		return noOfBranches;
	}
	public void setNoOfBranches(Integer noOfBranches) {
		this.noOfBranches = noOfBranches;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public List<MultipartFile> getImageData() {
		return imageData;
	}
	public void setImageData(List<MultipartFile> imageData) {
		this.imageData = imageData;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	
}
