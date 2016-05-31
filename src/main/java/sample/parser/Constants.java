package sample.parser;

public interface Constants {

    //Sigalwörter
    final String[] signalwoerter =
        {
            "Name",
            "Zutaten",
            "Zubereitung",
            "Kategorie",
            "Arbeitszeit",
            "Gerichtsart",
            "Portionen",
            "Kalorien",
            "Region",
            "Tageszeit",
            "Saison",
            "Ernährungsart"
        };
    //Triggerwords to convert between Time-Units
    final String[] timeMinWords = {"minuten", "min", "m"};
    final String[] timeHourWords = {"stunden","stunde","std","h"};
    //Maximum allowed Data-Length
    final int fieldLength = 45;
    final int preparationLength = 4242;
}
