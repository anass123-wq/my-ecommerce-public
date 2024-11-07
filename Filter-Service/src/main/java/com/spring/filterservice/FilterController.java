package com.spring.filterservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/Filters")
public class FilterController {
    @Autowired
    private FilterService filterService;
    @PostMapping("/{filterId}/addItem")
    public ResponseEntity<Filter> addItemToFilter(@PathVariable Integer filterId, @RequestBody Long newItem) {
        Filter updatedFilter = filterService.addItemToFilter(filterId, newItem);
        return ResponseEntity.ok(updatedFilter);
    }
    // Get all filters
    @GetMapping("/filters")
    public List<Filter> getAllFilters() {
        return filterService.findAll();
    }

    // Get a filter by ID
    @GetMapping("/{id}")
    public ResponseEntity<Filter> getFilterById(@PathVariable Integer id) {
        Optional<Filter> filter = filterService.findById(id);
        return filter.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new filter
    @PostMapping
    public Filter createFilter(@RequestBody Filter filter) {
        return filterService.save(filter);
    }

    // Update an existing filter by ID
    @PutMapping("/{id}")
    public ResponseEntity<Filter> updateFilter(@PathVariable Integer id, @RequestBody Filter filter) {
        try {
            Filter updatedFilter = filterService.update(id, filter);
            return ResponseEntity.ok(updatedFilter);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a filter by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Integer id) {
        filterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

