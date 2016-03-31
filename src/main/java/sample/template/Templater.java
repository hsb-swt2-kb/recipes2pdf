package sample.template;

import de.nixosoft.jlr.JLRConverter;

import java.io.File;

/**
 * Created by czoeller on 31.03.16.
 */
public class Templater {

    public Templater() {

        File workingDirectory = new File("C:" + File.separator + "Invoices");

        File template = new File(workingDirectory.getAbsolutePath() + File.separator + "invoiceTemplate.tex");

        File tempDir = new File(workingDirectory.getAbsolutePath() + File.separator + "temp");
        if (!tempDir.isDirectory()) {
            tempDir.mkdir();
        }
        File invoice1 = new File(tempDir.getAbsolutePath() + File.separator + "invoice1.tex");
        File invoice2 = new File(tempDir.getAbsolutePath() + File.separator + "invoice2.tex");

        JLRConverter converter = new JLRConverter(workingDirectory);
    }
}
