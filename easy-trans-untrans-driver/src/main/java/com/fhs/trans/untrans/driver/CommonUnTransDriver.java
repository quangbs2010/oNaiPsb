package com.fhs.trans.untrans.driver;

import com.fhs.core.trans.anno.UnTrans;
import com.fhs.trans.service.impl.SimpleTransService;
import com.fhs.trans.untrans.util.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * mysql/sqlserver/postgresql 的反向翻译驱动实现
 */
public class CommonUnTransDriver implements SimpleTransService.SimpleUnTransDiver {


    @Autowired
    private DataSource datasource;

    @Override
    public Map<String, String> getUnTransMap(UnTrans unTrans, List<String> groupKeys) {
        String groupKeyConcat = unTrans.columns().length > 1 ?
                "CONCAT(" + Arrays.stream(unTrans.columns()).collect(Collectors.joining(",'/',")) + ")" : unTrans.columns()[0];
        String sql = MessageFormat.format(SQL, groupKeyConcat, unTrans.uniqueColumn(), unTrans.tableName(), groupKeyConcat);
        try {
            return DBUtil.query(sql, groupKeys, datasource.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getUnTransResult(UnTrans unTrans, String groupKey) {
        Map<String, String> untransMap = getUnTransMap(unTrans, Arrays.asList(groupKey));
        return untransMap.get(groupKey);
    }


}
