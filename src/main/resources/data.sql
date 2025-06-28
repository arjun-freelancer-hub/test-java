-- Sample data for testing
INSERT INTO todos (title, description, completed, created_at) VALUES 
('Buy groceries', 'Milk, bread, eggs, and vegetables', false, CURRENT_TIMESTAMP),
('Complete project report', 'Finish the quarterly project report for the team', false, CURRENT_TIMESTAMP),
('Call dentist', 'Schedule annual checkup appointment', true, CURRENT_TIMESTAMP),
('Exercise', '30 minutes cardio workout', false, CURRENT_TIMESTAMP),
('Read book', 'Continue reading "Clean Code" by Robert Martin', false, CURRENT_TIMESTAMP),
('Pay bills', 'Electricity, water, and internet bills', true, CURRENT_TIMESTAMP),
('Plan weekend trip', 'Research destinations and book accommodation', false, CURRENT_TIMESTAMP),
('Learn Spring Boot', 'Complete the advanced Spring Boot course', false, CURRENT_TIMESTAMP); 