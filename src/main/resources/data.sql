-- USERS
INSERT INTO users (id, email, name, password, role, active, registration_date)
VALUES
  (1, 'admin@helphive.com', 'Admin User', '{noop}admin123', 'ROLE_ADMIN', TRUE, NOW()),
  (2, 'supervisor@helphive.com', 'Supervisor User', '{noop}super123', 'ROLE_SUPERVISOR', TRUE, NOW()),
  (3, 'tech@helphive.com', 'Technician User', '{noop}tech123', 'ROLE_TECHNICIAN', TRUE, NOW()),
  (4, 'user@helphive.com', 'Regular User', '{noop}user123', 'ROLE_USER', TRUE, NOW());

-- TICKETS
INSERT INTO tickets (id, title, description, priority, status, created_at, updated_at, creator_id, technician_id)
VALUES
  (100, 'Cannot connect to VPN', 'User reports issues connecting to VPN.', 'HIGH', 'OPEN', NOW(), NOW(), 4, 3),
  (101, 'Printer not working', 'Office printer in 3rd floor is jammed.', 'MEDIUM', 'IN_PROGRESS', NOW(), NOW(), 4, 3);

-- COMMENTS
INSERT INTO comments (id, body, created_at, author_id, ticket_id)
VALUES
  (1000, 'We are looking into this VPN issue.', NOW(), 3, 100),
  (1001, 'Printer jam cleared, needs toner refill.', NOW(), 3, 101);

-- WORKLOGS
INSERT INTO worklogs (id, minutes, note, created_at, technician_id, ticket_id)
VALUES
  (2000, 30, 'Checked VPN server logs', NOW(), 3, 100),
  (2001, 45, 'Fixed printer jam and tested print queue', NOW(), 3, 101);
