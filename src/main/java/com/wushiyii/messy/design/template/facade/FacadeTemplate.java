package com.wushiyii.messy.design.template.facade;

import com.wushiyii.messy.design.template.facade.enums.ExceptionEnum;
import com.wushiyii.messy.design.template.facade.enums.SystemException;
import com.wushiyii.messy.design.template.facade.enums.SystemStatusEnum;
import com.wushiyii.messy.design.template.facade.vo.BaseReqDto;
import com.wushiyii.messy.design.template.facade.vo.BaseRespDto;
import com.wushiyii.messy.utils.LogUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 通用facade接收返回模板
 * @param <T> 请求VO
 * @param <R> 返回VO
 */
public abstract class FacadeTemplate <T extends BaseReqDto, R extends BaseRespDto> {

    protected T req;
    protected Class<R> clazz;//
    private final String templateName;//接口名称

    private static final Logger logger = LoggerFactory.getLogger(FacadeTemplate.class);

    protected FacadeTemplate(String templateName, T req, Class<R> clazz) {
        this.templateName = checkNotNull(templateName);
        this.req = checkNotNull(req);
        this.clazz = checkNotNull(clazz);
    }

    protected FacadeTemplate(T req, Class<R> clazz) {
        this.templateName = StringUtils.EMPTY;
        this.req = checkNotNull(req);
        this.clazz = checkNotNull(clazz);
    }

    //基本参数校验
    protected void checkParams(T req) {
        if (req == null) {
            throw new SystemException(ExceptionEnum.UNKNOWN, "请求参数为空", SystemStatusEnum.UNKNOWN);
        }
    }

    //业务处理
    protected abstract R process();

    //业务后置处理
    protected void afterProcess() {
    }

    public final R execute() {
        long timeStart = System.currentTimeMillis();
        R result = null;
        try {
            logger.info(templateName + "接口接收到请求：{}", req);
            checkParams(req);
            result = process();
            if (result == null) {
                result = (R) result.getClass().newInstance();
            }
            if (result.getCode() == null) {
                result.setCode(ExceptionEnum.S00000.name());
                result.setMsg(ExceptionEnum.S00000.getMsg());
            }
            if (result.getSystemStatus() == null) {
                result.setSystemStatus(SystemStatusEnum.SUCCESS.name());
            }
        } catch (Throwable t) {
            result = handleException(t);
        } finally {
            long elapsedMills = System.currentTimeMillis() - timeStart;
            LogUtil.info(logger, templateName + "接口执行消耗{0}ms，返回结果：{1}， ", elapsedMills, result);
            afterProcess();
        }
        return result;
    }

    private R handleException(Throwable t) {
        logger.error(templateName + "接口发生异常", t);
        R result = null;
        try {
            result = clazz.newInstance();
            if (t instanceof SystemException) {
                SystemException memberBizException = (SystemException) t;
                result.setSystemStatus(
                        memberBizException.getLocalizedMessage() == null
                                ? SystemStatusEnum.FAIL.name()
                                : memberBizException.getExceptionEnum().name());
                result.setCode(memberBizException.getCode());
                result.setMsg(memberBizException.getMsg());
            } else {
                result.setSystemStatus(SystemStatusEnum.FAIL.name());
                result.setCode(ExceptionEnum.E10000.name());
                result.setMsg(ExceptionEnum.E10000.getMsg());
            }
        } catch (Exception e) {
            LogUtil.error(logger, e, "Unable to create facade response of clazz: " + clazz);
        }
        return result;
    }
}
