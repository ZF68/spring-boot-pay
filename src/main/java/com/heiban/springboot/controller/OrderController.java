package com.heiban.springboot.controller;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.heiban.springboot.bean.Order;
import com.heiban.springboot.config.AlipayConfig;
import com.heiban.springboot.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;


@Controller
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    //商品首页渲染
    @RequestMapping({"/","/index.html"})
    public String index() {
        return "index";
    }

    //订单列表渲染
    @RequestMapping("/information.html")
    public String orderdetails(ModelMap model) {

        List<Order> all = orderRepository.findAll();

        model.put("orderall",all);

        return "information";
    }

    //订单发起方法
    @PostMapping("/paymethod")
    public void paymethod(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

        String username = request.getParameter("username");//获取表单提交的用户名
        String commodity = request.getParameter("commodity");//获取表单提交的商品名称
        String money = commodity.equals("商品1")?"10":commodity.equals("商品2")?"20":"30";//通过商品名称获取价格
        String nowtime = String.valueOf(System.currentTimeMillis());

        //将订单信息保存到数据库 等待支付成功回调再进行处理
        Order order = new Order();
        order.setUsername(username);
        order.setMoney(money);
        order.setCommodity(commodity);
        order.setOut_trade_no(nowtime);//设置商户订单号
        orderRepository.save(order);//保存到数据库


        // 商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = nowtime;//采用时间戳当做订单号
        // 订单名称，必填
        String subject = new String(commodity);
        // 付款金额，必填
        String total_amount=new String(money);
        // 商品描述，可空
        String body = new String("商品测试");
        // 超时时间 可空
        String timeout_express="2m";
        // 销售产品码 必填
        String product_code="QUICK_WAP_WAY";
        /**********************/
        // SDK 公共请求类，包含公共请求参数，以及封装了签名与验签，开发者无需关注签名与验签
        //调用RSA签名方式
        AlipayClient client = new DefaultAlipayClient(AlipayConfig.URL, AlipayConfig.APPID, AlipayConfig.RSA_PRIVATE_KEY, AlipayConfig.FORMAT, AlipayConfig.CHARSET, AlipayConfig.ALIPAY_PUBLIC_KEY,AlipayConfig.SIGNTYPE);
        AlipayTradeWapPayRequest alipay_request=new AlipayTradeWapPayRequest();

        // 封装请求支付信息
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setSubject(subject);
        model.setTotalAmount(total_amount);
        model.setBody(body);
        model.setTimeoutExpress(timeout_express);
        model.setProductCode(product_code);
        alipay_request.setBizModel(model);
        // 设置异步通知地址
        alipay_request.setNotifyUrl(AlipayConfig.notify_url);
        // 设置同步地址
        alipay_request.setReturnUrl(AlipayConfig.return_url);

        // form表单生产
        String form = "";
        try {
            // 调用SDK生成表单
            form = client.pageExecute(alipay_request).getBody();
            response.setContentType("text/html;charset=" + AlipayConfig.CHARSET);
            response.getWriter().write(form);//直接将完整的表单html输出到页面
            response.getWriter().flush();
            response.getWriter().close();
        } catch (AlipayApiException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    //同步回调处理方法
    @GetMapping("/return_url")
    public String return_url(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        //获取支付宝GET过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码
            //该页面可做页面美工编辑
            System.out.println("验证成功");
            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——


            //处理标记付款成功
            Order order = orderRepository.findByOuttradeno(out_trade_no);
            if(order.getTrade_no()==null){
                order.setPlay(1);//设置为支付成功
                order.setTrade_no(trade_no);//设置支付宝交易号
                orderRepository.save(order);//保存到数据库
            }

            return "redirect:/information.html";//跳转到订单信息页面

            //////////////////////////////////////////////////////////////////////////////////////////
        }else{
            //该页面可做页面美工编辑
            System.out.println("验证失败");
            return "index";//跳转到首页
        }
    }


    //异步回调处理方法
    @ResponseBody
    @PostMapping("/notify_url")
    public String notify_url(HttpServletRequest request) throws AlipayApiException, UnsupportedEncodingException {
        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }
        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
        //商户订单号

        String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
        //支付宝交易号

        String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

        //交易状态
        String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");

        //获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
        //计算得出通知验证结果
        //boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
        boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.CHARSET, "RSA2");

        if(verify_result){//验证成功
            //////////////////////////////////////////////////////////////////////////////////////////
            //请在这里加上商户的业务逻辑程序代码

            //处理标记付款成功
            Order order = orderRepository.findByOuttradeno(out_trade_no);
            if(order.getTrade_no()==null){
                order.setPlay(1);//设置为支付成功
                order.setTrade_no(trade_no);//设置支付宝交易号
                orderRepository.save(order);//保存到数据库
                System.out.println("异步回调验证成功");
            }

            //——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
            if(trade_status.equals("TRADE_FINISHED")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
                //如果没有签约可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            } else if (trade_status.equals("TRADE_SUCCESS")){
                //判断该笔订单是否在商户网站中已经做过处理
                //如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
                //请务必判断请求时的total_fee、seller_id与通知时获取的total_fee、seller_id为一致的
                //如果有做过处理，不执行商户的业务程序

                //注意：
                //如果签约的是可退款协议，那么付款完成后，支付宝系统发送该交易状态通知。
            }


            //——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
            return "success";	//请不要修改或删除

            //////////////////////////////////////////////////////////////////////////////////////////
        }else{//验证失败
            return "fail";
        }
    }


}
