package com.hive.help.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "users",
        indexes = {
                @Index(name = "idx_users_email", columnList = "email", unique = true),
                @Index(name = "idx_users_role", columnList = "role")
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email @NotBlank
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Role role = Role.ROLE_USER;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @OneToMany(mappedBy = "creator")
    private List<Ticket> createdTickets = new ArrayList<>();

    @OneToMany(mappedBy = "assignedTechnician")
    private List<Ticket> assignedTickets = new ArrayList<>();

    public void addCreatedTicket(Ticket ticket) {
        createdTickets.add(ticket);
        ticket.setCreator(this);
    }
    public void assignTicket(Ticket ticket) {
        assignedTickets.add(ticket);
        ticket.setAssignedTechnician(this);
    }

}
