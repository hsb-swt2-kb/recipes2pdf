package sample.model.dao;

import sample.model.Region;
import sample.model.activejdbc.RegionDBO;

/**
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
        if (findById(pojo.getID()).isPresent()) {
            regionDBO.setID(pojo.getID());
        }

        regionDBO.setName(pojo.getName());
        regionDBO.save();
        return regionDBO;
    }
}
