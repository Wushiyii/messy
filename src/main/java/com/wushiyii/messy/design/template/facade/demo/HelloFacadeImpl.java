package com.wushiyii.messy.design.template.facade.demo;


import com.wushiyii.messy.design.template.facade.FacadeTemplate;
import com.wushiyii.messy.design.template.facade.enums.ExceptionEnum;
import com.wushiyii.messy.design.template.facade.enums.SystemException;
import com.wushiyii.messy.design.template.facade.enums.SystemStatusEnum;
import org.apache.commons.lang3.StringUtils;

public class HelloFacadeImpl implements HelloFacade {

    @Override
    public HelloRespVo sayHello(HelloReqVo reqVo) {
        return new FacadeTemplate<HelloReqVo, HelloRespVo>("sayHello", reqVo, HelloRespVo.class) {

            @Override
            protected void checkParams(HelloReqVo req) {
                super.checkParams(req);
                if (StringUtils.isBlank(reqVo.getOperator())) {
                    throw new SystemException(ExceptionEnum.V10000, "Operator 为空", SystemStatusEnum.FAIL);
                }
            }

            @Override
            protected HelloRespVo process() {
                HelloRespVo respVo = new HelloRespVo();
                respVo.setResult("Hello " + reqVo.getOperator());
                return respVo;
            }

            @Override
            protected void afterProcess() {
                System.out.println("afterProcess execute");
            }
        }.execute();
    }

    public static void main(String[] args) {
        HelloFacade facade = new HelloFacadeImpl();
        HelloReqVo reqVo = new HelloReqVo();
        reqVo.setOperator("Jimmy");
        HelloRespVo respVo = facade.sayHello(reqVo);
        System.out.println(respVo.getResult());
    }
}
