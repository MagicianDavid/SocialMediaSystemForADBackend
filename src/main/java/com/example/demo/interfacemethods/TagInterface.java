package com.example.demo.interfacemethods;

import com.example.demo.model.Tag;

public interface TagInterface {
	Tag saveTag(Tag tag);
	Tag getTagById(Integer id);
}
