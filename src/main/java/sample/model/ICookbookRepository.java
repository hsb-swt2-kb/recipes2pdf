package sample.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by czoeller on 30.03.16.
 */
public interface ICookbookRepository {
    List<ICookbook> getCookbooks();
    Optional<ICookbook> getCookbookById(long id );
}
