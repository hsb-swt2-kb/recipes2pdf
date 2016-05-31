package sample.database.dao;

import sample.database.dbo.SourceDBO;
import sample.model.Source;

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
        if (findById(pojo.getID()).isPresent()) {
            sourceDBO.setID(pojo.getID());
        }

        sourceDBO.setName(pojo.getName());
        return sourceDBO;
    }
}

