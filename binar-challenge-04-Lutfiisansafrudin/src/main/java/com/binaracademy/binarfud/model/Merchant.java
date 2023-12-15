package com.binaracademy.binarfud.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "merchant")
public class Merchant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "merchant_code")
    private String merchantCode;

    @Column(name = "merchant_name", nullable = false, unique = true)
    private String merchantName;

    @Column(name = "merchant_location", nullable = false)
    private String merchantLocation;

    @Column(name = "open", nullable = false)
    private Boolean open;
}
