package sample.builder;

import sample.config.IConfig;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import static org.apache.commons.io.IOUtils.toByteArray;

/**
 * This class is an abstractionlayer between Iconfig and PDFBuilder. It converts the Strings of the IConfig
 * Properties into suitable objects that are needed for the PdfBuilder
 */
public class PdfBuilderConfig {

    IConfig baseConfig;

    public PdfBuilderConfig(IConfig config) {
        this.baseConfig = config;
    }

    public File getParserRootDir() {
        return checkDir(new File(baseConfig.getProperty("PROGRAM_USERDATA_DIR")));
    }

    public File getImageDir() {
        return checkDir(new File(getParserRootDir() + File.separator + baseConfig.getProperty("IMAGE_FOLDER_NAME")));
    }

    public byte[] getDefaultImage() throws IOException {
        return toByteArray(this.getClass().getResourceAsStream("images/default_image.jpg"));
    }

    public File getTempDir() {
        return checkDir(new File(getParserRootDir() + File.separator + baseConfig.getProperty("TEMP_FOLDER_NAME")));
    }

    public File getTemplateDir() {
        return checkDir(new File(getParserRootDir() + File.separator + baseConfig.getProperty("TEMPLATE_FOLDER_NAME")));
    }

    public File getTemplateFile() {
        final InputStream in = this.getClass().getResourceAsStream("templates/cookbookTemplate.tex");
        File userTemplate = getUserTemplate();
        String templateName = userTemplate.getName();
        try {
            if (!userTemplate.exists() && templateName.equals("cookbookTemplate.tex")) {
                Files.copy(in, userTemplate.toPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userTemplate;
    }

    private File getUserTemplate() {
        return new File(getTemplateDir() + File.separator + baseConfig.getProperty("TEMPLATE_FILE_NAME"));
    }

    public File getOutputDir() {
        return checkDir(new File(getParserRootDir() + File.separator + baseConfig.getProperty("OUTPUT_FOLDER_NAME")));
    }

    public File getOutputTexFile(String cookbookName) {
        return new File(getOutputDir() + File.separator + cookbookName + ".tex");
    }

    public File getOutputPdfFile(String cookbookName) {
        return new File(getOutputDir() + File.separator + cookbookName + ".pdf");
    }

    private File checkDir(File dir) {
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

}
