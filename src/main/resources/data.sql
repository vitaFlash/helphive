-- Users
INSERT INTO users (id, email, name, password, role, active, registration_date) VALUES
  (1, 'admin@helphive.com', 'Admin User',       '$2b$10$DZmzqHj9W4bqS0H3gHCi8e8E09m1q2MZ6b1Eo9n3tYVxJ3rVj7rI6', 'ROLE_ADMIN',      TRUE, NOW()),
  (2, 'supervisor@helphive.com', 'Supervisor',  '$2b$10$7v5eC6S9GkLjZ3t1lKQwyeZ3mBz6kzH0yXo0cT0gQwQ3G5l8s2Hwe', 'ROLE_SUPERVISOR', TRUE, NOW()),
  (3, 'tech@helphive.com', 'Technician User',   '$2b$10$SneUrGgP0WmT4Xwdz6oJhuYJFBYmVYSN6bQD6XAPsqg1WYF5CKcWW', 'ROLE_TECHNICIAN', TRUE, NOW()),
  (4, 'user@helphive.com', 'Regular User',      '$2b$10$.I8L0pLckFleXUqTru.2s.Q5l.V97VN2I9wbmKQ9HvB0hKpLActt2', 'ROLE_USER',       TRUE, NOW())
ON DUPLICATE KEY UPDATE email = email;

-- Tickets
INSERT INTO tickets (id, title, description, priority, status, created_at, updated_at, creator_id, technician_id) VALUES
  (100, 'Cannot connect to VPN', 'User reports issues connecting to VPN.', 'HIGH',   'OPEN',         NOW(), NOW(), 4, 3),
  (101, 'Printer not working',   'Office printer in 3rd floor is jammed.', 'MEDIUM', 'IN_PROGRESS',  NOW(), NOW(), 4, 3)
ON DUPLICATE KEY UPDATE id = id;

-- Comments
INSERT INTO comments (id, ticket_id, author_id, body, created_at) VALUES
  (1000, 100, 4, 'Started having issues this morning.', NOW()),
  (1001, 100, 3, 'Checking VPN gateway logs.',          NOW())
ON DUPLICATE KEY UPDATE id = id;

-- Worklogs
INSERT INTO worklogs (id, ticket_id, technician_id, minutes, note, created_at) VALUES
  (2000, 100, 3, 30, 'Diagnosed VPN profile mismatch', NOW()),
  (2001, 101, 3, 15, 'Cleared paper jam',              NOW())
ON DUPLICATE KEY UPDATE id = id;




--admin@helphive.com → admin123
--supervisor@helphive.com → super123
--tech@helphive.com → tech123
--user@helphive.com → user123