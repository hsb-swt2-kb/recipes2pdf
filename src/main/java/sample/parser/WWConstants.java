package sample.parser;

/**
 * implemented by fpfennig on 18.05.2016
 */
interface WWConstants {
    /**
     * constants
     */
    int    maxFieldSize            = 45;

    String noon                    = "Mittag";
    String evening                 = "Abend";
    String meal                    = "essen";

    String[] keywords              = {  "Frühstück", "Fruehstueck", "Beilage", noon, evening,
        "frühstück", "fruehstueck", "beilage", "mittag",
        "abend" };

    String version2016LineBreaks = "<br>";
    String replaceSpaces           = "(&nbsp;)|( )|(\\t)|(\\h)";
    String numberWithCharacters    = "^(\\d)+([.,]\\d)*([äüöÄÖÜ\\w])*";
    String notANumber              = "([^\\d])+";
    String onlyNumber              = "(\\d)+";
    String separator               = " / ";

    String imgTag                  = "img";
    String srcAttr                 = "src";
    String servingsTag1            = "Portionen: ";
    String servingsTag2            = "Person";
    String ingredientsTag          = "Zutaten";
    String preparingTag            = "Zubereitung";
    String listTag                 = "li";
    String inputTag                = "input";
    String nameAttr                = "name";
    String valueAttr               = "value";
    String scriptTag               = "script";

    // Version statics
    String version2015Name         = "ucFooter:hdnWWVersion";
    String version2015             = "14.8.10.1008";
    String version2016             = "site_version: \"2.0\"";

    // Version 2015
    String name2015                = "div.article-intro";
    String image2015               = "div.article-img";
    String preparingTime2015       = "div.prep-time";
    String type2015                = "div.recipe-desc";
    String ingredientsAndDescr2015 = "div.recipe_int";

    // Version 2016
    String htmlDoc2016             = "div.rich-text";
    String name2016                = "h3";
    String image2016               = "div.field-items";
    String ingredients2016         = "p";
    String tableTd                 = "td";
}
