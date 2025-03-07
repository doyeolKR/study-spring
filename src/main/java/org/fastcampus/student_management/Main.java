package org.fastcampus.student_management;

import org.fastcampus.student_management.application.course.CourseService;
import org.fastcampus.student_management.domain.CourseFee;
import org.fastcampus.student_management.domain.dto.CourseInfoDto;
import org.fastcampus.student_management.application.student.StudentService;
import org.fastcampus.student_management.application.student.dto.StudentInfoDto;
import org.fastcampus.student_management.repo.CourseCommandRepositoryImpl;
import org.fastcampus.student_management.repo.CourseJdbcCommandRepository;
import org.fastcampus.student_management.repo.StudentRepository;
import org.fastcampus.student_management.ui.course.CourseController;
import org.fastcampus.student_management.ui.course.CoursePresenter;
import org.fastcampus.student_management.ui.student.StudentController;
import org.fastcampus.student_management.ui.student.StudentPresenter;
import org.fastcampus.student_management.ui.UserInputType;

public class Main {

  public static void main(String[] args) {
    StudentRepository studentRepository = new StudentRepository();
    CourseCommandRepositoryImpl courseRepositoryImpl = new CourseCommandRepositoryImpl();
    CourseJdbcCommandRepository jdbcCommandRepository = new CourseJdbcCommandRepository();


    StudentService studentService = new StudentService(studentRepository);
    CourseService courseService = new CourseService(courseRepositoryImpl, jdbcCommandRepository, studentRepository);

    CoursePresenter coursePresenter = new CoursePresenter();
    StudentPresenter studentPresenter = new StudentPresenter();

    CourseController courseController = new CourseController(coursePresenter, courseService, studentPresenter);
    StudentController studentController = new StudentController(studentPresenter, studentService);

    // 기본 default 세팅 추가
    StudentInfoDto studentInfoDto = new StudentInfoDto("카리나", 23, "서울시 강남구");
    StudentInfoDto studentInfoDto1 = new StudentInfoDto("윈터", 23, "대전 광역시 서구");
    StudentInfoDto studentInfoDto2 = new StudentInfoDto("장원영", 23, "대전 광역시 유성구");
    studentService.saveStudent(studentInfoDto);
    studentService.saveStudent(studentInfoDto1);
    studentService.saveStudent(studentInfoDto2);

    CourseInfoDto courseInfoDto = new CourseInfoDto("연기", 10000, "MONDAY", "카리나", 171729908L);
    CourseInfoDto courseInfoDto1 = new CourseInfoDto("댄스", 10000, "MONDAY", "윈터", 171729908L);
    CourseInfoDto courseInfoDto2 = new CourseInfoDto("노래", 10000, "MONDAY", "장원영", 171729908L);
    courseService.registerCourse(courseInfoDto);
    courseService.registerCourse(courseInfoDto1);
    courseService.registerCourse(courseInfoDto2);

    studentPresenter.showMenu();
    UserInputType userInputType = studentController.getUserInput();
    while (userInputType != UserInputType.EXIT) {
      switch (userInputType) {
        case NEW_STUDENT:
          studentController.registerStudent();
          break;
        case NEW_COURSE:
          courseController.registerCourse();
          break;
        case SHOW_COURSE_DAY_OF_WEEK:
          courseController.showCourseDayOfWeek();
          break;
        case ACTIVATE_STUDENT:
          studentController.activateStudent();
          break;
        case DEACTIVATE_STUDENT:
          studentController.deactivateStudent();
          break;
        case CHANGE_FEE:
          courseController.changeFee();
          break;
        default:
          studentPresenter.showErrorMessage();
          break;
      }
      studentPresenter.showMenu();
      userInputType = studentController.getUserInput();
    }
  }
}