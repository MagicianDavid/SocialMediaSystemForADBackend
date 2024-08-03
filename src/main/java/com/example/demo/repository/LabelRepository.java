package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Label;

public interface LabelRepository extends JpaRepository<Label, Integer>{
    Label findByLabel(String label);
}
