package sample.parser;

import java.util.List;

/**
 * implemented by on 08.06.16 by markus
 */
abstract class AHTMLParser extends AConcreteParser {

  HTMLParserLib lib = new HTMLParserLib();

  /**
   * Method to put the IngredientsList into the recipe.
   *
   * @param ingredientsList ArrayList<String[]> containing all the ingredients split into amount, unit and ingredient.
   */
  void setRecipeIngredientsList(List<String[]> ingredientsList){
    for (String[] ingredient : ingredientsList) {
      if(ingredient[2] != null) {
        if (ingredient[0].isEmpty()) {
          ingredient[0] = "0";
        }

        if (ingredient[1] == null) {
          ingredient[1] = "";
        }

        double amount;
        try {
          amount = Double.parseDouble(ingredient[0]);
          amount = lib.round(amount);
          recipe.add(ingredient[2], amount, ingredient[1]);
        } catch (Exception e) {
          // TODO: exception handling? maybe ignore?
        }
      }
    }
  }
}
