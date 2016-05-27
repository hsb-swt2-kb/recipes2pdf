package sample.builder;

import org.javalite.activejdbc.Configuration;
import org.junit.Before;
import org.junit.Test;
import sample.config.IConfig;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by kai on 26.05.16.
 */
public class PdfBuilderConfigTest {

        private PdfBuilderConfig config;
        //needed, because the path to user home dir is different for every user
        private String userdir = System.getProperty("user.home") + File.separator;

    @Before
        public void testSetProperties() {
            IConfig myconfig = IConfig.getInstance();
            myconfig.setProperty("TEMP_FOLDER_NAME", "temp");
            myconfig.setProperty("IMAGE_FOLDER_NAME", "images");
            myconfig.setProperty("TEMPLATE_FOLDER_NAME", "templates");
            myconfig.setProperty("TEMPLATE_FILE_NAME", "cookbookTemplate.tex");
            myconfig.setProperty("OUTPUT_FOLDER_NAME", "output");
            myconfig.setProperty("OUTPUT_FILE_PREFIX", "recipe");
            myconfig.setProperty("IMAGE_PREFIX", "image_");

            config = new PdfBuilderConfig(myconfig);
        }


        @Test
        public void getParserRootDir () {
            assertEquals(userdir + ".recipes2pdf", config.getParserRootDir().getPath());
            assertTrue(new File(userdir + ".recipes2pdf").exists());
        }

        @Test
        public void getImageDir () {
            assertEquals(userdir + ".recipes2pdf",config.getParserRootDir().getPath());
            assertTrue(new File(userdir + ".recipes2pdf" + File.separator + "images").exists());
        }
        @Test
        public void getImage (){
            assertEquals(userdir + ".recipes2pdf" + File.separator + "images"+ File.separator +"tmpRecipeImage.jpg",config.getImage("tmpRecipeImage.jpg").getAbsolutePath());
        }
        @Test
        public void getDefaultImage () {
            // method and test do the same, but since the ressourcepath is dynamic for each user, there is no other way to 'test' it
            assertEquals(this.getClass().getClassLoader().getResource("sample/builder/images/default_image.jpg").getPath(), config.getDefaultImage().getAbsolutePath());
        }
        @Test
        public void getTempDir () {
            assertEquals(userdir + ".recipes2pdf" + File.separator +"temp",config.getTempDir().getPath());
            assertTrue(new File(userdir + ".recipes2pdf" + File.separator +"temp").exists());
        }
        @Test
        public void getTemplateDir () {
            assertEquals(userdir + ".recipes2pdf" + File.separator +"templates", config.getTemplateDir().getPath());
            assertTrue(new File(userdir + ".recipes2pdf" + File.separator +"templates").exists());
        }
        @Test
        public void getTemplateFile (){
            assertEquals(userdir + ".recipes2pdf" + File.separator + "templates"+ File.separator +"cookbookTemplate.tex", config.getTemplateFile().getAbsolutePath());
        }

        @Test
        public void getOutputDir () {
            assertEquals(userdir + ".recipes2pdf" + File.separator +"output", config.getOutputDir().getPath());
            assertTrue(new File(userdir + ".recipes2pdf" + File.separator +"templates").exists());
        }
        @Test
        public void getOutputTexFile (){
            assertEquals(userdir + ".recipes2pdf" + File.separator + "output"+ File.separator +"TestCookbook.tex", config.getOutputTexFile("TestCookbook").getAbsolutePath());
        }
        @Test
        public void getOutputPdfFile (){
            assertEquals(userdir + ".recipes2pdf" + File.separator + "output"+ File.separator +"TestCookbook.pdf", config.getOutputPdfFile("TestCookbook").getAbsolutePath());
        }
    }
