CREATE TABLE "client_group" (
	client_group_id INTEGER NOT NULL,
	min_rating FLOAT ( 2147483647 ) NOT NULL,
	max_rating FLOAT ( 2147483647 ) NOT NULL,
	name VARCHAR ( 70 ) NOT NULL,
	description VARCHAR ( 255 ),

	CONSTRAINT TC_Client_Group_Name UNIQUE (name),
	CONSTRAINT PK_Client_Group_ID PRIMARY KEY (client_group_id)
	);

CREATE TABLE "user" (
	user_id INTEGER NOT NULL,
	first_name VARCHAR ( 20 ) NOT NULL,
	last_name VARCHAR ( 20 ) NOT NULL,
	pers_code VARCHAR ( 11 ) NOT NULL,
	password VARCHAR ( 20 ) NOT NULL,
	username VARCHAR ( 40 ) NOT NULL,

	CONSTRAINT PK_User_ID PRIMARY KEY (user_id),
	CONSTRAINT TC_User_Code UNIQUE (pers_code),
	CONSTRAINT TC_User_Username UNIQUE (username)
	);

CREATE TABLE "client_status" (
	client_status_id INTEGER NOT NULL,
	name VARCHAR ( 70 ) NOT NULL,
	description VARCHAR ( 255 ),

	CONSTRAINT TC_Client_Status_Name UNIQUE (name),
	CONSTRAINT PK_Client_Status_ID PRIMARY KEY (client_status_id)
	);

CREATE TABLE "client" (
	client_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	client_group_id INTEGER NOT NULL,
	client_status_id INTEGER NOT NULL,
	rating FLOAT ( 2147483647 ) NOT NULL,

	CONSTRAINT PK_Client_ID PRIMARY KEY (client_id),
	CONSTRAINT TC_Client_User_ID UNIQUE (user_id),
	CONSTRAINT FK_Client_Status_ID FOREIGN KEY (client_status_id) REFERENCES client_status (client_status_id),
	CONSTRAINT FK_Client_Group_ID FOREIGN KEY (client_group_id) REFERENCES cient_group (client_group_id),
	CONSTRAINT FK_Client_User_ID FOREIGN KEY (user_id) REFERENCES user (user_id)
	);

CREATE TABLE "client_history" (
	client_history_id INTEGER NOT NULL,
	client_id INTEGER NOT NULL,
	client_status_id INTEGER,
	client_group_id INTEGER,
	date TIMESTAMP NOT NULL,
	new_rating FLOAT ( 2147483647 ) NOT NULL,

	CONSTRAINT FK_Cleint_Status FOREIGN KEY (client_status_id) REFERENCES client_status (client_status_id),
	CONSTRAINT FK_Cleint_Group FOREIGN KEY (client_group_id) REFERENCES client_group (client_group_id),
	CONSTRAINT FK_Client FOREIGN KEY (client_id) REFERENCES client (client_id),
	CONSTRAINT PK_Client_History_Id PRIMARY KEY (client_history_id)
	);

CREATE TABLE "employee_position" (
	employee_position_id INTEGER NOT NULL,
	name VARCHAR ( 70 ) NOT NULL,
	description VARCHAR ( 255 ),

	CONSTRAINT TC_Position_Name UNIQUE (name),
	CONSTRAINT PK_Position_ID PRIMARY KEY (employee_position_id)
	);

CREATE TABLE "employee" (
	emloyee_id INTEGER NOT NULL,
	user_id INTEGER NOT NULL,
	employee_position_id INTEGER NOT NULL,

	CONSTRAINT TC_Employee_User_ID UNIQUE (user_id),
	CONSTRAINT PK_Employee_ID PRIMARY KEY (emloyee_id),
	CONSTRAINT FK_Employee_Position_ID FOREIGN KEY (employee_position_id) REFERENCES employee_position (employee_position_id),
	CONSTRAINT FK_Employee_User_ID FOREIGN KEY (user_id) REFERENCES user (user_id)
	);

CREATE TABLE "loan_offer" (
	loan_offer_id INTEGER NOT NULL,
	client_group_id INTEGER NOT NULL,
	sum FLOAT ( 2147483647 ) NOT NULL,
	period SMALLINT NOT NULL,
	apr FLOAT ( 2147483647 ) NOT NULL,
	rating_bonus FLOAT ( 2147483647 ) NOT NULL,
	offer_end DATE NOT NULL,

	CONSTRAINT PK_Loan_Offer_ID PRIMARY KEY (loan_offer_id),
	CONSTRAINT FK_Loan_Offer_Client_Group_ID FOREIGN KEY (client_group_id) REFERENCES cient_group (client_group_id)
	);

CREATE TABLE "loan_status" (
	loan_status_id INTEGER NOT NULL,
	name VARCHAR ( 70 ) NOT NULL,
	description VARCHAR ( 255 ),

	CONSTRAINT TC_Loan_Status_Name UNIQUE (name),
	CONSTRAINT PK_Loan_Status_ID PRIMARY KEY (loan_status_id)
	);

CREATE TABLE "loan_history" (
	loan_history_id INTEGER NOT NULL,
	loan_id INTEGER NOT NULL,
	loan_status_id INTEGER NOT NULL,
	date TIMESTAMP NOT NULL,

	CONSTRAINT PK_Loan_History_Id PRIMARY KEY (loan_history_id),
	CONSTRAINT FK_Loan_History_Status_ID FOREIGN KEY (loan_status_id) REFERENCES loan_status (loan_status_id),
	CONSTRAINT FK_Loan_History_Loan_ID FOREIGN KEY (loan_id) REFERENCES loan (loan_id)
	);

CREATE TABLE "postpone_request_status" (
	postpone_request_status_id INTEGER NOT NULL,
	name VARCHAR ( 70 ) NOT NULL,
	description VARCHAR ( 255 ),

	CONSTRAINT PK_Postpone_Request_Status_ID PRIMARY KEY (postpone_request_status_id),
	CONSTRAINT TC_Postpone_Request_Status_Name UNIQUE (name)
	);

CREATE TABLE "postpone_request" (
	postpone_request_id INTEGER NOT NULL,
	postpone_request_status_id INTEGER NOT NULL,
	employee_id INTEGER,
	date TIMESTAMP NOT NULL,
	comment VARCHAR ( 255 ),
	period_days INTEGER,

	CONSTRAINT PK_Postpone_Request_ID PRIMARY KEY (postpone_request_id),
	CONSTRAINT FK_Postpone_Request_Approved_By FOREIGN KEY (employee_id) REFERENCES employee (emloyee_id),
	CONSTRAINT FK_Postpone_Request_Status_ID FOREIGN KEY (postpone_request_status_id) REFERENCES postpone_request_status (postpone_request_status_id)
	);

CREATE TABLE "loan" (
	loan_id INTEGER NOT NULL,
	due_date DATE NOT NULL,
	base_due_date DATE NOT NULL,
	debt FLOAT ( 2147483647 ) NOT NULL,
	client_id INTEGER NOT NULL,
	employee_id INTEGER,
	loan_status_id INTEGER NOT NULL,
	loan_offer_id INTEGER NOT NULL,
	postpone_request_id INTEGER,
	comment VARCHAR ( 255 ),

	CONSTRAINT PK_Loan_ID PRIMARY KEY (loan_id),
	CONSTRAINT FK_Loan_Status_ID FOREIGN KEY (loan_status_id) REFERENCES loan_status (loan_status_id),
	CONSTRAINT FK_Loan_Employee_ID FOREIGN KEY (employee_id) REFERENCES employee (emloyee_id),
	CONSTRAINT FK_Loan_Postpone_Request_ID FOREIGN KEY (postpone_request_id) REFERENCES postpone_request (id),
	CONSTRAINT FK_Loan_Client_ID FOREIGN KEY (client_id) REFERENCES client (client_id),
	CONSTRAINT FK_Loan_Loan_Offer_ID FOREIGN KEY (loan_offer_id) REFERENCES loan_offer (loan_offer_id)
	);
