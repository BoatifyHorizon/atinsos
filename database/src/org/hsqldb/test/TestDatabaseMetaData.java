/* Copyright (c) 2001-2024, The HSQL Development Group
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * Neither the name of the HSQL Development Group nor the names of its
 * contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL HSQL DEVELOPMENT GROUP, HSQLDB.ORG,
 * OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */


package org.hsqldb.test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hsqldb.jdbc.JDBCDatabaseMetaData;

public class TestDatabaseMetaData extends TestBase {

    public TestDatabaseMetaData(String name) {
        super(name);
    }

    public void setUp() throws Exception {
        super.setUp();
    }

    public void testOne() throws Exception {

        Connection        conn = newConnection();
        PreparedStatement pstmt;
        int               updateCount;

        try {
            pstmt = conn.prepareStatement("DROP TABLE t1 IF EXISTS CASCADE");

            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(
                "CREATE TABLE t1 (cha CHARACTER, dec DECIMAL, doub DOUBLE, lon BIGINT, \"IN\" INTEGER, sma SMALLINT, tin TINYINT, "
                + "dat DATE DEFAULT CURRENT_DATE, tim TIME DEFAULT CURRENT_TIME, timest TIMESTAMP DEFAULT CURRENT_TIMESTAMP );");
            updateCount = pstmt.executeUpdate();

            assertEquals("expected update count of zero", 0, updateCount);

            pstmt = conn.prepareStatement("CREATE INDEX t1 ON t1 (cha );");
            updateCount = pstmt.executeUpdate();
            pstmt       = conn.prepareStatement("DROP TABLE t2 IF EXISTS CASCADE");
            updateCount = pstmt.executeUpdate();
            pstmt = conn.prepareStatement(
                "CREATE TABLE t2 (cha CHARACTER, dec DECIMAL, doub DOUBLE, lon BIGINT, \"IN\" INTEGER, sma SMALLINT, tin TINYINT, "
                + "dat DATE DEFAULT CURRENT_DATE, tim TIME DEFAULT CURRENT_TIME, timest TIMESTAMP DEFAULT CURRENT_TIMESTAMP );");
            updateCount = pstmt.executeUpdate();
            pstmt = conn.prepareStatement("CREATE INDEX t2 ON t2 (cha );");
            updateCount = pstmt.executeUpdate();

            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet        rsp  = dbmd.getTablePrivileges(null, null, "T1");

            while (rsp.next()) {
                System.out.println("Table: " + rsp.getString(3) + " priv: "
                                   + rsp.getString(6));
            }

            rsp = dbmd.getIndexInfo(null, null, "T1", false, false);

            while (rsp.next()) {
                System.out.println("Table: " + rsp.getString(3)
                                   + " IndexName: " + rsp.getString(6));
            }

            rsp = dbmd.getIndexInfo(null, null, "T2", false, false);

            while (rsp.next()) {
                System.out.println("Table: " + rsp.getString(3)
                                   + " IndexName: " + rsp.getString(6));
            }

            pstmt       = conn.prepareStatement("DROP INDEX t2;");
            updateCount = pstmt.executeUpdate();
            rsp         = dbmd.getIndexInfo(null, null, "T2", false, false);

            assertFalse("expected getIndexInfo returns empty resultset", rsp.next());

            ResultSet rs = dbmd.getTables(null, null, "T1",
                                          new String[]{ "TABLE" });
            ArrayList tablesarr = new ArrayList();
            int       i;

            for (i = 0; rs.next(); i++) {
                String tempstr =
                    rs.getString("TABLE_NAME").trim().toLowerCase();

                tablesarr.add(tempstr);
            }

            rs.close();
            assertEquals("expected table t1 count of 1", 1, i);

            Iterator it = tablesarr.iterator();

            while (it.hasNext()) {

                // create new ArrayList and HashMap for the table
                String tablename = ((String) it.next()).trim();
                List   collist   = new ArrayList(30);

                rs = dbmd.getColumns(null, null, tablename.toUpperCase(),
                                     null);

                for (i = 0; rs.next(); i++) {
                    collist.add(
                        rs.getString("COLUMN_NAME").trim().toLowerCase());
                }

                rs.close();
            }

            pstmt = conn.prepareStatement("DROP TABLE t_1 IF EXISTS CASCADE");

            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement(
                "CREATE TABLE t_1 (cha CHARACTER(10), deci DECIMAL(10,2), doub DOUBLE, lon BIGINT, \"IN\" INTEGER, sma SMALLINT, tin TINYINT, "
                + "dat DATE DEFAULT CURRENT_DATE, tim TIME DEFAULT CURRENT_TIME, timest TIMESTAMP DEFAULT CURRENT_TIMESTAMP, bool BOOLEAN );");
            updateCount = pstmt.executeUpdate();

            assertEquals("expected update count of zero", 0, updateCount);

            rs = dbmd.getTables(null, null, "T\\_1", new String[]{ "TABLE" });

            for (i = 0; rs.next(); i++) {
                String tempstr =
                    rs.getString("TABLE_NAME").trim().toLowerCase();

                tablesarr.add(tempstr);
            }

            rs.close();

            if (i != 1) {
                System.out.println("expected table t_1 count of 1 but was " + i);
            }

            assertEquals("expected table t_1 count of 1", 1, i);

            // test various methods
            dbmd.getPrimaryKeys(null, null, "T_1");
            dbmd.getImportedKeys(null, null, "T_1");
            dbmd.getCrossReference(null, null, "T_1", null, null, "T_1");

            // test ResultSetMetaData
            pstmt = conn.prepareStatement(
                "INSERT INTO T_1 (cha, deci, doub) VALUES ('name', 10.23, 0)");

            pstmt.executeUpdate();
            pstmt.close();

            pstmt = conn.prepareStatement("SELECT * FROM T_1");
            rs    = pstmt.executeQuery();

            ResultSetMetaData md = rs.getMetaData();
            int               x  = md.getColumnDisplaySize(1);
            int               y  = md.getColumnDisplaySize(2);
            int               b  = md.getPrecision(2);
            int               c  = md.getScale(1);
            int               d  = md.getScale(2);
            String            e  = md.getColumnClassName(10);
            boolean testresult = (x == 10) && (y == 12) && (b == 10)
                                 && (c == 0) && (d == 2)
                                 && e.equals("java.sql.Timestamp");

            assertTrue("wrong result metadata", testresult);

            e          = md.getColumnClassName(11);
            testresult = e.equals("java.lang.Boolean");

            assertTrue("wrong result metadata", testresult);
            pstmt.close();

            //
        } catch (Exception e) {
            fail("unable to prepare or execute DDL");
        } finally {
            conn.close();
        }
    }

    /**
     * Basic test of DatabaseMetaData functions that access system tables
     */
    public void testTwo() throws Exception {

        Connection conn = newConnection();
        int        updateCount;
        ResultSet  result = null;
        String     s = "";

        try {
            TestUtil.testScript(conn, "testrun/hsqldb/TestSelf01Function.txt");
            TestUtil.testScript(conn, "testrun/hsqldb/TestSelf01Procedure.txt");

            DatabaseMetaData dbmeta = conn.getMetaData();

            dbmeta.allProceduresAreCallable();
            result = dbmeta.getBestRowIdentifier(null, null, "T_1",
                                        DatabaseMetaData.bestRowTransaction,
                                        true);
            result = dbmeta.getCatalogs();
            result = dbmeta.getColumnPrivileges(null, "PUBLIC", "T_1", "%");

            while(result.next()) {
                s = result.getString(3);
            }
            result = dbmeta.getColumns("PUBLIC", "PUBLIC", "T_1", "%");
            result = dbmeta.getCrossReference(null, null, "T_1", null, null, "T_1");
            result = dbmeta.getExportedKeys(null, null, "T_1");
            result = dbmeta.getFunctionColumns(null, "%", "%", "%");
            result = dbmeta.getFunctions(null, "%", "%");

            while(result.next()) {
                s = result.getString(3);
            }

            result = dbmeta.getImportedKeys("PUBLIC", "PUBLIC", "T_1");
            result = dbmeta.getIndexInfo("PUBLIC", "PUBLIC", "T1", true, true);
            result = dbmeta.getPrimaryKeys("PUBLIC", "PUBLIC", "T_1");
            result = dbmeta.getProcedureColumns(null, null, "%", "%");
            result = dbmeta.getProcedures("PUBLIC", "%", "%");

            while(result.next()) {
                s = result.getString(3);
                s = result.getString(9);
            }

            result = dbmeta.getSchemas(null, "#");
            result = dbmeta.getTablePrivileges(null, "%", "%");
            result = dbmeta.getUDTs(null, "%", "%", new int[]{ Types.DISTINCT });
        } catch (Exception e) {
            fail("unable to prepare or execute DDL");
        } finally {
            conn.close();
        }
    }

    /**
     * Basic test of DatabaseMetaData functions that access functions
     */
    public void testThree() throws Exception {

        Connection conn = newConnection();
        int        updateCount;

        try {
            TestUtil.testScript(conn, "testrun/hsqldb/TestSelf.txt");

            DatabaseMetaData dbmeta     = conn.getMetaData();
            int txIsolation = dbmeta.getDefaultTransactionIsolation();
            String           userName   = dbmeta.getUserName();
            boolean          isReadOnly = dbmeta.isReadOnly();
            String           collation  = ((JDBCDatabaseMetaData) dbmeta).getDatabaseDefaultCollation();
            assertEquals("SQL_TEXT", collation);
        } catch (Exception e) {
            fail("unable to prepare or execute DDL");
        } finally {
            conn.close();
        }
    }
}
