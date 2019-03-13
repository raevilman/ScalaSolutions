import org.h2.jdbc.JdbcSQLException

import scala.io.StdIn

object Main extends App {
  Task2.initSchemas()
  Task2.insertDummyData()
  Iterator.continually(printMenu())
    .takeWhile(_ != 0)
    .foreach {

      case 1 => println(Task1.mostOccurringCharacter())
      case 2 => println(Task1.leastOccurringCharacter())
      case 3 => println(Task1.countOccurrence())
      case 4  => Task2.listAllCoursesSQL()
      case 5  => Task2.getMaxEnrolledCourse()
      case 6  => Task2.getStudentsCouseCount()
      case 7  => Task2.tryEnrollingInCourse()
      case 8  => Task2.tryEnrollingInFullCourse()
      case -1 =>
      case default => handleInvalidInput(default)
    }
  println("Exiting...")
  Task2.dropSchemas()
  Task2.closeDb()

  def printMenu():Int ={
    try{
      val menu =
        """
          | ===================================================
          | Task 1:
          |   Given a string
          |
          |   [1] return the most occurring character
          |   [2] return the least occurring character
          |   [3] count of occurrence of all characters
          |
          | Task 2:
          |  StudentCourse --StudentId, StudentName ,CourseId, StartDate,EndDate
          |  Course -- CourseId,CourseName,CourseDuration,Credit,Cost
          |
          |  functions available:
          |  [4] List all available courses
          |  [5] Get the Course which has Maximum enrollment
          |  [6] Get the number of courses each Student is enrolled at the sametime
          |  [7] Enroll a student
          |  [8] Try enrolling in the course which is full
          |
          |
          | Enter 0 to exit
          |
          | ===================================================
          |
          | Please input your option
        """.stripMargin
      println(menu)
      StdIn.readInt
    } catch{
      case nfe: NumberFormatException => {
        handleInvalidInput(nfe.getMessage)
        -1
      }
    }
  }

  def handleInvalidInput(input:Any): Unit ={
    println("Invalid option entered: "+input)
  }
}


