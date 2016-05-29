package sample.builder;

import sample.builder.Exceptions.TexParserException;
import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.model.ISortlevel;

import java.io.File;
import java.util.List;

public interface IConcreteBuilder {
    File build(ICookbook cookbook) throws Exception, TexParserException;

    File build(IRecipe recipe, List<ISortlevel> sortLevels) throws Exception, TexParserException;
    File build(IRecipe recipe) throws Exception, TexParserException;

    boolean builds(String filetype);
}
