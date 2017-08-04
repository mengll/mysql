# mysql 常用设置
###索引的重复
```   
select count(distinct left(word,1))/count(*) from table
```
###当前逐渐存在执行更新操作
```
insert into tb10(name,score)values('','') on duplicate key update score = score +1
```
