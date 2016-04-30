package sample.model;


import sample.model.activejdbc.CategoryDBO;

/**
 * Created by noex_ on 30.04.2016.
 */
public class CategoryDAO extends ADAO<Category, CategoryDBO> {

    @Override
    public boolean insert(Category pojo) {
        final CategoryDBO categoryDBO = toDBO(pojo);
        boolean status = categoryDBO.saveIt();
        pojo.setId( categoryDBO.getID() );
        return status;
    }

    @Override
    public boolean update(Category pojo) {
        return toDBO(pojo).saveIt();
    }

    @Override
    public boolean delete(Category pojo) {
        //return 0 < CategoryDBO.delete("name = ?", pojo.getName() );
        return toDBO(pojo).delete();
    }

    @Override
    Category toPOJO(CategoryDBO categoryDBO) {
        final Category category = new Category( categoryDBO.getID() );
        category.setName( categoryDBO.getName() );
        return category;
    }

    @Override
    CategoryDBO toDBO(Category pojo) {
        final CategoryDBO categoryDBO = new CategoryDBO();
        categoryDBO.setId(pojo.getID());
        categoryDBO.setName(pojo.getName());
        return categoryDBO;
    }
}
