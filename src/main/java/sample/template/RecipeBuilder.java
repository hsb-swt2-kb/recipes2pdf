package sample.template;

import de.nixosoft.jlr.JLRConverter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by czoeller on 31.03.16.
 */
public class RecipeBuilder {
    private File workingDirectory = new File("C:" + File.separator + "Invoices");
    JLRConverter converter = new JLRConverter(workingDirectory);
    private File template = new File(workingDirectory.getAbsolutePath() + File.separator + "invoiceTemplate.tex");
    if(!tempDir.isDirectory())
    private File tempDir = new File(workingDirectory.getAbsolutePath() + File.separator + "temp");
    File invoice1 = new File(tempDir.getAbsolutePath() + File.separator + "invoice1.tex");
    File invoice2 = new File(tempDir.getAbsolutePath() + File.separator + "invoice2.tex");
    private ArrayList<ArrayList<String>> services = new ArrayList<ArrayList<String>>();

    converter.replace("Number","1")
        converter.replace("CustomerName","Ivan Pfeiffer")
        converter.replace("CustomerStreet","Schwarzer Weg 4")
        converter.replace("CustomerZip","13505 Berlin")
    private ArrayList<String> subservice1 = new ArrayList<String>();
    private ArrayList<String> subservice2 = new ArrayList<String>();
    private ArrayList<String> subservice3 = new ArrayList<String>();

    {
        tempDir.mkdir();
    }

    subservice1.add("Software")
        subservice1.add("50")
        subservice2.add("Hardware1")
        subservice2.add("500")
        subservice3.add("Hardware2")
        subservice3.add("850")

        services.add(subservice1)
        services.add(subservice2)
        services.add(subservice3)

        converter.replace("services",services)
}
