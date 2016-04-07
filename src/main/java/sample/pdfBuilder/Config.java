package sample.pdfBuilder;

import java.io.File;

/**
 * @author Kai Nortmann
 */
interface Config {
    String WORKKING_DIRECTORY = "PdfBuilder";

    String TEMP_FOLDER_NAME = "temp";

    String IMAGE_FOLDER_NAME = "images";

    String TEMPLATE_FOLDER_NAME = "templates";
    String TEMPLATE_FILE_NAME = "recipeTemplate.tex";

    String OUTPUT_FOLDER_NAME = "output";
    String OUTPUT_FILE_PREFIX = "recipe";
    String OUTPUT_FILETYPE = ".tex";

    String IMAGE_PREFIX = "image_";
    String IMAGE_FILETYPE = ".jpg";

    String RESSOURCEPATH = "sample" + File.separator + "pdfBuilder" + File.separator;
}
