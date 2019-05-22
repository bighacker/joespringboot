package com.joe.joespringboot.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlDbUtils {
    private static final Map<String, String> DecodedConnectionStrings = new ConcurrentHashMap<>();

    private static final Pattern PasswordRegexPattern = Pattern.compile("(Password|Pwd)=(.*);");

    private static String decodeValue(String value) throws Exception {
        byte[] plaintext = Base64.decodeBase64(value);
        byte[] key = "%%benlaiLa_338$".substring(0, 8).getBytes();
        byte[] ciphertext = DESHelper.decrypt(plaintext, key);
        return new String(ciphertext, StandardCharsets.UTF_8);
    }

    public static String decodeConnectionString(String connectionString) throws Exception {
        try {
            String value = DecodedConnectionStrings.get(connectionString);

            if (value != null && !value.isEmpty()) {
                return value;
            }

            StringBuilder stringBuilder = new StringBuilder(connectionString);

            Matcher matcher = PasswordRegexPattern.matcher(connectionString);

            final int pwdGroup = 2;

            while (matcher.find()) {

                String password = matcher.group(pwdGroup);

                if (password == null || password.isEmpty()) {
                    continue;
                }

                if (password.startsWith("'") && password.endsWith("'")) {
                    password = password.substring(1, password.length() - 1);
                }

                String decodedPassword = decodeValue(password);

                stringBuilder.replace(matcher.start(pwdGroup), matcher.end(pwdGroup), decodedPassword);

            }

            String decodedConnectionString = stringBuilder.toString();

            DecodedConnectionStrings.put(connectionString, decodedConnectionString);

            return decodedConnectionString;

        } catch (Exception ignored) {
            throw new Exception("数据库连接字符串解析失败！请检查配置是否正确！");
        }
    }
}
