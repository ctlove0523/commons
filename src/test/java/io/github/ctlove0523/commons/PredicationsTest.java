package io.github.ctlove0523.commons;

import org.junit.Assert;
import org.junit.Test;
import org.junit.function.ThrowingRunnable;

public class PredicationsTest {

	@Test
	public void test_stringNull() {
		String name = null;
		String errorMessage = "message must not be null";
		Assert.assertThrows(errorMessage, IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() {
				Predications.stringNotNull(name, errorMessage);
			}
		});
	}

	@Test
	public void test_stringEmpty() {
		String name = "";
		String errorMessage = "message must not be empty";
		Assert.assertThrows(errorMessage, IllegalArgumentException.class, new ThrowingRunnable() {
			@Override
			public void run() {
				Predications.stringNotNull(name, errorMessage);
			}
		});
	}

	@Test
	public void test_stringValid() {
		String name = "predications";
		Assert.assertEquals(name, Predications.stringNotNull(name, ""));
	}
}
