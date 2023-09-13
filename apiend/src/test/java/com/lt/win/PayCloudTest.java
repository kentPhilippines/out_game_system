package com.lt.win;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.lt.win.utils.MD5;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * 支付对接请求接口demo
 *
 * @author wells
 * @date 2022/7/15 21:30
 */
@Slf4j
public class PayCloudTest {

    private static final String API_KEY = "e1337b9941ffdb6f6724a391d23464dc";

    /**
     * 代付
     */
    @Test
    public void createPay() {
       // String url = "https://api.adminuu.pro/ucc/pay/createPay";
        String url = "https://gateway.paycloud.pro/ucc/pay/createPayMerchant";
        String orderNo = "O2022100502470730623";
        //String payAddress = "TW119mpEuvxMLxs7rAK8CmafLi2hvmLznW";
       // String payAddress = "TEoaGUsnr7gxy9Ypy8Jd2A7MHNCyX6iSNc";
         String payAddress = "TFpbYpYJNbZQMLVA7aVJiULe3HAAqtF7Ca";
        String sign = MD5.encryption(API_KEY + orderNo + payAddress);
//        List<String> listParams = Lists.newArrayList();
//        listParams.add("merchantId=58");
//        listParams.add("sign="+sign);
//        listParams.add("orderNo="+orderNo);
//        listParams.add("payAddress="+payAddress);
//        listParams.add("amount=1.01");
//        listParams.add("returnAddress=http://sportwww.aqensc.com//api/v1/callback/pay");
//        String params = StringUtils.join(listParams, "&");
    //    String response = HttpUtil.createPost(url+"?"+params).execute().body();

       // Map<String, Object> paramsMap = new TreeMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("merchantId", "58");
        jsonObject.put("sign", sign);
        jsonObject.put("orderNo", orderNo);
        jsonObject.put("payAddress", payAddress);
        jsonObject.put("returnAddress", "http://1xwinadmin.aqensc.com/payment/v1/payCloud/payOutNotify");
        jsonObject.put("amount", "0.01");
        String response = HttpUtil.createPost(url).body(jsonObject.toJSONString()).execute().body();
        log.info("返回数据=" + response);
    }

    /**
     * 代收
     */
    @Test
    public void createAddress(){
       // String url = "https://api.adminuu.pro/ucc/pay/createAddress";
        String url = "https://gateway.paycloud.pro/ucc/pay/createAddressForMerchant";
        String orderNo = "MERNO1321538094712178";
        String sign = MD5.encryption(API_KEY + orderNo );
       // Map<String, Object> paramsMap = new TreeMap<>();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("merchantId", "58");
        jsonObject.put("sign", sign);
        jsonObject.put("orderNo", orderNo);
        jsonObject.put("returnAddress", "http://1xwinadmin.aqensc.com/payment/v1/payCloud/payInNotify");
        jsonObject.put("amount", "1.01");
      // jsonObject.put("backUrl","https://www.baidu.com/");
        jsonObject.put("params","{}");
//        List<String> listParams = Lists.newArrayList();
//        listParams.add("merchantId=58");
//        listParams.add("sign="+sign);
//        listParams.add("orderNo="+orderNo);
//        listParams.add("returnAddress=http://sportwww.aqensc.com/api/v1/callback/withdraw");
//        listParams.add("amount=1.02");
        //listParams.add("backUrl=https://www.baidu.com/");
       // String params = StringUtils.join(listParams, "&");
       // String response = HttpUtil.post(url,paramsMap);
        String response = HttpUtil.createPost(url).body(jsonObject.toJSONString()).execute().body();
        log.info("返回数据=" + response);
    }

}
