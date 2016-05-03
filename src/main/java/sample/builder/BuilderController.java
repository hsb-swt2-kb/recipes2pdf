package sample.builder;

import sample.model.ICookbook;
import sample.model.IRecipe;

import java.io.File;
import java.util.ArrayList;
import java.util.Optional;

/**
 * Created by kai on 27.04.16.
 */
public class BuilderController {
    private ArrayList<IBuilder> builders;

    public BuilderController(ArrayList<IBuilder>  builders) {
        this.builders = builders;
    }

    public File build(ICookbook cookbook, String builderOutputFiletype) throws Throwable {
        for (IBuilder builder : builders) {
            if ( builder.builds(builderOutputFiletype) ) {
                return builder.build(cookbook);
            }
        }
        return null;
    }
}
