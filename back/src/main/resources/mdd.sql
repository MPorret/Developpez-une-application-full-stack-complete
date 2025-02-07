CREATE TABLE `USERS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `email` VARCHAR(40) UNIQUE,
  `username` VARCHAR(40) UNIQUE,
  `password` VARCHAR(40),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);



CREATE TABLE `TOPICS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(40),
  `description` VARCHAR(200),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);



CREATE TABLE `SUBSCRIPTIONS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT,
  `topic_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `SUBSCRIPTIONS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`id`);



CREATE TABLE `POSTS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `title` VARCHAR(200),
  `content` VARCHAR(10000),
  `user_id` INT,
  `topic_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE `POSTS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `POSTS` ADD FOREIGN KEY (`topic_id`) REFERENCES `TOPICS` (`id`);



CREATE TABLE `COMMENTS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `content` VARCHAR(200),
  `user_id` INT,
  `post_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `COMMENTS` ADD FOREIGN KEY (`post_id`) REFERENCES `POSTS` (`id`);

-- Mock data for testing the app

  -- Insert 10 users
  INSERT INTO `USERS` (`email`, `username`, `password`) VALUES
  ('user1@example.com', 'user1', 'password1'),
  ('user2@example.com', 'user2', 'password2'),
  ('user3@example.com', 'user3', 'password3'),
  ('user4@example.com', 'user4', 'password4'),
  ('user5@example.com', 'user5', 'password5'),
  ('user6@example.com', 'user6', 'password6'),
  ('user7@example.com', 'user7', 'password7'),
  ('user8@example.com', 'user8', 'password8'),
  ('user9@example.com', 'user9', 'password9'),
  ('user10@example.com', 'user10', 'password10');

  -- Insert 30 topics
  INSERT INTO `TOPICS` (`name`, `description`) VALUES
  ('Artificial Intelligence', 'Discussions on AI and machine learning'),
  ('Cybersecurity', 'Topics related to cybersecurity and threats'),
  ('Data Science', 'Data analysis and visualization techniques'),
  ('Software Engineering', 'Best practices in software development'),
  ('Cloud Computing', 'Discussions on cloud services and infrastructure'),
  ('Blockchain', 'Blockchain technology and cryptocurrencies'),
  ('Quantum Computing', 'Advancements in quantum computing'),
  ('Internet of Things', 'IoT devices and applications'),
  ('Robotics', 'Robotics and automation technologies'),
  ('Virtual Reality', 'VR and AR technologies'),
  ('Programming Languages', 'Discussions on various programming languages'),
  ('Database Management', 'Database design and management'),
  ('Networking', 'Computer networking and protocols'),
  ('Operating Systems', 'Operating systems and kernel development'),
  ('Web Development', 'Frontend and backend web development'),
  ('Mobile App Development', 'Developing apps for mobile platforms'),
  ('Game Development', 'Game design and development'),
  ('Big Data', 'Handling and analyzing large datasets'),
  ('Machine Learning', 'Algorithms and models for machine learning'),
  ('Natural Language Processing', 'NLP techniques and applications'),
  ('Computer Vision', 'Image and video processing techniques'),
  ('Algorithm Design', 'Design and analysis of algorithms'),
  ('Computer Graphics', 'Graphics rendering and visualization'),
  ('Human-Computer Interaction', 'Designing user interfaces and experiences'),
  ('Data Mining', 'Extracting insights from data'),
  ('Parallel Computing', 'Parallel processing and high-performance computing'),
  ('Cryptography', 'Encryption techniques and security protocols'),
  ('Bioinformatics', 'Computational biology and genomics'),
  ('Embedded Systems', 'Design and development of embedded systems'),
  ('Computer Architecture', 'Design and optimization of computer hardware');

  -- Insert 40 posts
  INSERT INTO `POSTS` (`title`, `content`, `user_id`, `topic_id`) VALUES
  ('Introduction to AI', 'An overview of artificial intelligence.', 1, 1),
  ('Cybersecurity Basics', 'Basic concepts in cybersecurity.', 2, 2),
  ('Data Science Tools', 'Tools used in data science.', 3, 3),
  ('Software Engineering Principles', 'Principles of software engineering.', 4, 4),
  ('Cloud Computing Benefits', 'Benefits of using cloud services.', 5, 5),
  ('Blockchain Technology', 'Introduction to blockchain technology.', 6, 6),
  ('Quantum Computing Explained', 'Explanation of quantum computing.', 7, 7),
  ('IoT Devices', 'Common IoT devices and their uses.', 8, 8),
  ('Robotics in Industry', 'Use of robotics in industrial applications.', 9, 9),
  ('VR in Gaming', 'Use of VR in the gaming industry.', 10, 10),
  ('Popular Programming Languages', 'Overview of popular programming languages.', 1, 11),
  ('Database Design', 'Principles of database design.', 2, 12),
  ('Network Protocols', 'Common network protocols explained.', 3, 13),
  ('Operating Systems Overview', 'Overview of different operating systems.', 4, 14),
  ('Web Development Trends', 'Current trends in web development.', 5, 15),
  ('Mobile App Development Tools', 'Tools for mobile app development.', 6, 16),
  ('Game Development Engines', 'Popular game development engines.', 7, 17),
  ('Big Data Analytics', 'Analytics techniques for big data.', 8, 18),
  ('Machine Learning Algorithms', 'Common machine learning algorithms.', 9, 19),
  ('NLP Applications', 'Applications of natural language processing.', 10, 20),
  ('Computer Vision Techniques', 'Techniques used in computer vision.', 1, 21),
  ('Algorithm Design Principles', 'Principles of algorithm design.', 2, 22),
  ('Computer Graphics Basics', 'Basics of computer graphics.', 3, 23),
  ('HCI Design Principles', 'Principles of human-computer interaction design.', 4, 24),
  ('Data Mining Techniques', 'Techniques used in data mining.', 5, 25),
  ('Parallel Processing', 'Concepts in parallel processing.', 6, 26),
  ('Cryptography Basics', 'Basic concepts in cryptography.', 7, 27),
  ('Bioinformatics Tools', 'Tools used in bioinformatics.', 8, 28),
  ('Embedded Systems Design', 'Design principles for embedded systems.', 9, 29),
  ('Computer Architecture Overview', 'Overview of computer architecture.', 10, 30),
  ('AI in Healthcare', 'Applications of AI in healthcare.', 1, 1),
  ('Cybersecurity Threats', 'Common cybersecurity threats.', 2, 2),
  ('Data Science Projects', 'Ideas for data science projects.', 3, 3),
  ('Software Engineering Best Practices', 'Best practices in software engineering.', 4, 4),
  ('Cloud Computing Providers', 'Overview of cloud computing providers.', 5, 5),
  ('Blockchain Applications', 'Applications of blockchain technology.', 6, 6),
  ('Quantum Computing Research', 'Current research in quantum computing.', 7, 7),
  ('IoT Security', 'Security concerns in IoT devices.', 8, 8),
  ('Robotics in Healthcare', 'Use of robotics in healthcare.', 9, 9),
  ('VR in Education', 'Use of VR in education.', 10, 10),
  ('Programming Language Trends', 'Current trends in programming languages.', 1, 11),
  ('Database Management Systems', 'Overview of database management systems.', 2, 12),
  ('Network Security', 'Security concerns in computer networks.', 3, 13);

  -- Insert subscriptions for users
  INSERT INTO `SUBSCRIPTIONS` (`user_id`, `topic_id`) VALUES
  (1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
  (2, 6), (2, 7), (2, 8), (2, 9), (2, 10),
  (3, 11), (3, 12), (3, 13), (3, 14), (3, 15),
  (4, 16), (4, 17), (4, 18), (4, 19), (4, 20),
  (5, 21), (5, 22), (5, 23), (5, 24), (5, 25),
  (6, 26), (6, 27), (6, 28), (6, 29), (6, 30),
  (7, 1), (7, 2), (7, 3), (7, 4), (7, 5), (7, 6), (7, 7),
  (8, 8), (8, 9), (8, 10), (8, 11), (8, 12), (8, 13),
  (9, 14), (9, 15), (9, 16), (9, 17), (9, 18), (9, 19),
  (10, 20), (10, 21), (10, 22), (10, 23), (10, 24);

-- Insert comments for posts
INSERT INTO `COMMENTS` (`content`, `user_id`, `post_id`) VALUES
('Great introduction to AI!', 2, 1),
('Very informative post on cybersecurity.', 3, 2),
('Thanks for sharing these data science tools!', 4, 3),
('These principles are very helpful.', 5, 4),
('Cloud computing has indeed many benefits.', 6, 5),
('Blockchain is the future!', 7, 6),
('Quantum computing is fascinating.', 8, 7),
('IoT devices are becoming more common.', 9, 8),
('Robotics is revolutionizing industries.', 10, 9),
('VR is changing the gaming industry.', 1, 10),
('I love learning new programming languages.', 2, 11),
('Database design is crucial for any application.', 3, 12),
('Understanding network protocols is important.', 4, 13),
('This overview of operating systems is helpful.', 5, 14),
('Web development trends are always changing.', 6, 15),
('Mobile app development is a growing field.', 7, 16),
('Game development engines make it easier to create games.', 8, 17),
('Big data analytics can provide valuable insights.', 9, 18),
('Machine learning algorithms are powerful tools.', 10, 19),
('NLP has many practical applications.', 1, 20),
('Computer vision techniques are amazing.', 2, 21),
('Algorithm design principles are fundamental.', 3, 22),
('Computer graphics basics are important to understand.', 4, 23),
('HCI design principles improve user experience.', 5, 24),
('Data mining techniques can uncover hidden patterns.', 6, 25),
('Parallel processing can speed up computations.', 7, 26),
('Cryptography basics are essential for security.', 8, 27),
('Bioinformatics tools are advancing rapidly.', 9, 28),
('Embedded systems design is complex but rewarding.', 10, 29),
('Understanding computer architecture is crucial.', 1, 30),
('AI in healthcare has great potential.', 2, 31),
('Cybersecurity threats are always evolving.', 3, 32),
('Data science projects can be very impactful.', 4, 33),
('Software engineering best practices are important.', 5, 34),
('Cloud computing providers offer many services.', 6, 35),
('Blockchain applications are diverse.', 7, 36),
('Quantum computing research is exciting.', 8, 37),
('IoT security is a growing concern.', 9, 38),
('Robotics in healthcare is innovative.', 10, 39),
('VR in education can enhance learning.', 1, 40),
('Programming language trends are interesting to follow.', 2, 41),
('Database management systems are essential.', 3, 42),
('Network security is a critical issue.', 4, 43);
