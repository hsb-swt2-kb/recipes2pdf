package sample.model.dao;

import sample.model.Course;
import sample.model.activejdbc.CourseDBO;

/**
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
        courseDBO.save();
        return courseDBO;
    }
}
