## localhost access
create user 'rhino'@'localhost' identified by 'password';
grant all privileges on *.* to 'rhino'@'localhost' with grant option;

## Global access
create user 'rhino'@'%' identified by 'password';
grant all privileges on *.* to 'rhino'@'%' with grant option;
