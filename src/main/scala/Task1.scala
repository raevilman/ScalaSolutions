import scala.io.StdIn

object Task1 {

  val input = {
    println("Input the string you want to operate on")
    StdIn.readLine()
  }

  def countOccurrence(): Map[Char, Int] ={
    println(input)
    input.groupBy(_.toChar).mapValues(_.size)
  }

  def mostOccurringCharacter(): (Char, Int) ={
    val countMap = countOccurrence()
    countMap.maxBy(_._2)
  }

  def leastOccurringCharacter(): (Char, Int) ={
    val countMap = countOccurrence()
    countMap.minBy(_._2)
  }
}
