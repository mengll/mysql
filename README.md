# mysql 常用设置
###索引的重复
```   
select count(distinct left(word,1))/count(*) from table
```
