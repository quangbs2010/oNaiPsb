package com.fhs.core.trans.anno;

import com.fhs.core.trans.vo.TransPojo;
import com.fhs.core.trans.vo.VO;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 翻译
 *
 * @author wanglei
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface UnTrans {
    /**
     * 获取翻译类型，dictionary
     *
     * @return 类型
     */
    String type();

    /**
     * 字典分组/类型
     * @return
     */
    String dict() default "";

    /**
     *
     * 本类的哪些字段参与查询
     * @return
     */
    String[] refs() default {};

    /**
     * 对应的db表字段
     * @return
     */
    String[] columns() default {};

    /**
     * 需要返回的唯一键的column，可以是id 也可以其他的
     * @return
     */
    String uniqueColumn() default "id";



    /**
     * @return
     */
    String tableName() default "";


}
