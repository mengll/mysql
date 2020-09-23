package com.anfeng.utils
import java.text.SimpleDateFormat
import java.util.Date
object WindowUtils {
  
// 窗口划分测试，水印测试
// final val DateUtils.SECOND_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
/*
* 流处理窗口计算，计算窗口的开始时间，窗口数量
* @param windowDuration 窗口的长度 单位秒
* @param slideDuration  窗口的滑动步长
* @param starttime 开始时间
* @param waterts 水印时间
* @param f:Int=>Int 水印处理函数
*    案例：
*    val water = waterTime()
*    windowSize("2019-08-14 10:55:00",600,120,0,120,water)
*    println("---------------window----------------")
*    windowSize("2019-08-14 11:00:00",600,120,0,120,water)
*    println("---------------window-----------------")
*    windowSize("2019-08-14 10:55:00",600,120,0,120,water)
*/
// 窗口划分计算（有效窗口）
 def windowSize( event_time:String, windowDuration:Int, slideDuration:Int, starttime:Int,waterts:Int,f:Int=>Int) {
   // 最大的窗口数量
   val maxNumapp = math.ceil(windowDuration / slideDuration).toInt
   var nowts = new Date().getTime() / 1000
   if(event_time != ""){
      nowts =  DateUtils.tranTimeToLong(event_time) / 1000
   }
   var ws:Long = 0
   if (waterts > 0 ) {
      val tempws = nowts - waterts
     ws = f(tempws.toInt)
     ms2string(ws, DateUtils.SECOND_DATE_FORMAT)
   }

   for( i <- 0 to maxNumapp){
     val windowId = math.ceil((nowts - starttime)  / slideDuration).toLong  // 计算窗口ID
     val windowStart = windowId * slideDuration + (i- maxNumapp) * slideDuration + starttime // 窗口的开始时间
     val windowEnd = windowStart + windowDuration // 窗口的结束时间
     if (windowEnd > nowts &&  starttime < nowts ){
         if (windowEnd > ws && ws > 0 ){
           println(ms2string(windowStart.toLong,DateUtils.SECOND_DATE_FORMAT),ms2string(windowEnd.toLong,DateUtils.SECOND_DATE_FORMAT))
         }else if (ws ==0){
           println(ms2string(windowStart.toLong,DateUtils.SECOND_DATE_FORMAT),ms2string(windowEnd.toLong,DateUtils.SECOND_DATE_FORMAT))
       }
     }
   }
 }

 // 秒格式转化类型
def ms2string(tms:Long,format:String): String ={
  val ts = new SimpleDateFormat(DateUtils.SECOND_DATE_FORMAT).format(new java.util.Date(tms * 1000))
  ts
}

// 验证数据的水印时间
def waterTime(): Int =>Int ={
   var watertime:Int = 0
   val f = (a:Int) => {
      watertime =  if (a > watertime) { a }else watertime
      if (watertime >0){
        println("水印时间",ms2string(watertime,DateUtils.SECOND_DATE_FORMAT))
      }
      watertime
   }
   f
}

}
