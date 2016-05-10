package sample.builder;

import sample.model.ICookbook;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by kai on 27.04.16.
 */
public class Builder {
    private ArrayList<IConcreteBuilder> builders;

    public Builder(ArrayList<IConcreteBuilder>  builders) {
        this.builders = builders;
    }

    public File build(ICookbook cookbook, String builderOutputFiletype) throws Throwable {
        for (IConcreteBuilder builder : builders) {
            if ( builder.builds(builderOutputFiletype) ) {
                return builder.build(cookbook);
            }
        }
        return null;
    }
}
