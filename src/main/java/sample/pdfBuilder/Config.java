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
    String OUTPUT_TEX_FILETYPE = ".tex";
    String OUTPUT_PDF_FILETYPE = ".pdf";

    String IMAGE_PREFIX = "image_";
    String IMAGE_FILETYPE = ".jpg";

    String RESSOURCE_PATH = "sample" + File.separator + "pdfBuilder";
    String RESSOURCE_USER_PATH = System.getProperty("user.dir") + File.separator + ".recipes2pdf" + File.separator + "ressources";
}
