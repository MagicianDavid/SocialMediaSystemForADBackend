package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Post {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	private String imageUrl;
	private String content;
	private String timeStamp;
	private Boolean visibility;
	private Boolean status;
	private int likes;
	
	@JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comments>comments;
	
	@ManyToOne
	@JoinColumn(name="User_id")
	private Employee user;
	
	@OneToOne(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "post-tag")
	private Tag tag;
	 
	public Post() {}
	public Post(String imageUrl,String content,String timeStamp,Boolean visibility,Boolean status,int likes) {
		this.imageUrl=imageUrl;
		this.content=content;
		this.timeStamp=timeStamp;
		this.visibility=visibility;
		this.status=status;
		this.likes=likes;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Boolean getVisibility() {
		return visibility;
	}
	public void setVisibility(Boolean visibility) {
		this.visibility = visibility;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	public Employee getuser_id() {
		return user;
	}
	public void setUser(Employee user) {
		this.user = user;
	}
	public Tag getTag() {
		return tag;
	}
	public void setTag(Tag tags) {
		this.tag = tags;
	}
	
//	@Override
//	public String toString() {
//		return "Post [id=" + id + ", imageUrl=" + imageUrl + ", content=" + content + ", timeStamp=" + timeStamp
//				+ ", visibility=" + visibility + ", status=" + status + ", likes=" + likes + ", comments=" + comments
//				+ ", user=" + user + ", tag=" + tag + "]";
//	}

}
