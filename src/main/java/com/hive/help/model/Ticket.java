package com.hive.help.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "tickets",
        indexes = {
                @Index(name = "idx_tickets_status", columnList = "status"),
                @Index(name = "idx_tickets_priority", columnList = "priority"),
                @Index(name = "idx_tickets_creator", columnList = "creator_id"),
                @Index(name = "idx_tickets_technician", columnList = "technician_id"),
                @Index(name = "idx_tickets_created_at", columnList = "createdAt")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Column(nullable = false, length = 255)
    private String title;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private TicketStatus status = TicketStatus.OPEN;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private TicketPriority priority = TicketPriority.MEDIUM;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime firstRespondedAt;
    private LocalDateTime resolvedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private User assignedTechnician;

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WorkLog> workLogs = new ArrayList<>();

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Transient
    public TicketColor getColor() {
        if (createdAt == null) return TicketColor.GREEN;

        long hours = java.time.Duration.between(createdAt, LocalDateTime.now()).toHours();
        if (hours < 24) return TicketColor.GREEN;
        else if (hours <= 48) return TicketColor.ORANGE;
        else return TicketColor.RED;
    }
}


