package com.jwt.api.role;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jwt.api.supplier.Supplier;
import com.jwt.api.user.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "role_table")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String authority;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles")
    private List<User> users;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles")
    private List<Supplier> suppliers;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers = suppliers;
    }
}
