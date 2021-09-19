package com.tarefas.utils;

import java.security.MessageDigest;
import java.util.Base64;

public class EncryptUtil {

	// Fonte:
	// https://stackoverflow.com/questions/29495049/how-to-decode-decrypt-md5-encryption-using-java

	private final MessageDigest md;

	public EncryptUtil() throws SecurityException {
		try {
			md = MessageDigest.getInstance("MD5", "SUN");
		} catch (Exception se) {
			throw new SecurityException("In MD5 constructor " + se);
		}
	}

	public String encode(String in) throws Exception {
		if (in == null) {
			return null;
		}
		try {
			byte[] raw = null;
			byte[] stringBytes = null;
			stringBytes = in.getBytes("UTF8");
			synchronized (md) {
				raw = md.digest(stringBytes);
			}
			// substituído: BASE64Encoder is deprecated
			Base64.Encoder encoder = Base64.getEncoder().withoutPadding();
			return encoder.encodeToString(raw);
		} catch (Exception se) {
			throw new Exception("Exception while encoding " + se);
		}

	}

	public String decode(String in) {
		throw new RuntimeException("NOT SUPPORTED");
	}
}
