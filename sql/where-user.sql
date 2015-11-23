drop user 'where'@'localhost';
create user 'where'@'localhost' identified by 'where';
grant all privileges on wheredb.* to 'where'@'localhost';
flush privileges;