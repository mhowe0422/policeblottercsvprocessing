package org.coa.pittsburgh.utilities.database.connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Mark
 */
public class WriteDatabaseRecords {

    private Connection con;
    Connection[] conboth = new Connection[2];
    private Statement statement;
    Statement[] statementboth = new Statement[2];
    private String sql;

    public WriteDatabaseRecords(Connection con) throws SQLException {
        //PostgresConnection pc = new PostgresConnection();
        //pc.setConnectiontype("local");
        //pc.getPostgresConnection(); // local or RDS
        //setCon(pc.getCon());
        setCon(con);

        statement = getCon().createStatement();
        if (getCon() == null || statement == null) {
            System.err.println("Database statement has not be created");
            System.exit(1);
        } else {
            System.out.println("Database statement available");
        }

    }

    public WriteDatabaseRecords(Connection[] conboth) throws SQLException {
        //PostgresConnection pc = new PostgresConnection();
        //pc.setConnectiontype("local");
        //pc.getPostgresConnection(); // local or RDS
        //setCon(pc.getCon());
        //setCon(con);

        statementboth[0] = conboth[0].createStatement();
        statementboth[1] = conboth[1].createStatement();
        if (conboth[0] == null || statementboth[0] == null) {
            System.err.println("Database statement[0] has not be created");
            System.exit(1);
        } else {
            System.out.println("Database statement[0] available");
        }
        if (conboth[1] == null || statementboth[1] == null) {
            System.err.println("Database statement[1] has not be created");
            System.exit(1);
        } else {
            System.out.println("Database statement[1] available");
        }

    }

    public void WriteSQL(String sql) throws SQLException {
        if (con == null || statement == null) {
            System.err.println("Database connection/statement has not be created");
            System.exit(1);
        }
        System.out.println("In WriteDatabaseRecords: Closed " + getStatement().isClosed());
        statement.execute(sql);
    }
    
        public void WriteSQL(String sql,boolean both) throws SQLException {

        System.out.println("In WriteDatabaseRecords: Closed " + statementboth[0].isClosed());
        System.out.println("In WriteDatabaseRecords: Closed " + statementboth[1].isClosed());
        statementboth[0].execute(sql);
        statementboth[1].execute(sql);
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }

    public Connection[] getConboth() {
        return conboth;
    }

    public void setConboth(Connection[] conboth) {
        this.conboth = conboth;
    }

    public Statement getStatement() {
        return statement;
    }

    public void closeCon() throws SQLException {
        con.close();
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Statement[] getStatementboth() {
        return statementboth;
    }

    public void setStatementboth(Statement[] statementboth) {
        this.statementboth = statementboth;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

}
