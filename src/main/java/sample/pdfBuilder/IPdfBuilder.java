package sample.pdfBuilder;

import sample.model.ICookbook;
import sample.model.IRecipe;

import java.io.File;

public interface IPdfBuilder {
    File buildPDF(ICookbook cookbook);

    File buildPDF(IRecipe recipe);
}
