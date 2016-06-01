package sample.database.dao;

import sample.database.dbo.SourceDBO;
import sample.model.Source;

import java.util.Optional;

/**
 * Database Access Object for Source.
 * Does Mapping from POJO to DBO and vice versa.
 * Created by czoeller on 02.05.2016.
 */
public class SourceDAO extends ADAO<Source, SourceDBO> {

    @Override
    Source toPOJO(SourceDBO sourceDBO) {
        final Source source = new Source();
        source.setID(sourceDBO.getID());
        source.setName(sourceDBO.getName());
        return source;
    }

    @Override
    SourceDBO toDBO(Source pojo) {
        SourceDBO sourceDBO = new SourceDBO();
        Optional<Source> source = findFirst("name=?", pojo.getName());

        // if source not present in DB then insert it.
        if(!source.isPresent()){
            sourceDBO.setName(pojo.getName());
            sourceDBO.saveIt();
        }
        else {  // else read data from existing source entry in DB
            sourceDBO.setID(source.get().getID());
            sourceDBO.setName(source.get().getName());
        }
        return sourceDBO;
    }
}

