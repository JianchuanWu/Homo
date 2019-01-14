package org.homo.dbconnect.inventory;

import org.homo.dbconnect.annotation.ManyToOne;
import org.homo.dbconnect.annotation.OneToMany;
import org.homo.core.model.BaseEntity;
import org.homo.dbconnect.config.AbstractDatabaseConfig;
import org.homo.dbconnect.transaction.Transaction;
import org.homo.dbconnect.transaction.TransactionImpl;
import org.homo.dbconnect.utils.ReflectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.Collection;

import static org.homo.dbconnect.utils.ReflectUtils.FIND_CHILDREN;
import static org.homo.dbconnect.utils.ReflectUtils.FIND_PARENT;

/**
 * @author wujianchuan 2019/1/9
 */
abstract class AbstractSession implements Session {

    private Logger logger = LoggerFactory.getLogger(AbstractSession.class);

    Transaction transaction;
    AbstractDatabaseConfig databaseConfig;
    FieldTypeStrategy fieldTypeStrategy = FieldTypeStrategy.getInstance();
    ReflectUtils reflectUtils = ReflectUtils.getInstance();

    AbstractSession(AbstractDatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
        this.transaction = new TransactionImpl(databaseConfig);
    }

    void showSql(String sql) {
        if (this.databaseConfig.getShowSql()) {
            this.logger.info("SQL: {}", sql);
        }
    }

    void adoptChildren(BaseEntity entity) throws Exception {
        Field[] childrenFields = Arrays.stream(entity.getClass().getDeclaredFields()).filter(FIND_CHILDREN).toArray(Field[]::new);
        if (childrenFields.length > 0) {
            for (Field childField : childrenFields) {
                childField.setAccessible(true);
                OneToMany oneToMany = childField.getAnnotation(OneToMany.class);
                Collection child = (Collection) childField.get(entity);
                if (child != null && child.size() > 0) {
                    Field[] detailFields = childField.getAnnotation(OneToMany.class).clazz().getDeclaredFields();
                    Field mappingField = Arrays.stream(detailFields)
                            .filter(FIND_PARENT)
                            .filter(field -> oneToMany.name().equals(field.getAnnotation(ManyToOne.class).name()))
                            .findFirst().orElseThrow(() -> new NullPointerException("子表实体未配置ManyToOne(name = \"" + oneToMany.name() + "\")注解"));
                    for (Object detail : child) {
                        mappingField.setAccessible(true);
                        mappingField.set(detail, entity.getUuid());
                        this.save((BaseEntity) detail);
                    }
                }
            }
        }
    }

    void statementApplyValue(BaseEntity entity, Field[] fields, PreparedStatement preparedStatement) throws Exception {
        for (int valueIndex = 0; valueIndex < fields.length; valueIndex++) {
            Field field = fields[valueIndex];
            field.setAccessible(true);
            preparedStatement.setObject(valueIndex + 1, field.get(entity));
        }
    }
}
