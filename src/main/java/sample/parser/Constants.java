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
    final String[] timeMinWords = {"minuten", "min", "m"};
    final String[] timeHourWords = {"stunden","stunde","std","h"};
    final int fieldLength = 45;
    final int preparationLength = 4242;
}
