CREATE TABLE `customers` (
    `customer_id` int NOT NULL AUTO_INCREMENT,
    `name` varchar(64) NOT NULL,
    `email` varchar(64) NOT NULL,
    `mobile` varchar(24) NOT NULL,
    `password` varchar(512) NOT NULL,
    `role` varchar(32) NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(`customer_id`));

INSERT 
    INTO `customers` (`name`, `email`, `mobile`, `password`, `role`) 
    VALUES ('John', 'john@example.com', '123456789', '$2y$12$oRRbkNfwuR8ug4MlzH5FOeui.//1mkd.RsOAJMbykTSupVy.x/vb2', 'admin');


CREATE TABLE `accounts` (
    `customer_id` int NOT NULL,
    `account_number` int NOT NULL,
    `account_type` varchar(64) NOT NULL,
    `branch_address` varchar(128) NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  
    PRIMARY KEY (`account_number`),
    KEY `customer_id` (`customer_id`),
    CONSTRAINT `customer_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE);

INSERT 
    INTO `accounts` (`customer_id`, `account_number`, `account_type`, `branch_address`)
    VALUES (1, 1865764534, 'Savings', '123 Main Street, New York');


CREATE TABLE `account_transactions` (
    `transaction_id` varchar(256) NOT NULL,
    `account_number` int NOT NULL,
    `customer_id` int NOT NULL,
    `transaction_date` date NOT NULL,
    `transaction_summary` varchar(128) NOT NULL,
    `transaction_type` varchar(64) NOT NULL,
    `transaction_amount` int NOT NULL,
    `closing_balance` int NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  
    PRIMARY KEY (`transaction_id`),
    KEY `customer_id` (`customer_id`),
    KEY `account_number` (`account_number`),
    CONSTRAINT `accounts_ibfk_2` FOREIGN KEY (`account_number`) REFERENCES `accounts` (`account_number`) ON DELETE CASCADE,
    CONSTRAINT `acct_user_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE);

INSERT 
    INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_date`, `transaction_summary`, `transaction_type`, `transaction_amount`, `closing_balance`, `created_at`)  
    VALUES (UUID(), 1865764534, 1, DATE_SUB(CURDATE(), INTERVAL 7 DAY), 'Coffee Shop', 'Withdrawal', 30, 34500, DATE_SUB(CURDATE(), INTERVAL 7 DAY));
INSERT 
    INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_date`, `transaction_summary`, `transaction_type`, `transaction_amount`, `closing_balance`, `created_at`)  
    VALUES (UUID(), 1865764534, 1, DATE_SUB(CURDATE(), INTERVAL 6 DAY), 'Uber', 'Withdrawal', 100, 34400, DATE_SUB(CURDATE(), INTERVAL 6 DAY));
INSERT 
    INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_date`, `transaction_summary`, `transaction_type`, `transaction_amount`, `closing_balance`, `created_at`) 
    VALUES (UUID(), 1865764534, 1, DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'Self Deposit', 'Deposit', 500,34900,DATE_SUB(CURDATE(), INTERVAL 5 DAY));
INSERT 
    INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_date`, `transaction_summary`, `transaction_type`, `transaction_amount`, `closing_balance`, `created_at`) 
    VALUES (UUID(), 1865764534, 1, DATE_SUB(CURDATE(), INTERVAL 4 DAY), 'Ebay', 'Withdrawal', 600, 34300, DATE_SUB(CURDATE(), INTERVAL 4 DAY));
INSERT 
    INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_date`, `transaction_summary`, `transaction_type`, `transaction_amount`, `closing_balance`, `created_at`)
    VALUES (UUID(), 1865764534, 1, DATE_SUB(CURDATE(), INTERVAL 2 DAY), 'OnlineTransfer', 'Deposit', 700, 35000, DATE_SUB(CURDATE(), INTERVAL 2 DAY));
INSERT 
    INTO `account_transactions` (`transaction_id`, `account_number`, `customer_id`, `transaction_date`, `transaction_summary`, `transaction_type`, `transaction_amount`, `closing_balance`, `created_at`)
    VALUES (UUID(), 1865764534, 1, DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'Amazon.com', 'Withdrawal', 100, 34900, DATE_SUB(CURDATE(), INTERVAL 1 DAY));


CREATE TABLE `loans` (
    `loan_number` int NOT NULL AUTO_INCREMENT,
    `customer_id` int NOT NULL,
    `start_date` date NOT NULL,
    `loan_type` varchar(64) NOT NULL,
    `amount_total` int NOT NULL,
    `amount_paid` int NOT NULL,
    `amount_outstanding` int NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`loan_number`),
    KEY `customer_id` (`customer_id`),
    CONSTRAINT `loan_customer_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE);

INSERT
    INTO `loans` ( `customer_id`, `start_date`, `loan_type`, `amount_total`, `amount_paid`, `amount_outstanding`)
    VALUES ( 1, '2020-10-13', 'Home', 200000, 50000, 150000);
INSERT 
    INTO `loans` ( `customer_id`, `start_date`, `loan_type`, `amount_total`, `amount_paid`, `amount_outstanding`)
    VALUES ( 1, '2020-06-06', 'Vehicle', 40000, 10000, 30000);
INSERT
    INTO `loans` ( `customer_id`, `start_date`, `loan_type`, `amount_total`, `amount_paid`, `amount_outstanding`)
    VALUES ( 1, '2018-02-14', 'Home', 50000, 10000, 40000);
INSERT
    INTO `loans` ( `customer_id`, `start_date`, `loan_type`, `amount_total`, `amount_paid`, `amount_outstanding`)
    VALUES ( 1, '2018-02-14', 'Personal', 10000, 3500, 6500);


CREATE TABLE `cards` (
    `card_id` int NOT NULL AUTO_INCREMENT,
    `card_number` varchar(64) NOT NULL,
    `customer_id` int NOT NULL,
    `card_type` varchar(32) NOT NULL,
    `total_limit` int NOT NULL,
    `amount_used` int NOT NULL,
    `available_amount` int NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`card_id`),
    KEY `customer_id` (`customer_id`),
    CONSTRAINT `card_customer_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`) ON DELETE CASCADE);

INSERT 
    INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `created_at`)
    VALUES ('4565XXXX4656', 1, 'Credit', 10000, 500, 9500, CURDATE());
INSERT
    INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `created_at`)
    VALUES ('3455XXXX8673', 1, 'Credit', 7500, 600, 6900, CURDATE());
INSERT
    INTO `cards` (`card_number`, `customer_id`, `card_type`, `total_limit`, `amount_used`, `available_amount`, `created_at`)
    VALUES ('2359XXXX9346', 1, 'Credit', 20000, 4000, 16000, CURDATE());


CREATE TABLE `notifications` (
    `notification_id` int NOT NULL AUTO_INCREMENT,
    `notification_summary` varchar(256) NOT NULL,
    `notification_details` varchar(1024) NOT NULL,
    `notification_start_date` date NOT NULL,
    `notification_end_date` date DEFAULT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`notification_id`));

INSERT 
    INTO `notifications` ( `notification_summary`, `notification_details`, `notification_start_date`, `notification_end_date`, `created_at`, `updated_at`)
    VALUES ('Home Loan Interest rates reduced', 'Home loan interest rates are reduced as per the goverment guidelines. The updated rates will be effective immediately', CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);
INSERT 
    INTO `notifications` ( `notification_summary`, `notification_details`, `notification_start_date`, `notification_end_date`, `created_at`, `updated_at`)
    VALUES ('Net Banking Offers', 'Customers who will opt for Internet banking while opening a saving account will get a $50 amazon voucher', CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);
INSERT 
    INTO `notifications` ( `notification_summary`, `notification_details`, `notification_start_date`, `notification_end_date`, `created_at`, `updated_at`)
    VALUES ('Mobile App Downtime', 'The mobile application of the EazyBank will be down from 2AM-5AM on 12/05/2020 due to maintenance activities', CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);
INSERT 
    INTO `notifications` ( `notification_summary`, `notification_details`, `notification_start_date`, `notification_end_date`, `created_at`, `updated_at`)
    VALUES ('E Auction notice', 'There will be a e-auction on 12/08/2020 on the Bank website for all the stubborn arrears.Interested parties can participate in the e-auction', CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);
INSERT
    INTO `notifications` ( `notification_summary`, `notification_details`, `notification_start_date`, `notification_end_date`, `created_at`, `updated_at`)
    VALUES ('Launch of Millennia Cards', 'Millennia Credit Cards are launched for the premium customers of EazyBank. With these cards, you will get 5% cashback for each purchase', CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);
INSERT 
    INTO `notifications` ( `notification_summary`, `notification_details`, `notification_start_date`, `notification_end_date`, `created_at`, `updated_at`)
    VALUES ('COVID-19 Insurance', 'EazyBank launched an insurance policy which will cover COVID-19 expenses. Please reach out to the branch for more details', CURDATE() - INTERVAL 30 DAY, CURDATE() + INTERVAL 30 DAY, CURDATE(), null);


CREATE TABLE `messages` (
    `message_id` varchar(256) NOT NULL,
    `name` varchar(32) NOT NULL,
    `email` varchar(32) NOT NULL,
    `subject` varchar(128) NOT NULL,
    `message` varchar(2048) NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`message_id`));


CREATE TABLE `authorities` (
    `id` int NOT NULL AUTO_INCREMENT,
    `customer_id` int NOT NULL,
    `name` varchar(64) NOT NULL,
    `created_at` datetime DEFAULT CURRENT_TIMESTAMP,
    `updated_at` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `customer_id` (`customer_id`),
    CONSTRAINT `authorities_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`customer_id`));

INSERT 
    INTO `authorities` (`customer_id`, `name`)
    VALUES (1, 'ROLE_USER');
INSERT 
    INTO `authorities` (`customer_id`, `name`)
    VALUES (1, 'ROLE_ADMIN');