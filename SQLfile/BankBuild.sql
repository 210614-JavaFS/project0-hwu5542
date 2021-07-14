DROP TABLE IF EXISTS login_accounts CASCADE;
DROP TABLE IF EXISTS bank_accounts CASCADE;
DROP TABLE IF EXISTS applications CASCADE;

-- Schema

CREATE TABLE login_accounts(usernames varchar(20) not null UNIQUE, passwords varchar(20),
  account_id int, account_type int,
  first_name varchar(20), last_name varchar(20), address varchar(50), phone varchar(20), email varchar(50), join_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (usernames));

CREATE TABLE bank_accounts(account_id SERIAL not null, account_fund numeric(12, 2),
  account_user_one varchar(20), account_user_two varchar(20),
  PRIMARY KEY (account_id));
 
CREATE TABLE applications (application_id SERIAL not null, usernames varchar(20) not null,
  PRIMARY KEY (application_id)); 
 
ALTER TABLE login_accounts
  ADD CONSTRAINT fk_login_bank
  FOREIGN KEY (account_id) REFERENCES bank_accounts(account_id) ON DELETE SET NULL;

ALTER TABLE bank_accounts
  ADD CONSTRAINT fk_bank_login_one
  FOREIGN KEY (account_user_one) REFERENCES login_accounts(usernames) ON DELETE SET NULL;

ALTER TABLE bank_accounts
  ADD CONSTRAINT fk_bank_login_two
  FOREIGN KEY (account_user_two) REFERENCES login_accounts(usernames) ON DELETE SET NULL;
 
ALTER TABLE applications
  ADD CONSTRAINT fk_app_login
  FOREIGN KEY (usernames) REFERENCES login_accounts(usernames) ON DELETE CASCADE;

-- Trigger 
 
CREATE OR REPLACE FUNCTION user_type() RETURNS TRIGGER AS 
$BODY$
BEGIN 
	NEW.account_type := 1;
	RETURN NEW;
END
$BODY$
language plpgsql;

CREATE TRIGGER user_type BEFORE INSERT ON login_accounts 
	FOR EACH ROW EXECUTE PROCEDURE user_type();


-- New User

INSERT INTO login_accounts(usernames, passwords) VALUES
  ('admin', 'admin'), ('employee', 'employee'), ('tim','[]\');

UPDATE login_accounts SET account_type = 3 WHERE usernames = 'admin';
  
UPDATE login_accounts SET account_type = 2 WHERE usernames = 'employee';
