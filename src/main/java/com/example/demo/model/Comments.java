package com.example.demo.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Comments{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@JoinColumn(name="Source_id")
	private int sourceId;
	private String content;
	@JoinColumn(name="User_Id")
	private int userId;
	private String timeStamp;
	private String likes;
	private Boolean status;
	
	@ManyToOne
    @JoinColumn(name = "post_id")
	private Post post;
	
	@OneToOne(mappedBy="comments", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "comment-tag")
	private Tag tags;
	
	public Comments() {}
	
	public Comments(int SourceId,String content,int userId,String timeStamp,String likes,Boolean status) {
		this.sourceId=SourceId;
		this.content=content;
		this.userId=userId;
		this.timeStamp=timeStamp;
		this.likes=likes;
		this.status=status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSourceId() {
		return sourceId;
	}
	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getUserId() {
		return userId;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
	}
	
	public Tag getTag() {
		return tags;
	}
	public void setTag(Tag tags) {
		this.tags = tags;
	}
	
//	@Override
//	public String toString() {
//		return "Comments [id=" + id + ", sourceId=" + sourceId + ", content=" + content + ", userId=" + userId
//				+ ", timeStamp=" + timeStamp + ", likes=" + likes + ", status=" + status + ", post=" + post + ", tag=" + tags
//				+ "]";
//	}


}
