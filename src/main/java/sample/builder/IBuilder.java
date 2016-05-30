package sample.builder;

import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.model.ISortlevel;

import java.io.File;
import java.util.List;

public interface IBuilder {
    File build(ICookbook cookbook) throws Exception;

    File build(IRecipe recipe, List<ISortlevel> sortlevels) throws Exception;
}
