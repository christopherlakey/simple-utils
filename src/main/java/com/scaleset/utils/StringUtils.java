package com.scaleset.utils;


import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class StringUtils {


	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

	/**
	 * Returns a String object holding the value of the specified String. If the
	 * string is null, then the defalt value is returned instead.
	 * 
	 * @param s
	 * @param fallback
	 * @return
	 */
	public static String valueOf(String s, String fallback) {
		String result = fallback;
		if (s != null) {
			result = s;
		}
		return result;
	}

	public static String valueOf(double value) {
		String result = Double.toString(value);
		return result;
	}

	public static String valueOf(float value) {
		String result = Float.toString(value);
		return result;
	}

	public static String valueOf(int value) {
		String result = Integer.toString(value);
		return result;
	}

	public static String valueOf(long value) {
		String result = Long.toString(value);
		return result;
	}

	public static String valueOf(Object obj) {
		return valueOf(obj, null);
	}

	public static boolean empty(String str) {
		return str == null || "".equals(str);
	}

	public static boolean notEmpty(String str) {
		return str != null && !"".equals(str);
	}

	/**
	 * Returns a String object holding the value of the specified Object. If the
	 * object is null or if there is an issue converting the object, then the
	 * defalt value is returned instead. Supported types include: String, Integer,
	 * Long
	 * 
	 * @param s
	 * @param fallback
	 * @return
	 */
	public static String valueOf(Object obj, String fallback) {
		String result = fallback;
		if (obj instanceof String) {
			result = (String) obj;
		} else if (obj instanceof Date) {
			Date date = (Date) obj;
			result = DATE_FORMAT.format(date);
		} else if (obj != null) {
			result = obj.toString();
		}
		return result;
	}

	/**
	 * Copied from Spring Framework StringUtils
	 * 
	 * Check whether the given CharSequence has actual text. More specifically,
	 * returns <code>true</code> if the string not <code>null</code>, its length
	 * is greater than 0, and it contains at least one non-whitespace character.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * 
	 * @param str
	 *          the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not <code>null</code>, its
	 *         length is greater than 0, and it does not contain whitespace only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Copied from Spring Framework StringUtils
	 * 
	 * Check that the given CharSequence is neither <code>null</code> nor of
	 * length 0. Note: Will return <code>true</code> for a CharSequence that
	 * purely consists of whitespace.
	 * <p>
	 * 
	 * <pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * 
	 * @param str
	 *          the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}
}
