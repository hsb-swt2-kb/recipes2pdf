package sample.pdfBuilder;

import sample.config.IConfiguration;

import java.io.File;

/**
 * Created by kai on 11.04.16.
 */
public class PdfBuilderConfig {

    public File getParserRootDir() {
        return new File(IConfiguration.PROGRAM_USERDATA_DIR);
    }

    public File getImageDir() {
        return new File(getParserRootDir() + File.separator + IConfiguration.getInstance().getProperty("IMAGE_FOLDER_NAME"));
    }

    public File getOutputImage(Long ImageID) {
        ImageID = new Long(1); //TODO: Just for testing, delete later, when recipes come out of database
        return new File(getImageDir() + File.separator + "image_" + ImageID + "_out.jpg");
    }

    public File getInputImage(Long ImageID) { //TODO: MEthod just for testing purposes, Image will come out of database later
        ImageID = new Long(1); //TODO: Just for testing, delete later, when recipes come out of database
        return new File(getImageDir() + File.separator + "image_" + ImageID + ".jpg");
    }

    public File getTempDir() {
        return new File(getParserRootDir() + File.separator + IConfiguration.getInstance().getProperty("TEMP_FOLDER_NAME"));
    }

    public File getTemplateDir() {
        return new File(getParserRootDir() + File.separator + IConfiguration.getInstance().getProperty("TEMPLATE_FOLDER_NAME"));
    }

    public File getUserTemplate() {
        return new File(getTemplateDir() + File.separator + IConfiguration.getInstance().getProperty("TEMPLATE_FILE_NAME"));
    }

    public File getOutputDir() {
        return new File(getParserRootDir() + File.separator + IConfiguration.getInstance().getProperty("OUTPUT_FOLDER_NAME"));
    }

    public File getOutputTexFile(String cookbookName) {
        return new File(getOutputDir() + File.separator + cookbookName + ".tex");
    }

    public File getOutputPdfFile(String cookbookName) {
        return new File(getOutputDir() + File.separator + cookbookName + ".pdf");
    }
}
