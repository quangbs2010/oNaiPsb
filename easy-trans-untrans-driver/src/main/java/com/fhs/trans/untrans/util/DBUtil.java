package com.fhs.trans.untrans.util;

import lombok.Data;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.OutParameter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DBUtil {

    /**
     * 执行sql 返回map
     *
     * @param sql        sql
     * @param groupKeys  groupKeys
     * @param connection 数据库链接
     * @return
     */
    public static Map<String, String> query(String sql, List<String> groupKeys, Connection connection) {
        //in 有多少个就给多少个 问号，此种lowB的写法兼容mysql 的driver。
        String sqlIn = sql + "("  + groupKeys.stream().map(key->{return "?";}).collect(Collectors.joining(",")) + ")";
        QueryRunner qr = new QueryRunner();
        try {
            List<QueryResult> queryResultList = qr.query(connection, sqlIn, new BeanListHandler<QueryResult>(QueryResult.class), groupKeys.toArray());
            if(!TransactionSynchronizationManager.isActualTransactionActive()){
                DbUtils.close(connection);
            }
            return queryResultList.stream().collect(Collectors.toMap(QueryResult::getGroupKey, QueryResult::getUniqueKey));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 返回值接收
     */
    @Data
    public static class QueryResult {
        private String groupKey;
        private String uniqueKey;
    }
}
