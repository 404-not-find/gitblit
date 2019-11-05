/*
 * Copyright 2017 gitblit.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitblit.utils;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author Florian Zschocke
 *
 */
public class PasswordHashTest {

	static final String MD5_PASSWORD_0 = "password";
	static final String MD5_HASHED_ENTRY_0 = "MD5:5F4DCC3B5AA765D61D8327DEB882CF99";
	static final String MD5_PASSWORD_1 = "This is a test password";
	static final String MD5_HASHED_ENTRY_1 = "md5:8e1901831af502c0f842d4efb9083bcf";
	static final String CMD5_USERNAME_0 = "Jane Doe";
	static final String CMD5_PASSWORD_0 = "password";
	static final String CMD5_HASHED_ENTRY_0 = "CMD5:DB9639A6E5F21457F9DFD7735FAFA68B";
	static final String CMD5_USERNAME_1 = "Joe Black";
	static final String CMD5_PASSWORD_1 = "ThisIsAWeirdScheme.Weird";
	static final String CMD5_HASHED_ENTRY_1 = "cmd5:5c154768287e32fa605656b98894da89";


	/**
	 * Test method for {@link com.gitblit.utils.PasswordHash#instanceOf(java.lang.String)} for MD5.
	 */
	@Test
	public void testInstanceOfMD5() {

		PasswordHash pwdh = PasswordHash.instanceOf("md5");
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.MD5, pwdh.type);
		assertTrue("Failed to match " +MD5_HASHED_ENTRY_1, pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_1.toCharArray(), null));

		pwdh = PasswordHash.instanceOf("MD5");
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.MD5, pwdh.type);
		assertTrue("Failed to match " +MD5_HASHED_ENTRY_0, pwdh.matches(MD5_HASHED_ENTRY_0, MD5_PASSWORD_0.toCharArray(), null));

		pwdh = PasswordHash.instanceOf("mD5");
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.MD5, pwdh.type);
		assertTrue("Failed to match " +MD5_HASHED_ENTRY_1, pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_1.toCharArray(), null));


		pwdh = PasswordHash.instanceOf("CMD5");
		assertNotNull(pwdh);
		assertNotEquals(PasswordHash.Type.MD5, pwdh.type);
		assertFalse("Failed to match " +MD5_HASHED_ENTRY_1, pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_1.toCharArray(), null));
	}



	/**
	 * Test method for {@link com.gitblit.utils.PasswordHash#instanceOf(java.lang.String)} for combined MD5.
	 */
	@Test
	public void testInstanceOfCombinedMD5() {

		PasswordHash pwdh = PasswordHash.instanceOf("cmd5");
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.CMD5, pwdh.type);
		assertTrue("Failed to match " +CMD5_HASHED_ENTRY_1, pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), CMD5_USERNAME_1));

		pwdh = PasswordHash.instanceOf("cMD5");
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.CMD5, pwdh.type);
		assertTrue("Failed to match " +CMD5_HASHED_ENTRY_1, pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), CMD5_USERNAME_1));

		pwdh = PasswordHash.instanceOf("CMD5");
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.CMD5, pwdh.type);
		assertTrue("Failed to match " +CMD5_HASHED_ENTRY_0, pwdh.matches(CMD5_HASHED_ENTRY_0, CMD5_PASSWORD_0.toCharArray(), CMD5_USERNAME_0));


		pwdh = PasswordHash.instanceOf("MD5");
		assertNotNull(pwdh);
		assertNotEquals(PasswordHash.Type.CMD5, pwdh.type);
		assertFalse("Failed to match " +CMD5_HASHED_ENTRY_1, pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), CMD5_USERNAME_1));
	}







	/**
	 * Test that no instance is returned for plaintext or unknown or not
	 * yet implemented hashing schemes.
	 */
	@Test
	public void testNoInstanceOf() {
		PasswordHash pwdh = PasswordHash.instanceOf("plain");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceOf("PLAIN");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceOf("Plain");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceOf("scrypt");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceOf("bCrypt");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceOf("BCRYPT");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceOf("nixe");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceOf(null);
		assertNull(pwdh);
	}



	/**
	 * Test that for all known hash types an instance is created for a hashed entry
	 * that can verify the known password.
	 *
	 * Test method for {@link com.gitblit.utils.PasswordHash#instanceFor(java.lang.String)}.
	 */
	@Test
	public void testInstanceFor() {
		PasswordHash pwdh = PasswordHash.instanceFor(MD5_HASHED_ENTRY_0);
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.MD5, pwdh.type);
		assertTrue("Failed to match " +MD5_HASHED_ENTRY_0, pwdh.matches(MD5_HASHED_ENTRY_0, MD5_PASSWORD_0.toCharArray(), null));

		pwdh = PasswordHash.instanceFor(MD5_HASHED_ENTRY_1);
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.MD5, pwdh.type);
		assertTrue("Failed to match " +MD5_HASHED_ENTRY_1, pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_1.toCharArray(), null));


		pwdh = PasswordHash.instanceFor(CMD5_HASHED_ENTRY_0);
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.CMD5, pwdh.type);
		assertTrue("Failed to match " +CMD5_HASHED_ENTRY_0, pwdh.matches(CMD5_HASHED_ENTRY_0, CMD5_PASSWORD_0.toCharArray(), CMD5_USERNAME_0));

		pwdh = PasswordHash.instanceFor(CMD5_HASHED_ENTRY_1);
		assertNotNull(pwdh);
		assertEquals(PasswordHash.Type.CMD5, pwdh.type);
		assertTrue("Failed to match " +CMD5_HASHED_ENTRY_1, pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), CMD5_USERNAME_1));


	}

	/**
	 * Test that for no instance is returned for plaintext or unknown or
	 * not yet implemented hashing schemes.
	 *
	 * Test method for {@link com.gitblit.utils.PasswordHash#instanceFor(java.lang.String)}.
	 */
	@Test
	public void testInstanceForNaught() {
		PasswordHash pwdh = PasswordHash.instanceFor("password");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceFor("top secret");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceFor("pass:word");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceFor("PLAIN:password");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceFor("SCRYPT:1232rwv12w");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceFor("BCRYPT:urbvahiaufbvhabaiuevuzggubsbliue");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceFor("");
		assertNull(pwdh);

		pwdh = PasswordHash.instanceFor(null);
		assertNull(pwdh);
	}


	/**
	 * Test method for {@link com.gitblit.utils.PasswordHash#isHashedEntry(java.lang.String)}.
	 */
	@Test
	public void testIsHashedEntry() {
		assertTrue(MD5_HASHED_ENTRY_0, PasswordHash.isHashedEntry(MD5_HASHED_ENTRY_0));
		assertTrue(MD5_HASHED_ENTRY_1, PasswordHash.isHashedEntry(MD5_HASHED_ENTRY_1));
		assertTrue(CMD5_HASHED_ENTRY_0, PasswordHash.isHashedEntry(CMD5_HASHED_ENTRY_0));
		assertTrue(CMD5_HASHED_ENTRY_1, PasswordHash.isHashedEntry(CMD5_HASHED_ENTRY_1));

		assertFalse(MD5_PASSWORD_1, PasswordHash.isHashedEntry(MD5_PASSWORD_1));
		assertFalse("topsecret", PasswordHash.isHashedEntry("topsecret"));
		assertFalse("top:secret", PasswordHash.isHashedEntry("top:secret"));
		assertFalse("secret Password", PasswordHash.isHashedEntry("secret Password"));
		assertFalse("Empty string", PasswordHash.isHashedEntry(""));
		assertFalse("Null", PasswordHash.isHashedEntry(null));
	}

	/**
	 * Test that hashed entry detection is not case sensitive on the hash type identifier.
	 *
	 * Test method for {@link com.gitblit.utils.PasswordHash#isHashedEntry(java.lang.String)}.
	 */
	@Test
	public void testIsHashedEntryCaseInsenitive() {
		assertTrue(MD5_HASHED_ENTRY_1.toLowerCase(), PasswordHash.isHashedEntry(MD5_HASHED_ENTRY_1.toLowerCase()));
		assertTrue(CMD5_HASHED_ENTRY_1.toLowerCase(), PasswordHash.isHashedEntry(CMD5_HASHED_ENTRY_1.toLowerCase()));
	}

	/**
	 * Test that unknown or not yet implemented hashing schemes are not detected as hashed entries.
	 *
	 * Test method for {@link com.gitblit.utils.PasswordHash#isHashedEntry(java.lang.String)}.
	 */
	@Test
	public void testIsHashedEntryUnknown() {
		assertFalse("BCRYPT:thisismypassword", PasswordHash.isHashedEntry("BCRYPT:thisismypassword"));
		assertFalse("TSTHSH:asdchabufzuzfbhbakrzburzbcuzkuzcbajhbcasjdhbckajsbc", PasswordHash.isHashedEntry("TSTHSH:asdchabufzuzfbhbakrzburzbcuzkuzcbajhbcasjdhbckajsbc"));
	}




	/**
	 * Test creating a hashed entry for scheme MD5. In this scheme there is no salt, so a direct
	 * comparison to a constant value is possible.
	 *
	 * Test method for {@link com.gitblit.utils.PasswordHash#toHashedEntry(String, String)} for MD5.
	 */
	@Test
	public void testToHashedEntryMD5() {
		PasswordHash pwdh = PasswordHash.instanceOf("MD5");
		String hashedEntry = pwdh.toHashedEntry(MD5_PASSWORD_1, null);
		assertTrue(MD5_HASHED_ENTRY_1.equalsIgnoreCase(hashedEntry));

		hashedEntry = pwdh.toHashedEntry(MD5_PASSWORD_1, "charlie");
		assertTrue(MD5_HASHED_ENTRY_1.equalsIgnoreCase(hashedEntry));

		hashedEntry = pwdh.toHashedEntry("badpassword", "charlie");
		assertFalse(MD5_HASHED_ENTRY_1.equalsIgnoreCase(hashedEntry));

		hashedEntry = pwdh.toHashedEntry("badpassword", null);
		assertFalse(MD5_HASHED_ENTRY_1.equalsIgnoreCase(hashedEntry));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToHashedEntryMD5NullPassword() {
		PasswordHash pwdh = PasswordHash.instanceOf("MD5");
		pwdh.toHashedEntry(null, null);
	}


	/**
	 * Test creating a hashed entry for scheme Combined-MD5. In this scheme there is no salt, so a direct
	 * comparison to a constant value is possible.
	 *
	 * Test method for {@link com.gitblit.utils.PasswordHash#toHashedEntry(String, String)} for CMD5.
	 */
	@Test
	public void testToHashedEntryCMD5() {
		PasswordHash pwdh = PasswordHash.instanceOf("CMD5");
		String hashedEntry = pwdh.toHashedEntry(CMD5_PASSWORD_1, CMD5_USERNAME_1);
		assertTrue(CMD5_HASHED_ENTRY_1.equalsIgnoreCase(hashedEntry));

		hashedEntry = pwdh.toHashedEntry(CMD5_PASSWORD_1, "charlie");
		assertFalse(CMD5_HASHED_ENTRY_1.equalsIgnoreCase(hashedEntry));

		hashedEntry = pwdh.toHashedEntry("badpassword", "charlie");
		assertFalse(MD5_HASHED_ENTRY_1.equalsIgnoreCase(hashedEntry));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToHashedEntryCMD5NullPassword() {
		PasswordHash pwdh = PasswordHash.instanceOf("CMD5");
		pwdh.toHashedEntry(null, CMD5_USERNAME_1);
	}

	/**
	 * Test creating a hashed entry for scheme Combined-MD5, when no user is given.
	 * This should never happen in the application, so we expect an exception to be thrown.
	 *
	 * Test method for {@link com.gitblit.utils.PasswordHash#toHashedEntry(String, String)} for broken CMD5.
	 */
	@Test
	public void testToHashedEntryCMD5NoUsername() {
		PasswordHash pwdh = PasswordHash.instanceOf("CMD5");
		try {
			String hashedEntry = pwdh.toHashedEntry(CMD5_PASSWORD_1, "");
			fail("CMD5 cannot work with an empty '' username. Got: " + hashedEntry);
		}
		catch (IllegalArgumentException ignored) { /*success*/ }

		try {
			String hashedEntry = pwdh.toHashedEntry(CMD5_PASSWORD_1, "   ");
			fail("CMD5 cannot work with an empty '   ' username. Got: " + hashedEntry);
		}
		catch (IllegalArgumentException ignored) { /*success*/ }

		try {
			String hashedEntry = pwdh.toHashedEntry(CMD5_PASSWORD_1, "	");
			fail("CMD5 cannot work with an empty ' ' username. Got: " + hashedEntry);
		}
		catch (IllegalArgumentException ignored) { /*success*/ }

		try {
			String hashedEntry = pwdh.toHashedEntry(CMD5_PASSWORD_1, null);
			fail("CMD5 cannot work with a null username. Got: " + hashedEntry);
		}
		catch (IllegalArgumentException ignored) { /*success*/ }
	}




	/**
	 * Test method for {@link com.gitblit.utils.PasswordHash#matches(String, char[], String)} for MD5.
	 */
	@Test
	public void testMatchesMD5() {
		PasswordHash pwdh = PasswordHash.instanceOf("MD5");

		assertTrue("PWD0, Null user", pwdh.matches(MD5_HASHED_ENTRY_0, MD5_PASSWORD_0.toCharArray(), null));
		assertTrue("PWD0, Empty user", pwdh.matches(MD5_HASHED_ENTRY_0, MD5_PASSWORD_0.toCharArray(), ""));
		assertTrue("PWD0, With user", pwdh.matches(MD5_HASHED_ENTRY_0, MD5_PASSWORD_0.toCharArray(), "maxine"));

		assertTrue("PWD1, Null user", pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_1.toCharArray(), null));
		assertTrue("PWD1, Empty user", pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_1.toCharArray(), ""));
		assertTrue("PWD1, With user", pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_1.toCharArray(), "maxine"));



		assertFalse("Matched wrong password", pwdh.matches(MD5_HASHED_ENTRY_1, "wrongpassword".toCharArray(), null));
		assertFalse("Matched wrong password, with empty user", pwdh.matches(MD5_HASHED_ENTRY_1, "wrongpassword".toCharArray(), "  "));
		assertFalse("Matched wrong password, with user", pwdh.matches(MD5_HASHED_ENTRY_1, "wrongpassword".toCharArray(), "someuser"));

		assertFalse("Matched empty password", pwdh.matches(MD5_HASHED_ENTRY_1, "".toCharArray(), null));
		assertFalse("Matched empty password, with empty user", pwdh.matches(MD5_HASHED_ENTRY_1, " ".toCharArray(), "  "));
		assertFalse("Matched empty password, with user", pwdh.matches(MD5_HASHED_ENTRY_1, "	".toCharArray(), "someuser"));

		assertFalse("Matched null password", pwdh.matches(MD5_HASHED_ENTRY_1, null, null));
		assertFalse("Matched null password, with empty user", pwdh.matches(MD5_HASHED_ENTRY_1, null, "  "));
		assertFalse("Matched null password, with user", pwdh.matches(MD5_HASHED_ENTRY_1, null, "someuser"));


		assertFalse("Matched wrong hashed entry", pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_0.toCharArray(), null));
		assertFalse("Matched wrong hashed entry, with empty user", pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_0.toCharArray(), ""));
		assertFalse("Matched wrong hashed entry, with user", pwdh.matches(MD5_HASHED_ENTRY_1, MD5_PASSWORD_0.toCharArray(), "someuser"));

		assertFalse("Matched empty hashed entry", pwdh.matches("", MD5_PASSWORD_0.toCharArray(), null));
		assertFalse("Matched empty hashed entry, with empty user", pwdh.matches("  ", MD5_PASSWORD_0.toCharArray(), ""));
		assertFalse("Matched empty hashed entry, with user", pwdh.matches("	", MD5_PASSWORD_0.toCharArray(), "someuser"));

		assertFalse("Matched null entry", pwdh.matches(null, MD5_PASSWORD_0.toCharArray(), null));
		assertFalse("Matched null entry, with empty user", pwdh.matches(null, MD5_PASSWORD_0.toCharArray(), ""));
		assertFalse("Matched null entry, with user", pwdh.matches(null, MD5_PASSWORD_0.toCharArray(), "someuser"));


		assertFalse("Matched wrong scheme", pwdh.matches(CMD5_HASHED_ENTRY_0, MD5_PASSWORD_0.toCharArray(), null));
		assertFalse("Matched wrong scheme", pwdh.matches(CMD5_HASHED_ENTRY_0, CMD5_PASSWORD_0.toCharArray(), CMD5_USERNAME_0));
	}

	/**
	 * Test method for {@link com.gitblit.utils.PasswordHash#matches(String, char[], String)} for Combined-MD5.
	 */
	@Test
	public void testMatchesCombinedMD5() {
		PasswordHash pwdh = PasswordHash.instanceOf("CMD5");

		assertTrue("PWD0", pwdh.matches(CMD5_HASHED_ENTRY_0, CMD5_PASSWORD_0.toCharArray(), CMD5_USERNAME_0));
		assertTrue("Empty user", pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), CMD5_USERNAME_1));



		assertFalse("Matched wrong password", pwdh.matches(CMD5_HASHED_ENTRY_1, "wrongpassword".toCharArray(), CMD5_USERNAME_1));
		assertFalse("Matched wrong password", pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_0.toCharArray(), CMD5_USERNAME_1));

		assertFalse("Matched wrong user", pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), CMD5_USERNAME_0));
		assertFalse("Matched wrong user", pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), "Samantha Jones"));

		assertFalse("Matched empty user", pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), ""));
		assertFalse("Matched empty user", pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), "    "));
		assertFalse("Matched null user", pwdh.matches(CMD5_HASHED_ENTRY_1, CMD5_PASSWORD_1.toCharArray(), null));

		assertFalse("Matched empty hashed entry", pwdh.matches("", CMD5_PASSWORD_0.toCharArray(), CMD5_USERNAME_0));
		assertFalse("Matched empty hashed entry, with empty user", pwdh.matches("  ", CMD5_PASSWORD_1.toCharArray(), ""));
		assertFalse("Matched empty hashed entry, with null user", pwdh.matches("	", CMD5_PASSWORD_0.toCharArray(), null));

		assertFalse("Matched null entry, with user", pwdh.matches(null, CMD5_PASSWORD_1.toCharArray(), CMD5_USERNAME_1));
		assertFalse("Matched null entry, with empty user", pwdh.matches(null, CMD5_PASSWORD_1.toCharArray(), ""));
		assertFalse("Matched null entry, with null user", pwdh.matches(null, CMD5_PASSWORD_1.toCharArray(), null));


		assertFalse("Matched wrong scheme", pwdh.matches(MD5_HASHED_ENTRY_0, CMD5_PASSWORD_0.toCharArray(), null));
		assertFalse("Matched wrong scheme", pwdh.matches(MD5_HASHED_ENTRY_0, CMD5_PASSWORD_0.toCharArray(), CMD5_USERNAME_0));
		assertFalse("Matched wrong scheme", pwdh.matches(MD5_HASHED_ENTRY_0, MD5_PASSWORD_0.toCharArray(), CMD5_USERNAME_0));
	}
}
