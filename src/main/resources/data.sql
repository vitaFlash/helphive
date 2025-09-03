-- USERS
INSERT INTO users (id, email, name, password, role, active, registration_date)
VALUES
  (1, 'admin@helphive.com', 'Admin User', '$2b$10$DQfDu/rPLqOIqXP0MrY2fOu03OIn/x8BQv4K3F.06LHtGGDZpKTRq', 'ROLE_ADMIN', TRUE, NOW()),
  (2, 'supervisor@helphive.com', 'Supervisor User', '$2b$10$8wPeKX4PYHjforAs0H5SX.I/CyAkypbPoNWVNve53zPKBhZcpcxxS', 'ROLE_SUPERVISOR', TRUE, NOW()),
  (3, 'tech@helphive.com', 'Technician User', '$2b$10$SneUrGgP0WmT4Xwdz6oJhuYJFBYmVYSN6bQD6XAPsqg1WYF5CKcWW', 'ROLE_TECHNICIAN', TRUE, NOW()),
  (4, 'user@helphive.com', 'Regular User', '$2b$10$.I8L0pLckFleXUqTru.2s.Q5l.V97VN2I9wbmKQ9HvB0hKpLActt2', 'ROLE_USER', TRUE, NOW())
ON DUPLICATE KEY UPDATE email = email;

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



--admin@helphive.com → admin123
--supervisor@helphive.com → super123
--tech@helphive.com → tech123
--user@helphive.com → user123