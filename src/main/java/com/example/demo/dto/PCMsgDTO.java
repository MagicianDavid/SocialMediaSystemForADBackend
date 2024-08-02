package com.example.demo.dto;

import com.example.demo.model.PCMsg;

public class PCMsgDTO {
	
	// just to check pagination
	// can add more information if required
    private Integer id;
    private String content;

    // Constructor, getters, and setters

    public PCMsgDTO(PCMsg pcmsg) {
        this.id = pcmsg.getId();
        this.content = pcmsg.getContent();
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

}
