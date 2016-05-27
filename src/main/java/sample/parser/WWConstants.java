package sample.parser;

/**
 * Created by fpfennig on 18.05.2016.
 */
public interface WWConstants {
    // Constants
    public static final String noon                    = "Mittag";
    public static final String evening                 = "Abend";
    public static final String meal                    = "essen";

    public static final String[] keywords              = {  "Frühstück", "Fruehstueck", "Beilage", noon, evening,
                                                            "frühstück", "fruehstueck", "beilage", "mittag",
                                                            "abend" };

    public static final String replaceSpaces           = "(\\h)|(\\t)";
    public static final String numberWithCharacters    = "^(\\d)+([.,]\\d)*(\\w)*";
    public static final String charactersWithoutNumbers = "([^.,\\d])+";
    public static final String notANumber              = "([^\\d])+";
    public static final String onlyNumber              = "(\\d)+";
    public static final String numberSeperator         = "[.,]";
    public static final String separator               = " / ";

    public static final String imgTag                  = "img";
    public static final String srcAttr                 = "src";
    public static final String servingsTag1            = "Portionen: ";
    public static final String servingsTag2            = "Person";
    public static final String ingredientsTag          = "Zutaten";
    public static final String preparingTag            = "Zubereitung";
    public static final String listTag                 = "li";
    public static final String inputTag                = "input";
    public static final String nameAttr                = "name";
    public static final String valueAttr               = "value";
    public static final String scriptTag               = "script";

    // Version statics
    public static final String version2015Name         = "ucFooter:hdnWWVersion";
    public static final String version2015             = "14.8.10.1008";
    public static final String version2016             = "site_version: \"2.0\"";

    // Version 2015
    public static final String name2015                = "div.article-intro";
    public static final String image2015               = "div.article-img";
    public static final String preparingTime2015       = "div.prep-time";
    public static final String type2015                = "div.recipe-desc";
    public static final String ingredientsAndDescr2015 = "div.recipe_int";

    // Version 2016
    public static final String htmlDoc2016             = "div.rich-text";
    public static final String name2016                = "h3";
    public static final String image2016               = "div.field-items";
    public static final String ingredients2016         = "p";
    public static final String tableTd                 = "td";
}
