package com.spring.filterservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@PreAuthorize("hasAuthority('ADMIN')")
@CrossOrigin(origins = {"http://localhost:3004" ,"http://localhost:3006" ,"http://localhost:3007" ,"http://localhost:3008", "http://localhost:3003","http://localhost:3000","http://localhost:3005"})
@RestController
@RequestMapping("/api/Filters")
public class FilterController{

    @Autowired
    private FilterService filterService;
    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/{filterId}/addItem")
    public ResponseEntity<Filter> addItemToFilter(@PathVariable("filterId") Integer filterId, @RequestBody Long itemId) {
     System.out.println(itemId +" "+filterId);
        filterService.addItemToFilter(filterId, itemId);
        return ResponseEntity.ok().build();
    }
    // Get all filters
    @PreAuthorize("hasAuthority('ROLE_VIEW_FILTER')")
    @GetMapping("/filters")
    public List<Filter> getAllFilters() {
        return filterService.findAll();
    }

    // Get a filter by ID
    @PreAuthorize("hasAuthority({'GET_ID' })")
    @GetMapping("/{id}")
    public ResponseEntity<Filter> getFilterById(@PathVariable Integer id) {
        Optional<Filter> filter = filterService.findById(id);
        return filter.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority({'CREATE','ROLE_CREATE_FILTER'})")
    // Create a new filter
    @PostMapping
    public Filter createFilter(@RequestBody Filter filter) {
        return filterService.save(filter);
    }

    // Update an existing filter by ID
    @PreAuthorize("hasAuthority({'UPDATE' }) ")
    @PutMapping("/{id}")
    public ResponseEntity<Filter> updateFilter(@PathVariable Integer id, @RequestBody Filter filter) {
        try {
            Filter updatedFilter = filterService.update(id, filter);
            return ResponseEntity.ok(updatedFilter);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasAuthority('ROLE_DELETE_FILTER')")
    // Delete a filter by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFilter(@PathVariable Integer id) {
        filterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

