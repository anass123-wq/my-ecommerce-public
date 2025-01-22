package com.spring.securityservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.config.annotation.web.PortMapperDsl;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
    public class User implements UserDetails {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(nullable = false)
        private Integer id;

        @Column(nullable = false)
        private String fullName;

        @Column(unique = true, length = 100, nullable = false)
        private String email;

        @Column(nullable = false)
        private String password;

        @CreationTimestamp
        @Column(updatable = false, name = "created_at")
        private Date createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at")
        private Date updatedAt;
    @ManyToMany(fetch = FetchType.EAGER)  // EAGER to load roles immediately with user
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public User(Integer id, String username, String roles) {
    }

    @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return List.of();
        }

       @Override
        public String getUsername() {
            return email;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }

}