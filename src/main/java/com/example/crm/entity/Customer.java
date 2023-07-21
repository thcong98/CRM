package com.example.crm.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Nonnull
    @Column(name = "code", nullable = false)
    private Long code;

    @NotBlank(message = "Subject cannot be blank")
    @Nonnull
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotBlank(message = "Subject cannot be blank")
    @Nonnull
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @NotBlank(message = "Subject cannot be blank")
    @Nonnull
    @Column(name = "email", nullable = false)
    // , unique = true
    private String email;

    @NotBlank(message = "Subject cannot be blank")
    @Nonnull
    @Column(name = "phoneNumber", nullable = false)
    // , unique = true
    private String phoneNumber;
    
    @Nonnull
    @JsonFormat(pattern = "dd-MMM-yyy", shape = JsonFormat.Shape.STRING)
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @NotBlank(message = "Subject cannot be blank")
    @Nonnull
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "Subject cannot be blank")
    @Nonnull
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotBlank(message = "Subject cannot be blank")
    @Nonnull
    @Column(name = "typeId", nullable = false)
    private String typeId;
}