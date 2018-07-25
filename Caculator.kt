fun main(args: Array<String>) {
    println("请输入表达式，如：3 + 4")
    while (true){
        val inPut= readLine()?:break
        try{
            val result=inPut.trim().split(" ")
            if(result.size<3){
                throw IllegalArgumentException()
            }
            val vaue1:Double=result[0].toDouble()
            val value2:Double=result[2].toDouble()
            val operation:String=result[1]
            println("$vaue1 $operation $value2 =${OPeration(operation).apply(vaue1,value2)}")
        }catch (e:NumberFormatException){
            println("你确定输入的是数字吗？")
        }catch (e:IllegalArgumentException){
            println("您确定使用空格分割的吗？")
        }catch (e:Exception){
            println("人品差，程序异常，${e.message}")
        }

        println("是否再来一次？[Y]")
        val cmd= readLine()
        if(cmd==null || cmd.toUpperCase()!="Y"){
            println("谢谢使用")
            break
        }
    }
    args.filter ( String::isNullOrEmpty )
    mangArgs(str2="HAHAHA")
    args.forEach(::println)
    args.forEach(WoldPrrinter()::println)

    args.apply { }
    for(item in args.withIndex()){

    }


}

val age:Int by lazy {
    10
}

class WoldPrrinter{
    fun println(any:Any?){
        kotlin.io.println(any)
    }

}

class OPeration(ope:String){
    val operation:(left:Double, right:Double)->Double

    init {
        operation=when(ope){
            "+"->{l,r->l+r}
            "-"->{l,r->l-r}
            "*"->{l,r->l*r}
            "/"->{l,r->l/r}
            "%"->{l,r->l%r}
            else -> {
                throw UnsupportedOperationException(ope)
            }
        }
    }

    fun apply(left:Double,right:Double): Double {
        return operation(left,right)
    }
}

fun caculatorTest(){
    println("Package Call")
}

fun mangArgs(str1:String="STR1",str2:String,str3:String="STR3"){
    println("ARGS: $str1  $str2  $str3")
}

fun String.sayHello(){
    println("Hello World")
}