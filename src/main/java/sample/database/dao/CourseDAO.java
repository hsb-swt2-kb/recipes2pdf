package sample.database.dao;

import sample.database.dbo.CourseDBO;
import sample.model.Course;

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
        if (findById(pojo.getID()).isPresent()) {
            courseDBO.setID(pojo.getID());
        }

        courseDBO.setName(pojo.getName());
        return courseDBO;
    }
}
