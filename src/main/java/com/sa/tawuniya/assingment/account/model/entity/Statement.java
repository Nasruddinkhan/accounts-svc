package com.sa.tawuniya.assingment.account.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Statement")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Statement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private Account account;

    @Column(name = "datefield", nullable = false)
    private String dateField;

    @Column(name = "amount", nullable = false)
    private String amount;
}
