package sample.database.dao;

import sample.database.dbo.CourseDBO;
import sample.model.Course;

import java.util.Optional;

/**
 * Database Access Object for Course.
 * Does Mapping from POJO to DBO and vice versa.
 * Created by czoeller on 03.05.16.
 */
public class CourseDAO extends ADAO<Course, CourseDBO> {

    @Override
    Course toPOJO(CourseDBO courseDBO) {
        final Course course = new Course();
        course.setID(courseDBO.getID());
        course.setName(courseDBO.getName());
        return course;
    }

    @Override
    CourseDBO toDBO(Course pojo) {
        CourseDBO courseDBO = new CourseDBO();
        Optional<Course> course = findFirst("name=?", pojo.getName());

        // if course not present in DB then insert it.
        if(!course.isPresent()){
            courseDBO.setName(pojo.getName());
            courseDBO.saveIt();
        }
        else {  // else read data from existing course entry in DB
            courseDBO.setID(course.get().getID());
            courseDBO.setName(course.get().getName());
        }
        return courseDBO;
    }
}
