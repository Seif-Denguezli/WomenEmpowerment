package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entities.Course;
import tn.esprit.spring.entities.User;
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
	@Query(nativeQuery = true,value = "SELECT u.user_id from user u INNER JOIN certificate ON u.user_id = certificate.user_user_id INNER JOIN course ON certificate.course_course_id = course.course_id WHERE course_id=:param")
	public Long findUserById(@Param("param") Long courseId);
	
}
/*
SELECT * from certificate c INNER JOIN course ON c.course_course_id= course.course_id INNER JOIN course_quiz ON course.course_id= course_quiz.course_course_id INNER JOIN quiz ON quiz.quiz_id = course_quiz.quiz_quiz_id INNER JOIN quiz_questions ON quiz_questions.quiz_quiz_id = quiz.quiz_id INNER JOIN quiz_question ON quiz_question.question_id = quiz_questions.questions_question_id INNER JOIN quiz_question_answers ON quiz_question_answers.quiz_question_question_id = quiz_question.question_id INNER JOIN answer ON answer.answer_id = quiz_question_answers.answers_answer_id;
*/