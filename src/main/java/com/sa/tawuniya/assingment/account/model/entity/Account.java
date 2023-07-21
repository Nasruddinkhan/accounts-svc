package com.sa.tawuniya.assingment.account.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Account")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Account  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "account_type", nullable = false)
    private String accountType;


    @Column(name = "account_number", nullable = false)
    private String accountNumber;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Statement> statements;
}
