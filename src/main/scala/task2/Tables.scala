package task2


import slick.driver.H2Driver.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}


class Course(tag: Tag)
  extends Table[(Int, String, Double, Int, Double)](tag, "COURSE") {

  // This is the primary key column:
  def id: Rep[Int] = column[Int]("COURSE_ID", O.PrimaryKey)
  def name: Rep[String] = column[String]("COURSE_NAME")
  def duration: Rep[Double] = column[Double]("COURSE_DURATION")
  def credit: Rep[Int] = column[Int]("COURSE_CREDIT")
  def cost: Rep[Double] = column[Double]("COURSE_COST")

  // Every table needs a * projection with the same type as the table's type parameter
  def * : ProvenShape[(Int, String, Double, Int, Double)] =
    (id, name, duration, credit, cost)
}

// A Coffees table with 5 columns: name, supplier id, price, sales, total
class StudentCourse(tag: Tag)
  extends Table[(Int, String, Int, String, String)](tag, "STUDENT_COURSE") {

  def studentId: Rep[Int] = column[Int]("STUDENT_ID")
  def studentName: Rep[String] = column[String]("STUDENT_NAME")
  def courseId: Rep[Int] = column[Int]("COURSE_ID")
  def startDate: Rep[String] = column[String]("START_DATE")
  def endDate: Rep[String] = column[String]("END_DATE")

  def * : ProvenShape[(Int, String, Int, String, String)] =
    (studentId, studentName, courseId, startDate, endDate)

  // A reified foreign key relation that can be navigated to create a join
  def courses: ForeignKeyQuery[Course, (Int, String, Double, Int, Double)] =
    foreignKey("COURSE_FK", courseId, TableQuery[Course])(_.id)
}

