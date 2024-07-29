package com.example.demo.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="Tag_label")
public class Label {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String label;
	
	@OneToMany(mappedBy = "label")
	private List<Tag> tags;
	
	public Label() {}
	
	public Label(String label ) {
		this.label=label;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<Tag> getTag() {
		return tags;
	}
	
	@Override
	public String toString() {
		return "Label [id=" + id + ", label=" + label + ", tag=" + tags + "]";
	}


}
