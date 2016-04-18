package sample.pdfBuilder;

import sample.config.IConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by kai on 11.04.16.
 */
public class PdfBuilderConfig {

    public File getParserRootDir() {
        return checkDir(new File(IConfiguration.PROGRAM_USERDATA_DIR));
    }

    public File getImageDir() {
        return checkDir(new File(getParserRootDir() + File.separator + IConfiguration.getInstance().getProperty("IMAGE_FOLDER_NAME")));
    }

    public File getOutputImage(Long ImageID) throws IOException {
        ImageID = new Long(1); //TODO: Just for testing, delete later, when recipes come out of database
        return new File(getImageDir() + File.separator + "image_" + ImageID + "_out.jpg");
    }

    public File getInputImage() throws IOException { //TODO: MEthod just for testing purposes, Image will come out of database later
        return new File(this.getClass().getClassLoader().getResource("sample/pdfBuilder/images/default_image.jpg").getPath());
    }

    public File getTempDir() {
        return checkDir(new File(getParserRootDir() + File.separator + IConfiguration.getInstance().getProperty("TEMP_FOLDER_NAME")));
    }

    public File getTemplateDir() {
        return checkDir(new File(getParserRootDir() + File.separator + IConfiguration.getInstance().getProperty("TEMPLATE_FOLDER_NAME")));
    }

    public File getTemplateFile() throws IOException {
        File defaultTemplate = new File(this.getClass().getClassLoader().getResource("sample/pdfBuilder/templates/cookbookTemplate.tex").getPath());
        File userTemplate = getUserTemplate();
        try {
            if (!userTemplate.exists()) {
                Files.copy(defaultTemplate.toPath(), userTemplate.toPath());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userTemplate;
    }

    public File getUserTemplate() throws IOException {
        return new File(getTemplateDir() + File.separator + IConfiguration.getInstance().getProperty("TEMPLATE_FILE_NAME"));
    }

    public File getOutputDir() {
        return checkDir(new File(getParserRootDir() + File.separator + IConfiguration.getInstance().getProperty("OUTPUT_FOLDER_NAME")));
    }

    public File getOutputTexFile(String cookbookName) throws IOException {
        return new File(getOutputDir() + File.separator + cookbookName + ".tex");
    }

    public File getOutputPdfFile(String cookbookName) throws IOException {
        return new File(getOutputDir() + File.separator + cookbookName + ".pdf");
    }

    private File checkDir(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

}
