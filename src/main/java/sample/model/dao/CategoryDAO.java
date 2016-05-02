package sample.model.dao;


import sample.model.Category;
import sample.model.activejdbc.CategoryDBO;

/**
 * Created by czoeller on 30.04.2016.
 */
public class CategoryDAO extends ADAO<Category, CategoryDBO> {

    @Override
    Category toPOJO(CategoryDBO categoryDBO) {
        final Category category = new Category();
        category.setID(categoryDBO.getID());
        category.setName(categoryDBO.getName());
        return category;
    }

    @Override
    CategoryDBO toDBO(Category pojo) {
        CategoryDBO categoryDBO = new CategoryDBO();
        if (findById(pojo.getID()).isPresent()) {
            categoryDBO.setID(pojo.getID());
        }

        categoryDBO.setName(pojo.getName());
        return categoryDBO;
    }
}
