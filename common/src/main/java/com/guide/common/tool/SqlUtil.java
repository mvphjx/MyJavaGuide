package com.guide.common.tool;

import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParser;

public class SqlUtil
{
    public static void main(String[] args) throws Exception
    {
        // TODO
        String sql = "SELECT * from docs where  (  (  ( CREATE_USER='$CUR_USER_ID' )  AND  ( CREATE_TIME BETWEEN HSABIS_TO_DATE('20221118061831') AND HSABIS_TO_DATE('20221122101831') )  )  OR  (  ( HSABIS_BIT_INC_ALL(BTY_MASK , 3)=1  )  AND  ( PRINT_UNIT_CODE IN ( '$CUR_UNIT_CODE' ) )  )  ) ";
        // 创建SqlParser, 用于解析SQL字符串
        SqlParser parser = SqlParser.create(sql, SqlParser.Config.DEFAULT);
        // 解析SQL字符串, 生成SqlNode树
        SqlNode sqlNode = parser.parseStmt();
        SqlKind kind = sqlNode.getKind();
        System.out.println(kind);
    }

}
