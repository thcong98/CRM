package com.example.crm.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

//import javax.annotation.Nonnull;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "customer")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    @Nonnull
    @Column(name = "code", nullable = false)
    private Long code;

    @NotBlank(message = "firstName cannot be blank")
//    @Nonnull
    @Column(name = "firstName", nullable = false)
    private String firstName;

    @NotBlank(message = "lastName cannot be blank")
//    @Nonnull
    @Column(name = "lastName", nullable = false)
    private String lastName;

    @NotBlank(message = "email cannot be blank")
//    @Nonnull
    @Column(name = "email", nullable = false) // , unique = true
    private String email;

    @NotBlank(message = "phoneNumber cannot be blank")
//    @Nonnull
    @Column(name = "phoneNumber", nullable = false)// , unique = true
    private String phoneNumber;
    
//    @Nonnull
    @JsonFormat(pattern = "dd-MMM-yyy", shape = JsonFormat.Shape.STRING)
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @NotBlank(message = "address cannot be blank")
//    @Nonnull
    @Column(name = "address", nullable = false)
    private String address;

    @NotBlank(message = "gender cannot be blank")
//    @Nonnull
    @Column(name = "gender", nullable = false)
    private String gender;

    @NotBlank(message = "typeId cannot be blank")
//    @Nonnull
    @Column(name = "typeId", nullable = false)
    private String typeId;
}
