/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.security;

import org.json.JSONArray;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.function.Function;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;

/**
 * Super secure web token class based on the JSON Web Token specification
 * 
 * @author Justin Heinrichs
 * @version 1.0
 */
public class SecureToken 
{
    private static final Function<String, byte[]> decode = (string) -> Base64
        .getUrlDecoder()
        .decode(string);
        
    private static final Function<byte[], String> base64Encode = (bytes) -> Base64
        .getUrlEncoder()
        .withoutPadding()
        .encodeToString(bytes);

    private static final Function<JSONObject, byte[]> getBytes = (json) -> json
        .toString()
        .getBytes(StandardCharsets.UTF_8);

    private static final Function<JSONObject, String> encodeJSON = getBytes
        .andThen(base64Encode);

    private static final Function<String, JSONObject> decodeJSON = decode
        .andThen(String::new)
        .andThen(JSONObject::new);

    private final JSONObject header;
    private final JSONObject payload;
    private final String signature;

    private SecureToken(JSONObject header, JSONObject payload, String signature) {
        this.header = header;
        this.payload = payload;
        this.signature = signature;
    }

    public String getIssuer() {
        return this.payload.getString("iss");
    }

    public String getSubject() {
        return this.payload.getString("sub");
    }

    public String getAlgorithm() {
        return this.header.getString("alg");
    }

    public List<String> getAudience() {
        ArrayList<String> audience = new ArrayList<>();

        JSONArray aud = this.payload.getJSONArray("aud");
        for (int i = 0; i < aud.length(); i++) {
            audience.add(aud.getString(i));
        }

        return audience;
    }

    public long getIssuedAt() {
        return this.payload.getLong("iat");
    }

    public boolean isValid() {
        try {
            System.out.println(hashedSignature(this.header, this.payload));
            return this.signature.equals(hashedSignature(this.header, this.payload));
        } catch (Exception e) {
            return false;
        }
    }

    private static String hashedSignature(JSONObject header, JSONObject payload) throws Exception {
        String data = String.format("%s.%s", encodeJSON.apply(header), encodeJSON.apply(payload));
        String secret = payload.get("iat").toString();

        System.out.println(header.getString("alg").getBytes());
        
        return encryptedString(header.getString("alg"), data, secret);
    }

    private static String encryptedString(String algorithm, String data, String secret) throws Exception {
        switch (algorithm) {
            case "H256":
                return hash256(data, secret);
            default:
                return hashConcat(data, secret);
        }
    }

    private static String hash256(String input, String secret) throws Exception {      
        Mac mac = Mac.getInstance("HmacSHA256");

        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");

        mac.init(secretKey);
        byte[] hash = mac.doFinal(input.getBytes());

        return base64Encode.apply(hash);   
    }

    private static String hashConcat(String input, String secret) {
        return secret;
    }

    @Override
    public String toString() {
        return String.format("%s.%s.%s", encodeJSON.apply(header), encodeJSON.apply(payload), signature);
    }

    public static class SecureTokenBuilder {
        private final JSONObject header;
        private final JSONObject payload;

        public SecureTokenBuilder(String iss, String sub, String ...auds) {
            JSONObject header = new JSONObject();
            header.put("alg", "H256");

            JSONArray aud = new JSONArray();
            for (String audience : auds) {
                aud.put(audience);
            }

            JSONObject payload = new JSONObject();
            payload.put("iss", iss);
            payload.put("sub", sub);
            payload.put("aud", aud);
            payload.put("iat", System.currentTimeMillis());
            System.out.println(payload);

            this.header = header;
            this.payload = payload;
        }

        public static SecureToken unpack(String token) {
            String[] split = token.split("\\.");

            JSONObject header = decodeJSON.apply(split[0]);
            JSONObject payload = decodeJSON.apply(split[1]);
            String signature = split[2];

            return new SecureToken(header, payload, signature);
        }

        public SecureToken build() throws Exception {
            return new SecureToken(this.header, this.payload, hashedSignature(this.header, this.payload));
        }
    }

}
