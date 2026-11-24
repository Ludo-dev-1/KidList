package com.christmaswishlist.infrastructure.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "children")
public class ChildEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "first_name", nullable = false, length = 100) private String firstName;
    @Column(nullable = false) private int age;
    protected ChildEntity() { }
    public ChildEntity(String firstName, int age) { this.firstName = firstName; this.age = age; }
    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public int getAge() { return age; }
    public void setId(Long id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setAge(int age) { this.age = age; }
}
