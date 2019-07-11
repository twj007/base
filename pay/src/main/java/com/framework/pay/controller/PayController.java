package com.framework.pay.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/04/24
 **/
@Controller
public class PayController {

    @Autowired
    private AlipayClient alipayClient;

    @Value("${alipay.charset}")
    private String charset;

    @Value("${alipay.signType}")
    private String signType;

    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    @Value("${alipay.return.publicKey}")
    private String returnKey;

    /***
     * 退款
     * @param request
     */
    @RequestMapping("/notify")
    public void notifyPay(HttpServletRequest request){
        System.out.println("退款提醒");

    }


    @GetMapping("/gotoPayPage")
    public void gotoPayPage(HttpServletResponse response) throws AlipayApiException, IOException {
        //这里可以把订单的数据保存到数据库，写作处理中的状态（这边可能需要传递sku或啥东西，查询物件的信息，并生成系统内部的订单，保存到对应表中）
        // 订单模型
        String productCode = "FAST_INSTANT_TRADE_PAY";
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        model.setOutTradeNo(UUID.randomUUID().toString());//系统内部生成的订单号
        model.setSubject("支付测试");
        model.setTotalAmount("0.01");
        model.setBody("支付测试，共0.01元");
        model.setProductCode(productCode);//订单模型

        AlipayTradePagePayRequest pagePayRequest = new AlipayTradePagePayRequest();
        //返回地址
        pagePayRequest.setReturnUrl("http://127.0.0.1:8081/toReturnUrl");
        //异步通知地址
        pagePayRequest.setNotifyUrl(notifyUrl);
        pagePayRequest.setBizModel(model);

        // 调用SDK生成表单, 并直接将完整的表单html输出到页面
        String form = alipayClient.pageExecute(pagePayRequest).getBody();
        response.setContentType("text/html;charset="+charset);
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    /***
     * 支付宝回调
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    @RequestMapping("/toReturnUrl")
    public String returnUrl(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset="+charset);

        //通过支付宝的rsa验证后把订单的状态写成扣款成功的状态, 收到扣款通知后对订单的状态写入为完成
        boolean verifyResult = false;
        try {
            verifyResult = rsaCheckV1(request);
            if(verifyResult){
                //验证成功
                //请在这里加上商户的业务逻辑程序代码，如保存支付宝交易号
                //商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
                //支付宝交易号
                String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

                return "pagePaySuccess";
            }else{
                return "pagePayFail";

            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "pagePayFail";
    }


    /***
     * 校验合法性
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     * @throws AlipayApiException
     */
    public boolean rsaCheckV1(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException {
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, returnKey, charset, signType); //调用SDK验证签名

        return signVerified;
    }
}
