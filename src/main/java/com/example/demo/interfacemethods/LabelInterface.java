package com.example.demo.interfacemethods;

import com.example.demo.dto.LabelDTO;
import com.example.demo.model.Label;

import java.util.List;

public interface LabelInterface {
    List<Label> findAllLabels();
    List<LabelDTO> findAllLabelsDTO();
    Label findById(Integer id);
    Label findByLabel (String Label);

    // CRUD
    Label saveLabel(Label label);
    void deleteById(Integer id);
    void updatePenaltyScoreById(Integer id,Integer penaltyScore);
    Label updateLabel(Integer id, Label newLabel);
}
