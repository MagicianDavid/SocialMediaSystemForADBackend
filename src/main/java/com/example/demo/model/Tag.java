package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "P_C_Tag")
public class Tag {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		
		private String tag;
		
		@ManyToOne
		@JoinColumn(name="tags")
		private Post post;
		
		@ManyToOne
		@JoinColumn(name="Comment_id")
		private Comments comments;
		
		@ManyToOne
		@JoinColumn(name="Label_id")
		private Label label;
		
		private Boolean identity;
		
		private String remark;
		
	public Tag() {}
	public Tag(String tag,Boolean identity,String remark) {
		this.tag=tag;
		this.identity=identity;
		this.remark=remark;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Post getPost() {
		return post;
	}
	
	public Comments getComments() {
		return comments;
	}
	
	public Boolean getIdentity() {
		return identity;
	}
	public void setIdentity(Boolean identity) {
		this.identity = identity;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "Tag [id=" + id + ", tag=" + tag + ", post=" + post + ", comments=" + comments + ", identity=" + identity
				+ ", remark=" + remark + "]";
	}


}
