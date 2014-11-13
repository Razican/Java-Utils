package com.razican.utils;

import org.junit.Assert;
import org.junit.Test;

public class MathUtilsTest {

	@Test
	public void testUByteToInt() {
		Assert.assertEquals(0, MathUtils.uByteToInt((byte) 0));
		Assert.assertEquals(50, MathUtils.uByteToInt((byte) 0x32));
		Assert.assertEquals(128, MathUtils.uByteToInt((byte) 0x80));
		Assert.assertEquals(200, MathUtils.uByteToInt((byte) 0xC8));
	}

	@Test
	public void testUByteToShort() {
		Assert.assertEquals((short) 0, MathUtils.uByteToShort((byte) 0));
		Assert.assertEquals((short) 50, MathUtils.uByteToShort((byte) 0x32));
		Assert.assertEquals((short) 128, MathUtils.uByteToShort((byte) 0x80));
		Assert.assertEquals((short) 200, MathUtils.uByteToShort((byte) 0xC8));
	}
	
	@Test
	public void testTwoByteToShort() {
		Assert.assertEquals((short) 0, MathUtils.twoByteToShort((byte) 0, (byte) 0));
		Assert.assertEquals((short) 12309, MathUtils.twoByteToShort((byte) 0x30, (byte) 0x15));
		Assert.assertEquals((short) -1687, MathUtils.twoByteToShort((byte) 0xF9, (byte) 0x69));
	}
	
	@Test
	public void testToHex() {
		Assert.assertEquals("00", MathUtils.toHex((byte) 0));
		Assert.assertEquals("30", MathUtils.toHex((byte) 0x30));
		Assert.assertEquals("F9", MathUtils.toHex((byte) 0xF9));
	}
	
	@Test
	public void testGetByte() {
		Assert.assertEquals((byte) 0, MathUtils.getByte(0, 2));
		Assert.assertEquals((byte) 0x01, MathUtils.getByte(0x03F501, 0));
		Assert.assertEquals((byte) 0xF5, MathUtils.getByte(0x03F501, 1));
		Assert.assertEquals((byte) 0x03, MathUtils.getByte(0x03F501, 2));
		Assert.assertEquals((byte) 0x6F, MathUtils.getByte(0x6F544194, 3));
		Assert.assertEquals((byte) 0, MathUtils.getByte(0x6F544194, 4));
	}
}