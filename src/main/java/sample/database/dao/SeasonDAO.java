package sample.database.dao;

import sample.database.dbo.SeasonDBO;
import sample.model.Season;

import java.util.Optional;

/**
 * Database Access Object for Season.
 * Does Mapping from POJO to DBO and vice versa.
 * Created by czoeller on 02.05.2016.
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
        Optional<Season> season = findFirst("name=?", pojo.getName());

        // if season not present in DB then insert it.
        if(!season.isPresent()){
            seasonDBO.setName(pojo.getName());
            seasonDBO.saveIt();
        }
        else {  // else read data from existing season entry in DB
            seasonDBO.setID(season.get().getID());
            seasonDBO.setName(season.get().getName());
        }
        return seasonDBO;
    }
}

