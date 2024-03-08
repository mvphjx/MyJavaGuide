package com.guide.common.sql;

import org.apache.calcite.adapter.solr.SqlFilter2SolrFilterTranslator;
import org.apache.calcite.config.Lex;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.rel.core.Filter;
import org.apache.calcite.rel.core.Project;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlCharStringLiteral;
import org.apache.calcite.sql.SqlIdentifier;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlOperator;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql2rel.SqlToRelConverter;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

public class SolrSqlTest
{
    static SqlFilter2SolrFilterTranslator _trans;

    public static void main(String[] args) throws Exception
    {
        String sql = "SELECT * from docs where  (  (  ( CREATE_USER='$CUR_USER_ID' )  AND  ( CREATE_TIME BETWEEN HSABIS_TO_DATE('20221118061831') AND HSABIS_TO_DATE('20221122101831') )  )  OR  (  ( HSABIS_BIT_INC_ALL(BTY_MASK , 3)=1  )  AND  ( PRINT_UNIT_CODE IN ( '$CUR_UNIT_CODE' ) )  )  ) ";
        System.out.println(trans(sql));
    }

    private static String trans(String sql) throws Exception
    {
        // 创建SqlParser, 用于解析SQL字符串
        SqlParser parser = SqlParser.create(sql, SqlParser.Config.DEFAULT);
        // 解析SQL字符串, 生成SqlNode树
        SqlNode sqlNode = parser.parseStmt();
        if (SqlKind.SELECT.equals(sqlNode.getKind()))
        {
            SqlSelect sqlSelect = (SqlSelect) sqlNode;
            SqlNode from = sqlSelect.getFrom();
            SqlNode where = sqlSelect.getWhere();
            SqlNodeList selectList = sqlSelect.getSelectList();
            SqlKind whereKind = where.getKind();
            if(where instanceof SqlBasicCall){
                SqlBasicCall  basicWhere = (SqlBasicCall)where;
                //操作符
                SqlOperator operator = basicWhere.getOperator();
                List<SqlNode> operandList = basicWhere.getOperandList();
                for (SqlNode node : operandList)
                {
                    SqlKind kind = node.getKind();
                    if (SqlKind.LITERAL.equals(node.getKind())){
                        //常量
                        if(node instanceof SqlCharStringLiteral){

                        }
                    }
                    if (SqlKind.IDENTIFIER.equals(node.getKind())){
                        //列名
                        if(node instanceof SqlIdentifier){

                        }
                    }
                    if(node instanceof SqlBasicCall){
                        SqlBasicCall  basicWhereChild = (SqlBasicCall)node;
                        SqlOperator operatorChild = basicWhereChild.getOperator();
                        List<SqlNode> operandListChild = basicWhereChild.getOperandList();
                    }


                }


            }
            //标识符
            if (SqlKind.IDENTIFIER.equals(from.getKind()))
            {
                System.out.println(from.toString());
            }
            if (SqlKind.LESS_THAN.equals(where.getKind()))
            {
                SqlBasicCall sqlBasicCall = (SqlBasicCall) where;
                for (SqlNode sqlNode1 : sqlBasicCall.getOperandList())
                {
                    if (SqlKind.LITERAL.equals(sqlNode1.getKind()))
                    {
                        System.out.println(sqlNode1.toString());
                    }
                }
            }
            selectList.getList().forEach(x -> {
                if (SqlKind.IDENTIFIER.equals(x.getKind()))
                {
                    System.out.println(x.toString());
                }
            });
        }

        RelRoot relRoot = null;
        RelNode project = relRoot.project();
        RexNode condition = ((Filter) ((Project) project).getInput()).getCondition();
        return _trans.translate(condition).toSolrQueryString();
    }

}
