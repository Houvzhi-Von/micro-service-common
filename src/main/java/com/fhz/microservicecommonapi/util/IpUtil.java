package com.fhz.microservicecommonapi.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * @author fenghouzhi
 * @date 2019-07-25 - 11:28
 * @description: IP-工具类
 */
public class IpUtil {

    private static String DEFAULT_IP6 = "0:0:0:0:0:0:0:1";

    private IpUtil() {
    }

    public static String getIpAddr(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_REAL_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if (ip.contains(",")) {
            ip = ip.split(",")[0];
        }

        if (ip.indexOf(':') != -1) {
            try {
                return ipV6ToV4(ip);
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
        }

        return ip;
    }

    private static String ipV6ToV4(String ipV6) throws UnknownHostException {
        InetAddress address = InetAddress.getByName(ipV6);
        if (DEFAULT_IP6.equals(address.getHostAddress())) {
            return "127.0.0.1";
        }

        return InetAddress.getByAddress(
                Arrays.copyOfRange(address.getAddress(), 12, 16))
                .getHostAddress();
    }

    public static String getLocalIP() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                Enumeration inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses != null && inetAddresses.hasMoreElements()) {
                    ip = (InetAddress) inetAddresses.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        return ip.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
        }
        return "127";
    }

}