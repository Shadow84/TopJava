package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.UserMeal;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Created by Privat on 18.10.2016.
 */

@Repository
@Profile(Profiles.HSQLDB)
public class HsqldbJdbcUserMealRepositoryImpl extends AbstractJdbcUserMealRepositoryImpl<Timestamp> {

    @Autowired
    public HsqldbJdbcUserMealRepositoryImpl(DataSource dataSource) {
        super((rs, rowNum) ->
                new UserMeal(rs.getInt("id"), rs.getTimestamp("date_time").toLocalDateTime(),
                        rs.getString("description"), rs.getInt("calories")), dataSource
        );
    }

    @Override
    protected Timestamp toDbValue(LocalDateTime ldt) {
        return Timestamp.valueOf(ldt);
    }
}
