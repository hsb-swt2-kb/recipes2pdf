package sample.pdfBuilder;

import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.pdfBuilder.exceptions.ConvertTemplatetoTexFailedException;
import sample.pdfBuilder.exceptions.GeneratePdfFailedException;

import java.io.File;
import java.io.IOException;

public interface IPdfBuilder {
    File buildPDF(ICookbook cookbook) throws IOException, GeneratePdfFailedException, ConvertTemplatetoTexFailedException;

    File buildPDF(IRecipe recipe) throws ConvertTemplatetoTexFailedException, IOException, GeneratePdfFailedException;
}
