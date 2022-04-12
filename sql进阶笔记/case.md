### case 注意事项
case的条件具有排它性 如果条件已经满足查询条件将不会再往下执行 case 的匹配条件
1. 统一分支返回的数据类型
2. 不要忘记写end
3. 养成写else 的习惯 如果查询的结果为null 可能会造成查询结果错误

```
  case when name = 'aa' Then 'diyi'
       when name = 'bb' then 'dier'
       else 'wanda'
       end 
       
  -- 再group 的子句中执行 group 的操作
  
  select case game when '大话' then '大话西游'
                   when '传奇' then '传奇'
                   else '其他' end as distinct,
                   sum(pipo)
                   from bb
                   group by case game 
                   when '大话' then '大话西游'
                   when '传奇' then '传奇'
                   else '其他'
                   end 
                   
     优化版本
    [违反了标准写法] from on  where group having select  distinct order limit 
  select case game when '大话' then '大话西游'
                   when '传奇' then '传奇'
                   else '其他' end as distinct,
                   sum(pipo)
                   from bb
                   group by distinct
                 
  
   计算同一列的数据计算
   select pre_name ,
   sum(case when name = '1' then 0 else 1) as cnt_m
      sum(case when name = '1' then 1 else 2) as cnt_f
      from  dpad
       group by pref_name

```

### case 与 check
check是用来检查数据表中字段值有效性的一种手段
```
  CONSTRAINT check_salary CHECK
 ( CASE WHEN sex = '2'
 THEN CASE WHEN salary <= 200000
 THEN 1 ELSE 0 END
 ELSE 1 END = 1 )
 
```

### update中使用case 
```
UPDATE Salaries
 SET salary = CASE WHEN salary >= 300000
 THEN salary * 0.9
 WHEN salary >= 250000 AND salary < 280000
 THEN salary * 1.2
 ELSE salary END;
 
 
--- 批量调换
 UPDATE SomeTable
 SET p_key = CASE WHEN p_key = 'a'
 THEN 'b'
 WHEN p_key = 'b'
 THEN 'a'
 ELSE p_key END
 WHERE p_key IN ('a', 'b');
 
```
### 在case 中使用强大的
-- 在 CASE 表达式里，我们可以使用 BETWEEN、LIKE 和 <、 > 等
便利的谓词组合，以及能嵌套子查询的 IN 和 EXISTS 谓词。因此，CASE
表达式具有非常强大的表达能力。

```
-- 表的匹配 ：使用 IN 谓词
SELECT course_name,
 CASE WHEN course_id IN
 (SELECT course_id FROM OpenCourses
 WHERE month = 200706) THEN '○'
 ELSE '×' END AS "6 月",
 CASE WHEN course_id IN
 (SELECT course_id FROM OpenCourses
 WHERE month = 200707) THEN '○'
 ELSE '×' END AS "7 月",
 CASE WHEN course_id IN
 (SELECT course_id FROM OpenCourses
 WHERE month = 200708) THEN '○'
 ELSE '×' END AS "8 月"
 FROM CourseMaster;
 
 
-- 表的匹配 ：使用 EXISTS 谓词
SELECT CM.course_name,
 CASE WHEN EXISTS
 (SELECT course_id FROM OpenCourses OC
 WHERE month = 200706
 AND OC.course_id = CM.course_id) THEN '○'
 ELSE '×' END AS "6 月",
 CASE WHEN EXISTS
 (SELECT course_id FROM OpenCourses OC
 WHERE month = 200707
 AND OC.course_id = CM.course_id) THEN '○'
 ELSE '×' END AS "7 月",
 CASE WHEN EXISTS
 (SELECT course_id FROM OpenCourses OC
 WHERE month = 200708
 AND OC.course_id = CM.course_id) THEN '○'
 ELSE '×' END AS "8 月"
 FROM CourseMaster CM;
```
无论使用 IN 还是 EXISTS，得到的结果是一样的，但从性能方面来说，
EXISTS 更好。通过 EXISTS 进行的子查询能够用到“month, course_
id”这样的主键索引，因此尤其是当表 OpenCourses 里数据比较多的时候
更有优势。

### 在case 中使用聚合函数
```
SELECT std_id,
 CASE WHEN COUNT(*) = 1 -- 只加入了一个社团的学生
 THEN MAX(club_id)
 ELSE MAX(CASE WHEN main_club_flg = 'Y'
 THEN club_id
 ELSE NULL END)
 END AS main_club
 FROM StudentClub
 GROUP BY std_id;
```


