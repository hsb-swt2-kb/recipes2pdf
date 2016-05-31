package sample.builder;

import sample.config.IConfig;
import sample.model.ICookbook;
import sample.model.IRecipe;
import sample.model.ISortlevel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kai on 27.04.16.
 */
public class Builder implements IBuilder {
    private ArrayList<IConcreteBuilder> builders;

    public Builder(ArrayList<IConcreteBuilder> builders) {
        this.builders = builders;
    }

    public File build(ICookbook cookbook) throws Exception {
        for (IConcreteBuilder builder : builders) {
            if (builder.builds(IConfig.getInstance().getProperty("OUTPUT_FILETYPE"))) {
                return builder.build(cookbook);
            }
        }
        return null;
    }

    @Override
    public File build(IRecipe recipe, List<ISortlevel> sortlevels) throws Exception {
        for (IConcreteBuilder builder : builders) {
            if (builder.builds(IConfig.getInstance().getProperty("OUTPUT_FILETYPE"))) {
                return builder.build(recipe,sortlevels);
            }
        }
        return null;
    }
}
