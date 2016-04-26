package sample.converter;

import sample.model.ICookbook;
import sample.model.IRecipe;

import java.io.File;

public interface IConverter {
    File buildPDF(ICookbook cookbook) throws Exception;

    File buildPDF(IRecipe recipe) throws Exception;
}
