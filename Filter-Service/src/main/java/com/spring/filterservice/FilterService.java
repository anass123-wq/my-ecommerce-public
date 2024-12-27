package com.spring.filterservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FilterService {
    @Autowired
    private FilterRepository filterRepository;

    // Find all filters
    public List<Filter> findAll() {
        return filterRepository.findAll();
    }

    // Find a filter by ID
    public Optional<Filter> findById(Integer id) {
        return filterRepository.findById(id);
    }

    // Create or update a filter
    public Filter save(Filter filter) {
        return filterRepository.save(filter);
    }

    // Update a filter
    public Filter update(Integer id, Filter newFilter) {
        return filterRepository.findById(id)
                .map(filter -> {
                    filter.setName(newFilter.getName());
                    filter.setDescription(newFilter.getDescription());
                    return filterRepository.save(filter);
                })
                .orElseThrow(() -> new RuntimeException("Filter not found with id " + id));
    }

    // Delete a filter by ID
    public void delete(Integer id) {
        filterRepository.deleteById(id);
    }
    public Filter addItemToFilter(Integer filterId, Long newItem) {
        Optional<Filter> filterOptional = filterRepository.findById(filterId);
        if (filterOptional.isPresent()) {
            Filter filter = filterOptional.get();
            filter.getItems().add(newItem);
            return filterRepository.save(filter);
        }
        throw new RuntimeException("Filter not found");
    }

}
