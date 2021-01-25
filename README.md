# mysql 常用设置
###索引的重复
```   
select count(distinct left(word,1))/count(*) from table
```
###当前逐渐存在执行更新操作
```
insert into tb10(name,score)values('','') on duplicate key update score = score +1
```
### 远程访问授权 8 之前
```
mysql> grant all privileges on *.*  to  'root'@'%'  identified by 'root'  with grant option;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'identified by 'root'  with grant option' at line 1
mysql> create user 'brady'@'%' identified by 'brady';
Query OK, 0 rows affected (0.02 sec)
```
### 远程访问8 之后的授权
```
不能授权自己
root@a21073c9b9f7:/# mysql -uroot -p
mysql: [Warning] World-writable config file '/etc/mysql/conf.d/mysql.cnf' is ignored.
Enter password:
Welcome to the MySQL monitor.  Commands end with ; or \g.
Your MySQL connection id is 9
Server version: 8.0.13 MySQL Community Server - GPL
 
Copyright (c) 2000, 2018, Oracle and/or its affiliates. All rights reserved.
 
Oracle is a registered trademark of Oracle Corporation and/or its
affiliates. Other names may be trademarks of their respective
owners.
 
Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
 
mysql> grant all privileges on *.*  to  'root'@'%'  identified by 'root'  with grant option;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'identified by 'root'  with grant option' at line 1

### 正确打开方式

<br>mysql> create user 'brady'@'%' identified by 'brady';
Query OK, 0 rows affected (0.02 sec)
 
mysql>
mysql>
mysql> grant all privileges on *.*  to  'brady'@'%'  identified by 'brady'  with grant option;
ERROR 1064 (42000): You have an error in your SQL syntax; check the manual that corresponds to your MySQL server version for the right syntax to use near 'identified by 'brady'  with grant option' at line 1
mysql> GRANT ALL PRIVILEGES ON *.* TO 'brady'@'%';
Query OK, 0 rows affected (0.04 sec)
 
mysql> FLUSH PRIVILEGES;
Query OK, 0 rows affected (0.00 sec)
 
mysql> ALTER USER 'brady'@'%' IDENTIFIED WITH mysql_native_password BY 'brady';
Query OK, 0 rows affected (0.02 sec)
 
mysql> flush privileges;
Query OK, 0 rows affected (0.01 sec)


ALTER USER 'username'@'%' IDENTIFIED WITH mysql_native_password BY 'password';
FLUSH PRIVILEGES;

```
