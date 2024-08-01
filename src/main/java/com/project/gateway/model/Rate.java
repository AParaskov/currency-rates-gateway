package com.project.gateway.model;

import jakarta.persistence.*;
import lombok.*;

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
