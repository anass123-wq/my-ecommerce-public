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
public class FilterController {

    @Autowired
    private FilterService filterService;
    @PostMapping("/{filterId}/addItem")
    public ResponseEntity<Filter> addItemToFilter(@PathVariable("filterId") Integer filterId, @RequestBody Long itemId) {
        if (!AuthorizationClient.doesPermissionExist("ROLE_VIEW_PRODUCTS")) {
            throw new AccessDeniedException("Permission ROLE_VIEW_PRODUCTS does not exist.");
        }System.out.println(itemId +" "+filterId);
        filterService.addItemToFilter(filterId, itemId);
        return ResponseEntity.ok().build();
    }
    // Get all filters
    @GetMapping("/filters")
    public List<Filter> getAllFilters() {
        if (!AuthorizationClient.doesPermissionExist("ROLE_VIEW_PRODUCTS")) {
            throw new AccessDeniedException("Permission ROLE_VIEW_PRODUCTS does not exist.");
        }
        return filterService.findAll();
    }

    // Get a filter by ID
    @GetMapping("/{id}")
    public ResponseEntity<Filter> getFilterById(@PathVariable Integer id) {
        if (!AuthorizationClient.doesPermissionExist("")) {
            throw new AccessDeniedException("Permission  does not exist.");
        }
        Optional<Filter> filter = filterService.findById(id);
        return filter.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_CREATE_FILTER')")
    // Create a new filter
    @PostMapping
    public Filter createFilter(@RequestBody Filter filter) {
        if (!AuthorizationClient.doesPermissionExist("ROLE_CREATE_FILTER")) {
            throw new AccessDeniedException("Permission ROLE_CREATE_FILTER does not exist.");
        }
        return filterService.save(filter);
    }

    // Update an existing filter by ID
    @PutMapping("/{id}")
    public ResponseEntity<Filter> updateFilter(@PathVariable Integer id, @RequestBody Filter filter) {
        if (!AuthorizationClient.doesPermissionExist("")) {
            throw new AccessDeniedException("Permission  does not exist.");
        }
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
        if (!AuthorizationClient.doesPermissionExist("ROLE_DELETE_FILTER")) {
            throw new AccessDeniedException("Permission ROLE_DELETE_FILTER does not exist.");
        }
        filterService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

