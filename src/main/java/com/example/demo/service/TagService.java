package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.interfacemethods.TagInterface;
import com.example.demo.model.Tag;
import com.example.demo.repository.TagRepository;

@Service
@Transactional
public class TagService implements TagInterface{

    @Autowired
    private TagRepository tagRepository;	
	
	@Override
	public Tag saveTag(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public Tag getTagById(Integer id) {
		return tagRepository.findById(id).orElse(null);
	}

}
