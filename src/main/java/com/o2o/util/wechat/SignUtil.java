package com.o2o.util.wechat;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created by yukai
 * 2019/4/6 10:48
 * 微信请求校验工具类
 */
public class SignUtil {
    //与微信里配置的token一致
    private static String token = "o2o";

    /**
     * 验证微信签名，判断是否是来自微信服务器
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public static boolean checkSignature(String signature, String timestamp, String nonce) {
        String[] arr = new String[]{token, timestamp, nonce};
        //将三个参数进行排序
        Arrays.sort(arr);
        //拼接
        StringBuilder content = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            content.append(arr[i]);
        }
        MessageDigest md = null;
        String tmpStr = null;
        try {
            //微信的加密算法
            md = MessageDigest.getInstance("SHA-1");
            //将字符串进行SHA1加密
            byte[] digest = md.digest(content.toString().getBytes());
            tmpStr = byteToStr(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        content = null;
        return tmpStr!=null?tmpStr.equals(signature.toUpperCase()):false;
    }

    /**
     * 将字节数组转为二进制字符串
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i <byteArray.length; i++) {
            strDigest+=byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }

    /**
     * 将字节转为16进制字符串
     * Java中byte用二进制表示占用8位，而我们知道16进制的每个字符需要用4位二进制位来表示，
     * 所以我们就可以把每个byte转换成两个相应的16进制字符，
     * 即把byte的高4位和低4位分别转换成相应的16进制字符H和L，
     * 并组合起来得到byte转换到16进制字符串的结果new String(H) + new String(L)。
     * 即byte用十六进制表示只占2位。
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = digit[mByte & 0X0F];
        String s = new String(tempArr);
        return s;
    }
}
