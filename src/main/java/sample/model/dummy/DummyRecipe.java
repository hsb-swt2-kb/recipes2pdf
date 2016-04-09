package sample.model.dummy;

import sample.model.IIngredient;
import sample.model.IRecipe;
import sample.model.IUnit;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by kai on 07.04.16.
 */
public class DummyRecipe implements IRecipe {
    static Long id;
    private String title;

    public DummyRecipe() {
        id = new Long(1);
    }
    @Override
    public boolean saveIt() {
        return true;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void add(String ingredientName, int amount, String unitName) {
    }

    @Override
    public Long getID() {
        return id;
    }


    @Override
    public Map<IIngredient, Map<Integer, IUnit>> getIngredients() {
        TreeMap<IIngredient, Map<Integer, IUnit>> map = new TreeMap<>();
        TreeMap<Integer, IUnit> innerMap = new TreeMap<Integer, IUnit>();


        IIngredient eier = IIngredient.getInstance();
        eier.setName("Eier");

        IUnit stueck = IUnit.getInstance();
        stueck.setName("Stück");

        innerMap.put(5, stueck);
        map.put(eier, innerMap);

        return map;

    }

    @Override
    public String getCategory() {
        return "Kategorie";
    }

    @Override
    public int getCategoryNumber() {
        return 1;
    }

    @Override
    public String getRecipeText() {
        String text = "";
        for (int i = 1; i <= 20; i++) {
            text += "Texstzeile " + i + "\\\\";
        }
        return text;
    }
}
