package com.ontimize.jee.server.dao.jdbc;

import com.ontimize.jee.common.db.AdvancedEntityResult;
import com.ontimize.jee.common.db.AdvancedEntityResultMapImpl;
import com.ontimize.jee.common.db.SQLStatementBuilder;
import com.ontimize.jee.common.db.handler.DefaultSQLStatementHandler;
import com.ontimize.jee.common.dto.EntityResult;
import com.ontimize.jee.common.dto.EntityResultMapImpl;
import com.ontimize.jee.server.dao.ISQLQueryAdapter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OntimizeJdbcDaoSupportTest {

    @InjectMocks
    OntimizeJdbcDaoSupport ontimizeJdbcDaoSupport;

//    @Mock
//    QueryTemplateInformation queryTemplateInformation;
//
//    @Mock
//    JdbcTemplate jdbcTemplate;


    @Disabled
    @Nested
    class Query {


        @Test
        void when_receive_keysValues_and_attributes_and_sort_and_queryId_and_queryAdapter_expect_alert_ERROR_CODE_RETURN() {

            Map keysValues = Stream.of(new Object[][]{{"column2", 2}, {"column3", 3},}).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
            List<String> attributes = new ArrayList<>(Arrays.asList("column1"));
            List<String> sort = new ArrayList();
            String queryId = "queryId";

            keysValues.put("key1", "value1");
            sort.add("sort1");

            List requestedColumns = new ArrayList();
            Map conditions = new HashMap();
            List wildcards = new ArrayList();
            List columnSorting = new ArrayList();

            requestedColumns.add("column1");
            conditions.put("key1", "value1");
            wildcards.add("wildcards1");
            columnSorting.add("sort1");

            ReflectionTestUtils.setField(OntimizeJdbcDaoSupportTest.this.ontimizeJdbcDaoSupport, "compiled", true);
            DefaultSQLStatementHandler statementHandler = new DefaultSQLStatementHandler();
            OntimizeJdbcDaoSupportTest.this.ontimizeJdbcDaoSupport.setStatementHandler(statementHandler);
            OntimizeTableMetaDataContext tableMetaDataContext = OntimizeJdbcDaoSupportTest.this.ontimizeJdbcDaoSupport.getTableMetaDataContext();
            ReflectionTestUtils.setField(tableMetaDataContext, "tableName", "my-table");
            EntityResult entityResult = ontimizeJdbcDaoSupport.query(keysValues, attributes, sort, queryId, null);

            String expected = "EntityResult:  ERROR CODE RETURN:  : {}";
            Assertions.assertEquals(expected, entityResult.toString());

        }

        @Test
        @DisplayName("When the query method receives keyValues, attributes, sort, queryId and queryAdapter, return an EntityResult")
        void when_receive_keysValues_and_attributes_and_sort_and_queryId_and_queryAdapter_expect_query() {

            ArgumentCaptor<String> sqlQuery = ArgumentCaptor.forClass(String.class);
            ArgumentCaptor<ArgumentPreparedStatementSetter> pss = ArgumentCaptor.forClass(ArgumentPreparedStatementSetter.class);
            String checkSQLQuery = " SELECT column1 FROM  [my-table]   WHERE column3 = ?  AND column2 = ? ";

            Map<String, Object> keysValues = Stream.of(new Object[][]{{"column2", 2}, {"column3", 3},}).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
            List<String> attributes = new ArrayList<>(Arrays.asList("column1"));
            List<String> sort = new ArrayList<>();
            String queryId = "default";

            JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);

            ontimizeJdbcDaoSupport.setJdbcTemplate(jdbcTemplate);
            ontimizeJdbcDaoSupport.setStatementHandler(new DefaultSQLStatementHandler());
            ReflectionTestUtils.setField(ontimizeJdbcDaoSupport, "compiled", true);
            OntimizeTableMetaDataContext tableMetaDataContext = ontimizeJdbcDaoSupport.getTableMetaDataContext();
            ReflectionTestUtils.setField(tableMetaDataContext, "tableName", "my-table");


            EntityResult entityResult = ontimizeJdbcDaoSupport.query(keysValues, attributes, sort, queryId);
            Mockito.verify(jdbcTemplate).query(sqlQuery.capture(), pss.capture(), Mockito.any(EntityResultResultSetExtractor.class));
            assertEquals(checkSQLQuery, sqlQuery.getValue());
            Object[] args = (Object[]) (ReflectionTestUtils.getField(pss.getValue(), "args"));
            assertEquals(3, args[0]);
            assertEquals(2, args[1]);
        }

    }

    @Nested
    class PaginationQuery {

        @Test
        void when_receive_keysValues_and_attributes_and_sort_and_queryId_and_queryAdapter_expect_paginationQuery() {

            ArgumentCaptor<OntimizeJdbcDaoSupport.SimpleScrollablePreparedStatementCreator> ssPSC = ArgumentCaptor.forClass(OntimizeJdbcDaoSupport.SimpleScrollablePreparedStatementCreator.class);
            ArgumentCaptor<ArgumentPreparedStatementSetter> pss = ArgumentCaptor.forClass(ArgumentPreparedStatementSetter.class);
            String checkSQLQuery = " SELECT column1 FROM  [my-table]   WHERE column3 = ?  AND column2 = ? ";

            Map<String, Object> keysValues = Stream.of(new Object[][]{{"column2", 2}, {"column3", 3},}).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
            List<String> attributes = new ArrayList<>(Arrays.asList("column1"));
            int recordNumber = 5;
            int startIndex = 3;
            List<String> orderBy = new ArrayList<>();
            String queryId = "default";
            ISQLQueryAdapter adapter = null;

            JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
            Mockito.doReturn(new AdvancedEntityResultMapImpl(EntityResult.OPERATION_SUCCESSFUL, EntityResult.DATA_RESULT)).when(jdbcTemplate).query(Mockito.any(PreparedStatementCreator.class), Mockito.any(), Mockito.any());
            Mockito.doReturn(new EntityResultMapImpl()).when(jdbcTemplate).query(Mockito.any(String.class), (Object[]) Mockito.any(), Mockito.any(ResultSetExtractor.class));
            ontimizeJdbcDaoSupport.setJdbcTemplate(jdbcTemplate);
            ontimizeJdbcDaoSupport.setStatementHandler(new DefaultSQLStatementHandler());
            ReflectionTestUtils.setField(ontimizeJdbcDaoSupport, "compiled", true);
            OntimizeTableMetaDataContext tableMetaDataContext = ontimizeJdbcDaoSupport.getTableMetaDataContext();
            ReflectionTestUtils.setField(tableMetaDataContext, "tableName", "my-table");

            AdvancedEntityResult eR = ontimizeJdbcDaoSupport.paginationQuery(keysValues, attributes, recordNumber, startIndex, orderBy, queryId, null);
            Mockito.verify(jdbcTemplate).query(ssPSC.capture(), pss.capture(), Mockito.any(AdvancedEntityResultResultSetExtractor.class));
            assertEquals(checkSQLQuery, ssPSC.getValue().getSql());

        }

        @Test
        void when_receive_keysValues_and_attributes_and_sort_and_queryId_and_queryAdapter_expect_paginationQuery_v2() {

            Map keysValues = new HashMap();
            List attributes = new ArrayList();
            List sort = new ArrayList();
            String queryId = "queryId";
            List orderBy = new ArrayList();
            int recordNumber = 1;
            int startIndex = 1;

            keysValues.put("key1", "value1");
            attributes.add("column1");
            sort.add("sort1");
            orderBy.add("columnSort");


            ArrayList requestedColumns = new ArrayList();
            HashMap conditions = new HashMap();
            ArrayList wildcards = new ArrayList();
            ArrayList columnSorting = new ArrayList();

            requestedColumns.add("column1");
            conditions.put("key1", "value1");
            wildcards.add("wildcards1");
            columnSorting.add("columnSort");

            ReflectionTestUtils.setField(ontimizeJdbcDaoSupport, "compiled", true);
            DefaultSQLStatementHandler statementHandler = new DefaultSQLStatementHandler();
            ontimizeJdbcDaoSupport.setStatementHandler(statementHandler);
            OntimizeTableMetaDataContext tableMetaDataContext = ontimizeJdbcDaoSupport.getTableMetaDataContext();
            ReflectionTestUtils.setField(tableMetaDataContext, "tableName", "my-table");
            SQLStatementBuilder.SQLStatement stSQL = Mockito.mock(SQLStatementBuilder.SQLStatement.class);
            JdbcTemplate jdbcTemplate = Mockito.mock(JdbcTemplate.class);
            ontimizeJdbcDaoSupport.setJdbcTemplate(jdbcTemplate);

            /* String sqlQuery = "SELECT  COUNT(*) AS "TotalRecordNumber"  FROM  [my-table]   WHERE key1 = ?";*/
            ArgumentCaptor<String> sqlQuery = ArgumentCaptor.forClass(String.class);
            ArrayList vValues = new ArrayList();
            vValues.add(1);

            ArgumentCaptor<ArgumentPreparedStatementSetter> pss = ArgumentCaptor.forClass(ArgumentPreparedStatementSetter.class);
            ArgumentCaptor<OntimizeJdbcDaoSupport.SimpleScrollablePreparedStatementCreator> simpleScrollablePreparedStatementCreator = ArgumentCaptor.forClass(OntimizeJdbcDaoSupport.SimpleScrollablePreparedStatementCreator.class);
            AdvancedEntityResult advancedER = ontimizeJdbcDaoSupport.paginationQuery(keysValues, attributes, recordNumber, startIndex, orderBy, queryId, null);
            Mockito.verify(jdbcTemplate).query(simpleScrollablePreparedStatementCreator.capture(), pss.capture(), Mockito.any(EntityResultResultSetExtractor.class));


            //Mockito.verify(jdbcTemplate).query(sqlQuery.capture(), vValues.toArray(), Mockito.any(EntityResultResultSetExtractor.class));
            //Mockito.doReturn(1).when(jdbcTemplate).query((PreparedStatementCreator) Mockito.any(), Mockito.any(), Mockito.any());
            //Mockito.doReturn(1).when(jdbcTemplate).query(sqlQuery.capture(), vValues.toArray(), Mockito.any(EntityResultResultSetExtractor.class));

            /*
			advancedER.setTotalRecordCount(this.getQueryRecordNumber(keysValues, queryId));
			return advancedER;*/

        }


        @Test
        void when_receive_keysValues_and_attributes_and_sort_and_queryId_and_queryAdapter_expect_ERROR_CODE_RETURN() {

            Map keysValues = new HashMap();
            List attributes = new ArrayList();
            List sort = new ArrayList();
            String queryId = "queryId";
            List orderBy = new ArrayList();
            int recordNumber = 1;
            int startIndex = 1;

            keysValues.put("key1", "value1");
            attributes.add("column1");
            sort.add("sort1");
            orderBy.add("columnSort");


            ArrayList requestedColumns = new ArrayList();
            HashMap conditions = new HashMap();
            ArrayList wildcards = new ArrayList();
            ArrayList columnSorting = new ArrayList();

            requestedColumns.add("column1");
            conditions.put("key1", "value1");
            wildcards.add("wildcards1");
            columnSorting.add("columnSort");

            ReflectionTestUtils.setField(ontimizeJdbcDaoSupport, "compiled", true);
            DefaultSQLStatementHandler statementHandler = new DefaultSQLStatementHandler();
            ontimizeJdbcDaoSupport.setStatementHandler(statementHandler);
            OntimizeTableMetaDataContext tableMetaDataContext = ontimizeJdbcDaoSupport.getTableMetaDataContext();
            ReflectionTestUtils.setField(tableMetaDataContext, "tableName", "my-table");
            SQLStatementBuilder.SQLStatement stSQL = Mockito.mock(SQLStatementBuilder.SQLStatement.class);
            AdvancedEntityResult advancedER = ontimizeJdbcDaoSupport.paginationQuery(keysValues, attributes, recordNumber, startIndex, orderBy, queryId, null);
            String expected = "EntityResult:  ERROR CODE RETURN:  : {}";
            Assertions.assertEquals(expected, advancedER.toString());
            System.out.println(advancedER.toString());

        }

    }

    @Disabled
    @Nested
    class insert {
        @Test
        void when_receive_attributesValues_expect_message_alert() {
            Map attributesValues = Stream.of(new Object[][]{{"column1", 1}, {"column2", 2},}).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
            ReflectionTestUtils.setField(OntimizeJdbcDaoSupportTest.this.ontimizeJdbcDaoSupport, "compiled", true);
            OntimizeTableMetaDataContext tableMetaDataContext = OntimizeJdbcDaoSupportTest.this.ontimizeJdbcDaoSupport.getTableMetaDataContext();
            List<String> tableColumns = new ArrayList<>(Arrays.asList("tableColumn1"));
            tableMetaDataContext.getTableColumns();
            EntityResult erResult = new EntityResultMapImpl();
            erResult = ontimizeJdbcDaoSupport.insert(attributesValues);
            Assertions.assertTrue(true, "Insert: Attributes does not contain any pair key-value valid");

        }

        @Test
        void when_receive_attributesValues_expect_entityResult() {
            Map attributesValues = Stream.of(new Object[][]{{"attribute1", 1}, {"attribute2", 2},}).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
            ReflectionTestUtils.setField(OntimizeJdbcDaoSupportTest.this.ontimizeJdbcDaoSupport, "compiled", true);
            OntimizeTableMetaDataContext tableMetaDataContext = OntimizeJdbcDaoSupportTest.this.ontimizeJdbcDaoSupport.getTableMetaDataContext();
            List<String> tableColumns = new ArrayList<>();
            tableColumns.add("column1");
            tableColumns.add("column2");
            ReflectionTestUtils.setField(tableMetaDataContext, "tableColumns", tableColumns);

            EntityResult erResult = new EntityResultMapImpl();
            erResult = ontimizeJdbcDaoSupport.insert(attributesValues);

        }
    }


}