package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "P_C_Tag")
public class Tag {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
		private int id;
		
		private String tags;
		
		@OneToOne
	    @JoinColumn(name = "post_id")
	    @JsonBackReference(value = "post-tag")
	    private Post post;
		
		@OneToOne
		@JoinColumn(name="Comment_id")
	    @JsonBackReference(value = "comment-tag")
		private Comments comments;
		
		@ManyToOne
		@JoinColumn(name="Label_id")
		private Label label;
		
		private Boolean identity;
		
		private String remark;
		
	public Tag() {}
	public Tag(String tag,Boolean identity,String remark) {
		this.tags=tag;
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
		return tags;
	}
	public void setTag(String tags) {
		this.tags = tags;
	}
	
	public Post getPost() {
		return post;
	}
	public void setPost(Post post) {
		this.post = post;
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
		return "Tag [id=" + id + ", tag=" + tags + ", post=" + post + ", comments=" + comments + ", identity=" + identity
				+ ", remark=" + remark + "]";
	}


}
