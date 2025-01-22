package com.spring.purchaseorderservice.service;

import com.spring.purchaseorderservice.model.LineReturn;
import com.spring.purchaseorderservice.repository.LineReturnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReturnService {
    @Autowired
    private LineReturnRepository  lineReturnRepository;
    public List<LineReturn> getAllLineReturn() {
        return lineReturnRepository.findAll();
    }
    public LineReturn getLineReturnById(int id) {
        return lineReturnRepository.findById(id).get();
    }
    public LineReturn addLineReturn(LineReturn lineReturn) {
        return lineReturnRepository.save(lineReturn);
    }
    public LineReturn updateLineReturn(LineReturn lineReturn) {
        return lineReturnRepository.save(lineReturn);
    }
    public void deleteLineReturnById(int id) {
        lineReturnRepository.deleteById(id);
    }
}
