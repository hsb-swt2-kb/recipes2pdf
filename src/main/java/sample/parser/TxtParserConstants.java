package sample.parser;

public interface TxtParserConstants {

  /**
   * SignalWords for the attributes of the recipe
   */
  //SignalWords
    String[] signalWord =
        {
            "Name",        // 0
            "Zutaten",     // 1
            "Zubereitung", // 2
            "Kategorie",   // 3
            "Arbeitszeit", // 4
            "Gerichtsart", // 5
            "Portionen",   // 6
            "Kalorien",    // 7
            "Region",      // 8
            "Tageszeit",   // 9
            "Saison",      // 10
            "Ernährungsart"// 11
        };

    //TriggerWords to convert between Time-Units
    String[] timeMinWords = {"minuten", "min", "m"};
    String[] timeHourWords = {"stunden","stunde","std","h"};

    String ingredientMarker = "-";
    String attributeMarker = ":";

    //Maximum allowed Data-Length
    int fieldLength = 45;
    int preparationLength = 4242;
}
