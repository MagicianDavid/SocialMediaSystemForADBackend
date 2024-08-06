package com.example.demo.dto;

import com.example.demo.model.PCMsg;
import com.example.demo.model.Tag;

public class PCMsgDTO {
	
	// just to check pagination
	// can add more information if required
    private Integer id;
    private String content;
	private String userName;
	private Integer userId;
	private Integer sourceId;
	private String timeStamp;
	private String status;
	private Tag tag;

    // Constructor, getters, and setters

    public PCMsgDTO(PCMsg pcmsg) {
        this.id = pcmsg.getId();
        this.content = pcmsg.getContent();
		this.userId = pcmsg.getUser().getId();
		this.userName = pcmsg.getUser().getName();
		this.status = pcmsg.getStatus();
		this.tag = pcmsg.getTag();
		this.sourceId = pcmsg.getSourceId();
		this.timeStamp = pcmsg.getTimeStamp();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSourceId() {
		return sourceId;
	}

	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Tag getTag() {
		return tag;
	}

	public void setTag(Tag tag) {
		this.tag = tag;
	}
}
