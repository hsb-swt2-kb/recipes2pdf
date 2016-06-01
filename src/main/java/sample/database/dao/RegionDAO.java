package sample.database.dao;

import sample.database.dbo.RegionDBO;
import sample.model.Region;

import java.util.Optional;

/**
 * Database Access Object for Region.
 * Does Mapping from POJO to DBO and vice versa.
 * Created by czoeller on 03.05.16.
 */
public class RegionDAO extends ADAO<Region, RegionDBO> {

    @Override
    Region toPOJO(RegionDBO regionDBO) {
        final Region region = new Region();
        region.setID(regionDBO.getID());
        region.setName(regionDBO.getName());
        return region;
    }

    @Override
    RegionDBO toDBO(Region pojo) {
        RegionDBO regionDBO = new RegionDBO();
        Optional<Region> region = findFirst("name=?", pojo.getName());

        // if region not present in DB then insert it.
        if(!region.isPresent()){
            regionDBO.setName(pojo.getName());
            regionDBO.saveIt();
        }
        else {  // else read data from existing region entry in DB
            regionDBO.setID(region.get().getID());
            regionDBO.setName(region.get().getName());
        }
        return regionDBO;
    }
}
