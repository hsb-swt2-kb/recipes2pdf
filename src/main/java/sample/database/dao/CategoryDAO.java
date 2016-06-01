package sample.database.dao;


import sample.database.dbo.CategoryDBO;
import sample.model.Category;

import java.util.Optional;

/**
 * Database Access Object for Category.
 * Does Mapping from POJO to DBO and vice versa.
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
        Optional<Category> category = findFirst("name=?", pojo.getName());
        if(!category.isPresent()){
            categoryDBO.setName(pojo.getName());
            categoryDBO.saveIt();
        }

        if (findById(pojo.getID()).isPresent()) {
            categoryDBO.setID(pojo.getID());
        }

        categoryDBO.setName(pojo.getName());
        return categoryDBO;
    }
}
