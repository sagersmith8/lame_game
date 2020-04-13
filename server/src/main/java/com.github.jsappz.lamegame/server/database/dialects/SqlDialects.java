package com.github.jsappz.lamegame.server.database.dialects;

import com.github.jsappz.lamegame.server.database.SqlDialect;

import static java.util.Objects.requireNonNull;

public enum SqlDialects {
    MY_SQL(new MySqlDialect()),
    STANDARD(new StandardDialect());

    private SqlDialect dialect;

    SqlDialects(SqlDialect dialect) {
        this.dialect = requireNonNull(dialect, "dialect");
    }

    public SqlDialect get() {
        return dialect;
    }
}
