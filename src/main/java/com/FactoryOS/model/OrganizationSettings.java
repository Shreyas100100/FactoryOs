package com.FactoryOS.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "organization_settings")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id", nullable = false, unique = true)
    private Organization organization;

    @Column(name = "whatsapp_alerts_enabled", nullable = false)
    @Builder.Default
    private Boolean whatsappAlertsEnabled = true;

    @Column(name = "digest_time")
    @Builder.Default
    private String digestTime = "08:00";

    @Column(name = "low_stock_alert_enabled", nullable = false)
    @Builder.Default
    private Boolean lowStockAlertEnabled = true;
}