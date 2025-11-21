package com.gemini.roadmap2.models;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Table(name = "users")
@Entity
public class User  implements UserDetails {
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
    


    /////// functions of userdetails interface ////////
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
   @Override
    public String getUsername() {
        return email;
    }
   public Integer getId() {
    return id;
   }
   public void setId(Integer id) {
    this.id = id;
   }
   public String getFullName() {
    return fullName;
   }
   public void setFullName(String fullName) {
    this.fullName = fullName;
   }
   public String getEmail() {
    return email;
   }
   public void setEmail(String email) {
    this.email = email;
   }

   
   public void setUsername(String email) {
    this.email = email;
   }
   public String getPassword() {
    return password;
   }
   public void setPassword(String password) {
    this.password = password;
   }
   public Date getCreatedAt() {
    return createdAt;
   }
   public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
   }
   public Date getUpdatedAt() {
    return updatedAt;
   }
   public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
   }

}
