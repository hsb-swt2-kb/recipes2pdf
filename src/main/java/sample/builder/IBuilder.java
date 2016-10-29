package sample.builder;

import sample.builder.Exceptions.TexParserException;
import sample.model.Cookbook;
import sample.model.Recipe;
import sample.model.Sortlevel;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IBuilder {


    /**
     * This Method builds one PDF File out of the given cookbook.
     * <h1>Precondition:</h1>
     * Cookbook is not Null and has Recipes and Sortlevels(optional but recommendet)<br>
     * Recipes in the cookbook have to have all attributes, that are needed for the document building process.<br>
     * The needed attributes in recipes are:<br>
     * <ul>
     * <li>id</li>
     * <li>title</li>
     * <li>ingredients (the Ingredients have to be filled with values that are not null)</li>
     * <li>image (optional but recommendet). When no image is found, a default image will be taken instead.</li>
     * <li>text</li>
     * </ul>
     * <h1>Postcondition:</h1>
     * Document is saved on Harddrive at the configurated path relativ to  userhomedir/.recipes2pdf, that is given in the config
     *
     * @param cookbook Cookbook, that is converted into a Document
     * @throws TexParserException Is thrown, when the recipe does have a null-Attribute in one of the fields, that are needed for the Template
     * @throws IOException        Is thrown by the JLR Converter, when anything with the Filesystem went wrong while converting the template to an explicit .tex for the cookbook
     * @return: File object, that points to the generated Document
     */
    File build(Cookbook cookbook) throws TexParserException, IOException;


    /**
     * This Method generates a single Recipe Document that has a header showing the primary sort level and footer showing the referendnumber)
     * <h1>Precondition:</h1>
     * Recipe is not Null and has following Attributes filled with values: <br>
     * <ul>
     * <li>id</li>
     * <li>title</li>
     * <li>ingredients (the Ingredients have to be filled with values that are not null)</li>
     * <li>image (optional but recommendet). When no image is found, a default image will be taken instead.</li>
     * <li>text</li>
     * </ul>     *
     * <h1>Postcondition:</h1>
     * Document is saved on Harddrive at the configurated path relativ to  userhomedir/.recipes2pdf, that is given in the config
     *
     * @param recipe     The recipe, that sould be converted into a Document
     * @param sortlevels A sorted List of Sortlevel. The recipe will get a Referencenumber according to the order of this List.
     * @return File object, that points to the generated Document
     * @throws TexParserException Is thrown, when the recipe does have a null-Attribute in one of the fields, that are needed for the Template
     * @throws IOException        Is thrown by the JLR Parser, when anything with the Filesystem went wrong while parsing the PDF File
     */
    File build(Recipe recipe, List<Sortlevel> sortlevels) throws TexParserException, IOException;

    /**
     * This Method generates a single Recipe Document that has no header (normally showing the primary sort level) and no footer (normally showing the reference number)
     * <h1>Precondition:</h1>
     * Recipe is not Null and has following Attributes filled with values: <br>
     * <ul>
     * <li>id</li>
     * <li>title</li>
     * <li>ingredients (the Ingredients have to be filled with values that are not null)</li>
     * <li>image (optional but recommendet). When no image is found, a default image will be taken instead.</li>
     * <li>text</li>
     * </ul>
     * <p>
     * <h1>Postcondition:</h1>
     * Document is saved on Harddrive at the configurated path relativ to  userhomedir/.recipes2pdf, that is given in the config
     *
     * @param recipe The recipe, that sould be converted into a Document
     * @return File object, that points to the generated Document
     * @throws TexParserException Is thrown, when the recipe does have a null-Attribute in one of the fields, that are needed for the Template
     * @throws IOException        Is thrown by the JLR Parser, when anything with the Filesystem went wrong while parsing the PDF File
     */
    File build(Recipe recipe) throws TexParserException, IOException;
}
