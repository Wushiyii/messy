package com.wushiyii.messy.utils;

import com.google.common.collect.Lists;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * 接口入参数据校验工具类,使用hibernate-validator进行校验
 *
 * @NotNull 被注释的元素必须不为 null
 * @Min(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @Max(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @DecimalMin(value) 被注释的元素必须是一个数字，其值必须大于等于指定的最小值
 * @DecimalMax(value) 被注释的元素必须是一个数字，其值必须小于等于指定的最大值
 * @Size(max=, min=)   被注释的元素的大小必须在指定的范围内
 * @Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内
 * @Past 被注释的元素必须是一个过去的日期
 * @Future 被注释的元素必须是一个将来的日期
 * @Pattern(regex=,flag=) 被注释的元素必须符合指定的正则表达式
 * @NotBlank(message =)   验证字符串非null，且长度必须大于0
 * @Email 被注释的元素必须是电子邮箱地址
 * @Length(min=,max=) 被注释的字符串的大小必须在指定的范围内
 * @NotEmpty 被注释的字符串的必须非空
 * @Range(min=,max=,message=) 被注释的元素必须在合适的范围内
 */
public class ValidationUtils {

    /**
     * @Fields validator : 使用hibernate的注解来进行验证
     */
    private static Validator validator = Validation.byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory()
            .getValidator();

    /**
     * 注解验证参数
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> ValidateResult validate(T obj) {
        ValidateResult result = new ValidateResult();
        result.setIsValid(true);
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(obj);
        // 抛出检验异常
        if (constraintViolations.size() > 0) {
            // 字段校验未通过的字段名称
            result.setIsValid(false);
            List<String> msgs = Lists.newArrayList();
            constraintViolations.forEach(violation -> msgs.add(violation.getMessage()));
            result.setMessages(msgs);
        }
        return result;
    }
}
