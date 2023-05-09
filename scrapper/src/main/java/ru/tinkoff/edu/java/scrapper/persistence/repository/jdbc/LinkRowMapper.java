package ru.tinkoff.edu.java.scrapper.persistence.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import org.springframework.jdbc.core.RowMapper;
import ru.tinkoff.edu.java.scrapper.persistence.model.Link;

public class LinkRowMapper implements RowMapper<Link> {
    @Override
    public Link mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Link(
                rs.getLong("id"),
                rs.getString("url"),
                OffsetDateTime.ofInstant(rs.getTimestamp("updated_at").toInstant(), ZoneId.of("UTC")),
                OffsetDateTime.ofInstant(rs.getTimestamp("checked_at").toInstant(), ZoneId.of("UTC"))
        );
    }
}
