package com.ahmed.simpleBank.utils;

import com.ahmed.simpleBank.business.AccountTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(AccountTypeEnum.class)
public class AccountTypeEnumTypeHandler extends BaseTypeHandler<AccountTypeEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, AccountTypeEnum parameter, JdbcType jdbcType)
            throws SQLException {
        ps.setString(i, parameter.getAccountType());
    }

    @Override
    public AccountTypeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : AccountTypeEnum.fromString(value);
    }

    @Override
    public AccountTypeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : AccountTypeEnum.fromString(value);
    }

    @Override
    public AccountTypeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : AccountTypeEnum.fromString(value);
    }
}
