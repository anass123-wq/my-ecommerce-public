package com.spring.supplierclientservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierClientService {

    @Autowired
    private SupplierClientRepository clientRepository;

    public SupplierClient getSupplierClientById(long id) {
        return clientRepository.findById(id);
    }
     public SupplierClient getSupplierClientByName(String name){
        return clientRepository.findByName(name);
     }

    //
    public SupplierClient saveSupplierClient(SupplierClient supplierClient) {
        return clientRepository.save(supplierClient);
    }

    public List<SupplierClient> getAllSupplierClients() {
        return clientRepository.findAll();
    }
}