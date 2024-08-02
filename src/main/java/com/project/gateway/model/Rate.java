package com.project.gateway.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "rate")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id")
    private Long id;

    @Column(name = "currency")
    private String currency;

    @Column(name = "rate",precision = 38, scale = 10)
    private BigDecimal rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "history_id")
    private History historyId;
}
