package com.heiban.springboot.config;

public class AlipayConfig {

    // 商户appid
    public static String APPID = "2016102100730795";
    // 私钥 pkcs8格式的
    public static String RSA_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDohqdWEiXwzS4LccfHnjUwRUwT+ZWqJa13GRbV+zxL4jGN/7kptKo9oBgpZd0eRGa1+sZJ26emyywJExeo0FMXp9ey2NcfQKAZkEHwnMCrv/fxQ90B/Eu5uy8ta+RGE8l1QqmiKILqK0sDkUydQUmFhiuFdheZgqnXAsvb0tkjMPb76IJY3mPxiAG+D1YQ9E8QwrqVll5teMPNzM3fTKyAdS7+W9sG1IIua88AgtQiim2mErqi1V+dX07/HA6w+zuPrm2YJPTaGnRi2iLLZ3Ah/bgBsjfNF5RJnvMiay5p6GX+SOx6CNTrgzNePwkVeHHvhU6CnXV52ArIIHXqwtc9AgMBAAECggEABV4+dL08hP+npXn9+7zKS4/9/qQPcXsnMw2wOkZvkOzq6fVSaAUUvRF6HVKNtUos+NyOQVxB1ZbxMFBxhmMw1+X3E9jvtnf+ejibXRuxUwW+iqJfctFJSkNpBmpsPbXtO7jLQ8QdqZxQQgfTtfmw4dVEIfOk/dVHaIBXBn8qpiZK9mwY15s4ftg+IeJIAgDAcWXQAAoXg3jMM+CkJnTsPnOu6fzCOOCvc5eli9nICSqdrzwJmULajOeMIH1YLWxsN53aLFj6EqYgn4F5aB1TuCa5y6ab5Oqjd3IVKUreKysFwevLIc13yWpI+6WdnuZYmIaKGjlyWjDzjnp8SHtf3QKBgQD/f+pvhtPucd1TKWL1dWw0GMBMmtJAO/Oa1uIJA/zNVF2s9soxDI0dolKMumm35XSVju1WDpgMFJJ/683Id/koiROmL+if64jh22FEzHTEu266v1s5hLhXyqwljd8BZa6UCDQyzDe9sJP+bVbw5futTWxgX52aa3IhpvwtKfO4vwKBgQDo+ziScpnsb3fBrdBvcvbbJ65FDuKmTeSk38NXMsazdIWYa3PZak4a5e1pnQL22qtxPfChcMzXCzylG4RONuBLiDNDkPzPfwgiv/fzjcxKB4/YI/wKYE7tomM38ZpPhvw+bZzA2o2mOWZBpH75z7nD+mWkESP3iwwbpcmiGK6TAwKBgQDxMCV0hoiFrQNLP+Rk5iXwv/S32+biMDX0UykpbZQ1mJCdeJLB3qFP+i2HH2ow6PA3RqcQ99HchYcOLuEhfu+7rjrYRJ2KaWPcIaYetgr8tBsBOya4k2sZpP8ArH556Sz2HRB5a4KrFIeARGMwIUqF/HJ0P46deRRSZDnlwJaMLQKBgCPsQG/MPdGH6mYxn7bfehKWjrdVMTtFPWDyo5L5YEvhRowW3VkDnCFnnHCxZficBN1EGKs70knCEMrAcGkC6xiaWpUrcN+pjZ6WDOw/y+UKP3KrMbyD1werBzNkDFAA1znk+st9p2Oe7BZo/68TCWifXjaq8enERcyphmp49cnzAoGABf/JTmur7ffj7j71kSE7x4y9H0u4fPSEGBUBw1vUeKXtPdbFrG7WRPzUTOWXx0WwzaZVAL3SCSwbPD5C/VJ20FKvky6FXqtVz1GQb9EFO0dVPUpPZGotBpBfY7hG0iX+FkTyi3bliWvu0DVFTZ56f2TWUbyIPSr+9va1UJWp/Ec=";
    // 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://127.0.0.1/notify_url";
    // 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
    public static String return_url = "http://127.0.0.1/return_url";
    // 请求网关地址
    public static String URL = "https://openapi.alipaydev.com/gateway.do";
    // 编码
    public static String CHARSET = "UTF-8";
    // 返回格式
    public static String FORMAT = "json";
    // 支付宝公钥
    public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvr882cjPWhTErx1TxFYlOeCimOstQV6rlHx1gLf9tKE0CpJX3CPOKU3bXJCEHLmZDFI6GIGT82GSrn9NWzE4FZcD327ExorF/2n30TSYXWe9odxV3ppyR4qF63LnubyNDiC4UUk01eQ6JZ+qJIuNgT3gUqptS4vY/CSJ8AefndVy+pNLDNzhiSwA0JXheBWfW8Yy45WGXAeouDX1KbmQd7x/Uxnutlkb/VOLj6JbwoS1B4sm5fW1kETYnMSfnydothq25QzGQZ7V78a43qe+9l0cKcgtPTc1Ktyvi/KCbii6LZUJiAFhiF65Y0eCpkXKP045tbL+8x8Qjx4NMtOaCQIDAQAB";
    // 日志记录目录
    public static String log_path = "/log";
    // RSA2
    public static String SIGNTYPE = "RSA2";

}
