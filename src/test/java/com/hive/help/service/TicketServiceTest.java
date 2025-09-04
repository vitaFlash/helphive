package com.hive.help.service;

import com.hive.help.dto.ticket.*;
import com.hive.help.mapper.TicketMapper;
import com.hive.help.model.*;
import com.hive.help.repository.TicketRepository;
import com.hive.help.repository.UserRepository;
import com.hive.help.service.impl.TicketServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Minimal, fast unit tests for TicketServiceImpl using Mockito.
 * - Avoids loading Spring context (no @SpringBootTest).
 * - Uses record DTO constructors and enum types.
 */
@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepo;

    @Mock
    private UserRepository userRepo;

    @Mock
    private TicketMapper mapper;

    @InjectMocks
    private TicketServiceImpl service;

    private TicketCreateDto createDto;
    private TicketUpdateDto updateDto;
    private TicketStatusUpdateDto statusUpdateDto;

    @BeforeEach
    void setUp() {
        // DTOs are records - construct with proper types (enums, etc.)
        createDto = new TicketCreateDto("Sample ticket", "Sample description", TicketPriority.HIGH);
        updateDto = new TicketUpdateDto("Updated title", "Updated description", TicketPriority.MEDIUM);
        statusUpdateDto = new TicketStatusUpdateDto(TicketStatus.IN_PROGRESS);
    }

    @Test
    void create_shouldSaveAndReturnDto() {
        // given
        long creatorId = 1L;
        com.hive.help.model.User creator = new com.hive.help.model.User();
        creator.setId(creatorId);

        Ticket entityFromMapper = new Ticket();
        entityFromMapper.setTitle(createDto.title());
        entityFromMapper.setDescription(createDto.description());
        entityFromMapper.setPriority(createDto.priority());
        // mapper.toEntity will be stubbed to return entityFromMapper

        TicketDto returnedDto = new TicketDto(
                10L,
                createDto.title(),
                createDto.description(),
                TicketStatus.OPEN,
                createDto.priority(),
                TicketColor.GREEN,
                LocalDateTime.now(),
                null,
                null,
                null,
                creatorId,
                null
        );

        when(userRepo.findById(creatorId)).thenReturn(Optional.of(creator));
        when(mapper.toEntity(createDto)).thenReturn(entityFromMapper);
        when(ticketRepo.save(any(Ticket.class))).thenAnswer(inv -> {
            Ticket t = inv.getArgument(0);
            t.setId(10L); // simulate DB assign
            return t;
        });
        when(mapper.toDto(any(Ticket.class))).thenReturn(returnedDto);

        // when
        TicketDto result = service.create(creatorId, createDto);

        // then
        assertNotNull(result);
        assertEquals(returnedDto.id(), result.id());
        assertEquals(createDto.title(), result.title());
        verify(ticketRepo, times(1)).save(any(Ticket.class));
        verify(mapper).toEntity(createDto);
        verify(mapper).toDto(any(Ticket.class));
    }

    @Test
    void updateStatus_shouldSetFirstRespondedWhenMovingToInProgress() {
        // given
        long ticketId = 2L;
        Ticket ticket = new Ticket();
        ticket.setId(ticketId);
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setFirstRespondedAt(null);
        ticket.setResolvedAt(null);

        when(ticketRepo.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(ticketRepo.save(any(Ticket.class))).thenAnswer(inv -> inv.getArgument(0));
        // mapper.toDto not essential for side-effect verification, but stub to avoid NPE
        when(mapper.toDto(any(Ticket.class))).thenReturn(new TicketDto(
                ticketId,
                "t",
                "d",
                TicketStatus.IN_PROGRESS,
                TicketPriority.MEDIUM,
                TicketColor.GREEN,
                LocalDateTime.now(),
                null,
                LocalDateTime.now(),
                null,
                null,
                null
        ));

        // when
        TicketDto result = service.updateStatus(ticketId, statusUpdateDto);

        // then - ensure entity got updated and firstRespondedAt populated
        assertNotNull(ticket.getFirstRespondedAt(), "firstRespondedAt should be set when status moves to IN_PROGRESS");
        assertEquals(TicketStatus.IN_PROGRESS, ticket.getStatus());
        assertNotNull(result);
        verify(ticketRepo).findById(ticketId);
        verify(ticketRepo).save(ticket);
        verify(mapper).toDto(ticket);
    }
}
