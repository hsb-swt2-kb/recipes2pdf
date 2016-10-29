package sample.ui;

/**
 * implemented by on 24.05.16 by markus
 * These tests are failing because they don't use the Testdatabase.
 * Each statement on the Testdatabase is wrapped into a transaction that is rolled back after the test.
 * Depending on the order of execution of these tests they are not predictable.
 * As UI has static methods that create a new database connection on their own it is not easy to change to use the
 * normal database in production and test database for unit tests.
 * (Fix would be: Use ADatabaseTest & change static methods of UI to public methods.
 * A constructor for UI would be passed a Database or DatabaseConnection)
 */
public class UITest {
/*    @Test
    public void addRecipePositive() throws Exception {
        File file = new File("src/test/resources/sample/Rezepte/Bolognese.txt");
        the(UI.addRecipe(file)).shouldBeTrue();
    }

    @Test
    public void addRecipeNegative() throws Exception {
        // working dir = src root dir
        File file = new File("src/test/resources/sample/Rezepte/keinRezept.txt");
        UI.addRecipe(file);
    }

    @Test
    public void addRecipesPositive() throws Exception {
        List<File> files = new ArrayList<>();
        files.add(new File("src/test/resources/sample/Rezepte/Bolognese.txt"));
        files.add(new File("src/test/resources/sample/Rezepte/ChurryChekoch.html"));
        files.add(new File("src/test/resources/sample/Rezepte/Asia-Wokgemuese.html"));
        the(UI.addRecipes(files)).shouldBeTrue();
    }

    @Test
    public void addRecipesNegative() throws Exception {
        List<File> files = new ArrayList<>();
        files.add(new File("src/test/resources/sample/Rezepte/ChurryChekoch.html"));
        files.add(new File("src/test/resources/sample/Rezepte/keinRezept.html"));
        UI.addRecipes(files);
    }

    @Test
    public void delRecipes() throws Exception {
        this.addRecipePositive();
        the(new RecipeDAO().delete(new RecipeDAO().getAll().get(0))).shouldBeTrue();
    }

    @Test
    public void createCookBook() throws Exception {
        Cookbook cookbook = new Cookbook();
        cookbook.setTitle("Testkochbuch");
        //TODO the(new CookbookDAO().insert(cookbook)).shouldBeTrue();
    }
    @Test
    public void updateRecipe() throws Exception {
        List<Recipe> recipes = new RecipeDAO().getAll();
        Recipe recipe = recipes.get(0);
        recipe.setTitle("Testtiteländerung");
        the(UI.updateRecipe(recipe)).shouldBeTrue();
    }

    @Test
    public void getAllRecipesFromDB() {
        int a = UI.getAllRecipesFromDB().size();
        int b = new RecipeDAO().getAll().size();
        the(a).shouldBeEqual(b);
    }

    @Test
    public void getAllCookbooksFromDB() throws Exception {
        the(UI.getAllCookbooksFromDB()).shouldBeEqual(new CookbookDAO().getAll().size());
    }

    @Test
    public void castRecipeToRecipe() throws Exception {
    }

    @Test
    public void castCookbookToCookBook() throws Exception {
    }

    @Test
    public void addCookBook() throws Exception {
        Cookbook cookbook = new Cookbook();
        final String title = "asdf";
        cookbook.setTitle(title);
        UI.addCookBook(cookbook);
        final Optional<Cookbook> byId = new CookbookDAO().findFirst("title = ?", title);
        byId.orElseThrow(IllegalStateException::new);
        the(byId.get().getTitle()).shouldBeEqual(title);
    }

    @Test
    public void delCookBook() throws Exception {
        this.addCookBook();
        the(new RecipeDAO().delete(new RecipeDAO().getAll().get(0))).shouldBeTrue();
    }

    @Test
    public void changeCookBook() throws Exception {
        this.addCookBook();
        Cookbook cookbook = new CookbookDAO().getAll().get(0);
        cookbook.setTitle("blubb");
        the(UI.changeCookBook(cookbook)).shouldBeTrue();
    }

    @Test
    public void changeRecipe() throws Exception {
        this.addRecipePositive();
        Recipe recipe = new RecipeDAO().getAll().get(0);
        recipe.setTitle("blubb");
        the(UI.changeRecipe(recipe)).shouldBeTrue();
    }

    @Test
    public void delRecipe() throws Exception {
        this.addCookBook();
        Recipe recipe = new RecipeDAO().getAll().get(0);
        the(UI.delRecipe(recipe)).shouldBeTrue();
    }

    @Test
    public void searchCookBook() throws Exception {
        Cookbook cookbook = new CookbookDAO().getAll().get(0);
        the(UI.searchCookBook(cookbook.getTitle())).shouldBeTrue();
    }

    @Test
    public void searchRecipe() throws Exception {
        Recipe recipe = new RecipeDAO().getAll().get(0);
        the(UI.searchRecipe(recipe.getTitle()).getTitle().equals(recipe.getTitle())).shouldBeTrue();
    }
    */
}
