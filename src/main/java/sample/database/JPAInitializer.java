package sample.database;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;

/**
 * Created by czoeller on 23.07.16.
 */
public class JPAInitializer {
    @Inject
    JPAInitializer(PersistService service) {
        service.start();
        // At this point JPA is started and ready.
    }
}
