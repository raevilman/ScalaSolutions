import scala.concurrent.{Await}
import scala.concurrent.duration.Duration
import slick.dbio.DBIOAction
import slick.driver.H2Driver.api._
import task2.{Course, StudentCourse}


object Task2 {
  println("Starting db")
  val db = Database.forConfig("h2mem1")

  val courseTable: TableQuery[Course] = TableQuery[Course]

  val studentCourseTable: TableQuery[StudentCourse] = TableQuery[StudentCourse]

  def initSchemas() ={
    val schemas = DBIOAction.seq(
      courseTable.schema.create,
      studentCourseTable.schema.create
    )
    Await.result(db.run(schemas), Duration.Inf)
    println("Schema initiated...")
  }
  def dropSchemas() ={
    try{
      val schemas = DBIOAction.seq(
        courseTable.schema.drop,
        studentCourseTable.schema.drop
      )
      Await.result(db.run(schemas), Duration.Inf)
      println("Schema dropped...")
    } catch {
      case e:org.h2.jdbc.JdbcSQLException => println("")
    }

  }

  def insertDummyData() = {
    val insertions = DBIOAction.seq(
      courseTable += (101, "Scala Beginners", 40.0, 1, 350.0),
      courseTable += (102, "Scala Advanced", 30.0, 1, 200.0),
      courseTable += (103, "Functional Programming", 25.0, 1, 150.0),
      courseTable += (104, "SQL Crash Course", 20.0, 1, 100.0),

      studentCourseTable += (1, "Elon", 101, "20-02-2019", "20-05-2019"),
      studentCourseTable += (1, "Elon", 102, "20-02-2019", "20-05-2019"),
      studentCourseTable += (1, "Elon", 103, "20-02-2019", "20-05-2019"),
      studentCourseTable += (1, "Elon", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (2, "Mark", 101, "20-02-2019", "20-05-2019"),
      studentCourseTable += (2, "Mark", 103, "20-02-2019", "20-05-2019"),
      studentCourseTable += (2, "Mark", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (3, "John", 102, "20-02-2019", "20-05-2019"),
      studentCourseTable += (3, "John", 103, "20-02-2019", "20-05-2019"),

      studentCourseTable += (4, "Student-1", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (5, "Student-2", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (6, "Student-3", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (7, "Student-4", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (8, "Student-5", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (9, "Student-6", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (10, "Student-7", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (11, "Student-8", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (12, "Student-9", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (13, "Student-10", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (14, "Student-11", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (15, "Student-12", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (16, "Student-13", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (17, "Student-14", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (18, "Student-15", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (19, "Student-16", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (20, "Student-17", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (21, "Student-18", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (22, "Student-19", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (23, "Student-20", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (24, "Student-21", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (25, "Student-22", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (26, "Student-23", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (27, "Student-24", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (28, "Student-25", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (29, "Student-26", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (30, "Student-27", 104, "20-02-2019", "20-05-2019"),
      studentCourseTable += (31, "Student-28", 104, "20-02-2019", "20-05-2019")

    )
    Await.result(db.run(insertions),Duration.Inf)
    println("Dummy data inserted....")
  }

  def listAllCourses(): Unit ={
    val result = Await.result(db.run(courseTable.result), Duration.Inf)
    println(result.mkString("\n"))
    //.onSuccess{ case s => println(s"Result: $s") }
  }

  def listAllCoursesSQL(): Unit ={

    val result = Await.result(db.run(sql"SELECT * FROM COURSE".as[(Int, String, Double, Int, Double)]), Duration.Inf)
    println(result.mkString("\n"))
    //.onSuccess{ case s => println(s"Result: $s") }
  }

  def getMaxEnrolledCourse():Unit = {
    val result = Await.result(db.run(
      sql"""
           SELECT stdcrs.COURSE_ID, crs.COURSE_NAME, COUNT(STUDENT_ID) as count  FROM STUDENT_COURSE stdcrs
           LEFT JOIN
           COURSE crs
           ON stdcrs.COURSE_ID=crs.COURSE_ID
           GROUP BY stdcrs.COURSE_ID
           ORDER BY count DESC
           LIMIT 1
        """.as[(Int, String, Int)]), Duration.Inf)
    println("(CourseId, CourseName, EnrollmentCount")
    println(result.mkString("\n"))
  }

  def getStudentsCouseCount():Unit = {
    val result = Await.result(db.run(
      sql"""
           SELECT stdcrs.STUDENT_ID, stdcrs.STUDENT_NAME, COUNT(COURSE_ID) as count  FROM STUDENT_COURSE stdcrs
           GROUP BY stdcrs.STUDENT_ID, stdcrs.STUDENT_NAME
           ORDER BY count DESC
        """.as[(Int, String, Int)]), Duration.Inf)
    println("(StudentId, StudentName, EnrollmentCount)")
    println(result.mkString("\n"))
  }

  def tryEnrollingInFullCourse():Unit = {
    println(
      """
        | Assumptions:
        |   CourseId : 104 //As it already has 30 students
        |   StudentId: 3 //As John(id:3) is not enrolled in this course
      """.stripMargin)
    enrollInCourse(104, 3, "John")
  }

  def tryEnrollingInCourse():Unit = {
    println(
      """
        | Assumptions:
        |   CourseId : 101 //As it has seats available
        |   StudentId: 3 //As John(id:3) is not enrolled in this course
      """.stripMargin)
    enrollInCourse(101, 3, "John")
  }

  def enrollInCourse(courseId: Int, studentId: Int, studentName:String, startDate:String="20-02-2019", endDate:String="20-05-2019"):Unit = {

    val result = Await.result(db.run(
      sql"""
           SELECT COUNT(STUDENT_ID) as count  FROM STUDENT_COURSE stdcrs
           WHERE COURSE_ID = $courseId
        """.as[(Int)]), Duration.Inf)
    if(result.head < 30){
      Await.result(db.run(
        sql"""
           INSERT INTO STUDENT_COURSE VALUES($studentId, $studentName, $courseId, $startDate, $endDate )
        """.as[(Int)]), Duration.Inf)
      println("Enrolled successfully!")
    } else{
      println("Sorry, Course is full!")
    }
//    println(result.mkString("\n"))

  }

  def queryStudents(): Unit ={
    val result = Await.result(db.run(studentCourseTable.result), Duration.Inf)
    println(result.mkString("\n"))
    //.onSuccess{ case s => println(s"Result: $s") }
  }

  def closeDb(): Unit ={
    db.close()
  }
}
