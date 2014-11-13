package com.razican.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.CharacterCodingException;

import org.junit.Assert;
import org.junit.Test;

public class StringUtilsTest {

	@Test
	public void testFirstToUpper() {
		
		Assert.assertEquals("", StringUtils.firstToUpper(""));
		Assert.assertEquals("Abc123", StringUtils.firstToUpper("abc123"));
		Assert.assertEquals("ABC123", StringUtils.firstToUpper("ABC123"));
		Assert.assertEquals("123abc", StringUtils.firstToUpper("123abc"));
	}
	
	@Test
	public void testSha1() {
		
		Assert.assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709", StringUtils.sha1());
		Assert.assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709", StringUtils.sha1(""));
		Assert.assertEquals("6367c48dd193d56ea7b0baad25b19455e529f5ee", StringUtils.sha1("abc123"));
		Assert.assertEquals("c963b7df20488e9ef50c1a309c1fa747ab5d8822", StringUtils.sha1("hj6¬"));
		
		char[] test5 = {};
		char[] test6 = {'a', 'b', 'c', '1', '2', '3'};
		char[] test7 = {'h', 'j', '6', '¬'};
		
		Assert.assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709", StringUtils.sha1(test5));
		Assert.assertEquals("6367c48dd193d56ea7b0baad25b19455e529f5ee", StringUtils.sha1(test6));
		Assert.assertEquals("c963b7df20488e9ef50c1a309c1fa747ab5d8822", StringUtils.sha1(test7));
	}
	
	@Test
	public void testToByte() throws CharacterCodingException {
		
		char[] test1 = {'0', '4', 'F', 'j'};
		char[] test2 = {'h', 'j', '6', '¬'};
		char[] test3 = {'ó', 'ñ', '~', 'Ϩ'};
		
		
		byte[] result1 = {48, 52, 70, 106};
		byte[] result2 = {104, 106, 54, -62, -84};
		byte[] result3 = {-61, -77, -61, -79, 126, -49, -88};
		
		
		
		Assert.assertArrayEquals(result1, StringUtils.toByte(test1));
		Assert.assertArrayEquals(result2, StringUtils.toByte(test2, "UTF-8"));
		Assert.assertArrayEquals(result3, StringUtils.toByte(test3, "UTF-8"));
	}
	
	@Test
	public void testToChar() throws UnsupportedEncodingException {
		
		byte[] test1 = {48, 52, 70, 106};
		byte[] test2 = {104, 106, 54, -62, -84};
		byte[] test3 = {-61, -77, -61, -79, 126, -49, -88};
		
		char[] result1 = {'0', '4', 'F', 'j'};
		char[] result2 = {'h', 'j', '6', '¬'};
		char[] result3 = {'ó', 'ñ', '~', 'Ϩ'};
		
		Assert.assertArrayEquals(result1, StringUtils.toChar(test1, "UTF-8"));
		Assert.assertArrayEquals(result2, StringUtils.toChar(test2, "UTF-8"));
		Assert.assertArrayEquals(result3, StringUtils.toChar(test3, "UTF-8"));
	}
}