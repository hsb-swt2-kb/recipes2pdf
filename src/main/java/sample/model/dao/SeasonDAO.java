package sample.model.dao;

import sample.model.Season;
import sample.model.activejdbc.SeasonDBO;

/**
 * Created by noex_ on 02.05.2016.
 */
public class SeasonDAO extends ADAO<Season, SeasonDBO> {

    @Override
    Season toPOJO(SeasonDBO seasonDBO) {
        final Season season = new Season();
        season.setID(seasonDBO.getID());
        season.setName(seasonDBO.getName());
        return season;
    }

    @Override
    SeasonDBO toDBO(Season pojo) {
        SeasonDBO seasonDBO = new SeasonDBO();
        if (findById(pojo.getID()).isPresent()) {
            seasonDBO.setID(pojo.getID());
        }

        seasonDBO.setName(pojo.getName());
        return seasonDBO;
    }
}

