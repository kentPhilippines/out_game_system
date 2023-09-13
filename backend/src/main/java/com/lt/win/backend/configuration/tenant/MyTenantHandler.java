package com.lt.win.backend.configuration.tenant;

import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.lt.win.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.schema.Column;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * @Author : Wells
 * @Date : 2020-11-10 11:54 下午
 * @Description : xx
 */
@Slf4j
public class MyTenantHandler implements TenantHandler {

    // 是否存在含义plat_id的表
    boolean isExistTable = false;

    @Override
    public Expression getTenantId(boolean select) {
        // select since: 3.3.2，参数 true 表示为 select 下的 where 条件,false 表示 insert/update/delete 下的条件
        // 只有 select 下才允许多参(ValueListExpression),否则只支持单参
        if (MyTenantParser.METHOD_FLAG.get().equals("SELECT") || (isExistTable) && MyTenantParser.METHOD_FLAG.get().equals("DELETE")) {
            var ids = TenantContextHolder.getTenantId();
            log.debug("ids=" + ids);
            if (StringUtils.isNotEmpty(ids)) {
                var inExpression = new InExpression();
                inExpression.setLeftExpression(new Column(getTenantIdColumn()));
                var expressList = new ArrayList<Expression>();
                var idArr = ids.split(",");
                for (String uid : idArr) {
                    expressList.add(new LongValue(uid));
                }
                var itemsList = new ExpressionList(expressList);
                inExpression.setRightItemsList(itemsList);
                return inExpression;
            }
        } else if ((MyTenantParser.METHOD_FLAG.get().equals("INSERT") || (MyTenantParser.METHOD_FLAG.get().equals("UPDATE")))) {
            return new LongValue(DateUtils.getCurrentTime());
        }
        return new LongValue(-1);
    }

    @Override
    public String getTenantIdColumn() {
        var str = TenantContextHolder.getField();
        if (MyTenantParser.METHOD_FLAG.get().equals("INSERT") || (MyTenantParser.METHOD_FLAG.get().equals("UPDATE"))) {
            str = "updated_at";
        }
        return str;
    }

    @Override
    public boolean doTableFilter(String tableName) {
        var list = new TenantProperties().getIgnoreTables();
        if (!list.contains(tableName)) {
            var fieldName = TableProperties.NO_IGNORE_TABLE.get(tableName);
            if (StringUtils.isNotEmpty(fieldName)) {
                TenantContextHolder.setField(TableProperties.NO_IGNORE_TABLE.get(tableName));
                return false;
            }
        }
        return true;
    }

}