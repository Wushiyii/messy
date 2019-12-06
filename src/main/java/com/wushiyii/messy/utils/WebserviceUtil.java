package com.wushiyii.messy.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.description.OperationDesc;
import org.apache.axis.description.ParameterDesc;
import org.apache.axis.encoding.ser.BeanDeserializerFactory;
import org.apache.axis.encoding.ser.BeanSerializerFactory;
import org.apache.axis.transport.http.HTTPConstants;

import javax.xml.namespace.QName;


@Slf4j
public class WebserviceUtil {
    

    /**
     * webservice调用
     *
     * @param wsdlUrl              wsdlUrl
     * @param namespace            命名空间
     * @param method               方法
     * @param requestLocalPart     请求域
     * @param responseLocalPart    响应域
     * @param characterSetEncoding xml 编码
     * @param timeout              超时时间
     * @param request              请求
     * @param responseClazz        响应类型
     * @param <P>                  请求泛化声明
     * @param <R>                  响应泛化声明
     * @return 响应
     * @throws Exception 异常
     */
    public static <P, R> R callWebService(String wsdlUrl, String namespace, String method, String requestLocalPart, String responseLocalPart,
                                          String characterSetEncoding, int timeout, P request, Class<R> responseClazz) throws Exception {
        long startTime = System.currentTimeMillis();
        LogUtil.info(log, "webservice收到请求:{0}", request);
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(wsdlUrl));
        // 注册序列化器 requestQName和responseQName可以一样，只有namespace
        QName requestQname = new QName(namespace, requestLocalPart);
        QName responseQname = new QName(namespace, responseLocalPart);
        Class requestClazz = request.getClass();
        // 请求序列化
        call.registerTypeMapping(requestClazz, requestQname,
                new BeanSerializerFactory(requestClazz, requestQname),
                new BeanDeserializerFactory(requestClazz, requestQname), false);
        // 响应序列化
        call.registerTypeMapping(responseClazz, responseQname,
                new BeanSerializerFactory(responseClazz, responseQname),
                new BeanDeserializerFactory(responseClazz, responseQname), false);
        // 设置操作方法
        call.setOperationName(new QName(namespace, method));
        // 定义操作描述
        OperationDesc operationDesc = new OperationDesc();
        operationDesc.setName(method);
        ParameterDesc parameterDesc = new ParameterDesc(new QName("", requestLocalPart), ParameterDesc.IN,
                new QName(namespace, requestLocalPart), requestClazz, false, false);
        parameterDesc.setOmittable(true);
        operationDesc.addParameter(parameterDesc);
        operationDesc.setReturnType(new QName(namespace, responseLocalPart));
        operationDesc.setReturnClass(responseClazz);
        operationDesc.setReturnQName(new javax.xml.namespace.QName("", responseLocalPart));
        operationDesc.setStyle(org.apache.axis.constants.Style.WRAPPED);
        operationDesc.setUse(org.apache.axis.constants.Use.LITERAL);
        call.setOperation(operationDesc);
        // 属性定义
        call.setProperty(org.apache.axis.MessageContext.HTTP_TRANSPORT_VERSION, HTTPConstants.HEADER_PROTOCOL_V11);
        call.setProperty(org.apache.axis.client.Call.CHARACTER_SET_ENCODING, characterSetEncoding);
        call.setProperty(org.apache.axis.client.Call.SEND_TYPE_ATTR, Boolean.FALSE);
        call.setProperty(org.apache.axis.AxisEngine.PROP_DOMULTIREFS, Boolean.FALSE);
        call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        // 其他
        call.setUseSOAPAction(true);
        call.setSOAPActionURI("");
        call.setEncodingStyle(null);
        call.setTimeout(timeout);
        R response = (R) call.invoke(new Object[]{request});
        LogUtil.info(log, "webservice原始xml请求报文:{0}", call.getMessageContext().getRequestMessage().getSOAPPartAsString());
        LogUtil.info(log, "webservice原始xml响应报文:{0}", call.getMessageContext().getResponseMessage().getSOAPPartAsString());
        long costTimeMills = System.currentTimeMillis() - startTime;
        LogUtil.info(log, "webservice响应结果:{0},costTimeMill:{1}", response, costTimeMills);
        return response;
    }

}