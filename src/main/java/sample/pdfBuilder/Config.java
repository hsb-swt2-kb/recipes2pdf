package sample.pdfBuilder;

/**
 * @author Kai Nortmann
 */
interface Config {
    String WORKKING_DIRECTORY = "PdfBuilder";

    String TEMP_FOLDER_NAME = "temp";

    String TEMPLATE_FOLDER_NAME = "templates";
    String TEMPLATE_FILE_NAME = "recipeTemplate.tex";

    String OUTPUT_FOLDER_NAME = "output";
    String OUTPUT_FILE_PREFIX = "recipe";
    String OUTPUT_FILETYPE = ".tex";
}
