package com.rosaloves.bitlyj;

import static com.rosaloves.bitlyj.Bitly.expand;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;

/**
 * ExpandMethod
 * 
 * $Id$
 * 
 * @author clewis Jul 17, 2010
 *
 */
public class ExpandMethodTest {
	Document doc;
	
	@Before
	public void before() {
		doc = Utils.classpathXmlIS("expand_j3.xml");
	}
	
	@Test
	public void name() {
		assertEquals("expand", expand("j3").getName());
	}

	@Test
	public void buildWithSingleHashArgument() {
		BitlyMethod<Url> url = expand("j3");
		assertTrue(url.getParameters().size() == 1);
		assertEquals("j3", url.getParameters().get("hash").get(0));
	}
	
	@Test
	public void buildWithMultipleHashArguments() {
		BitlyMethod<Set<Url>> url = expand("j3", "a3.");
		assertTrue(url.getParameters().size() == 1);
		Assert.assertEquals(2, url.getParameters().get("hash").size());
	}
	
	@Test
	public void buildWithSingleUrlArgument() {
		BitlyMethod<Url> url = expand("http://bit.ly/1YKMfY");
		assertTrue(url.getParameters().size() == 1);
		assertEquals("http://bit.ly/1YKMfY", url.getParameters().get("shortUrl").get(0));
	}
	
	@Test
	public void buildWithMultipleUrlArguments() {
		BitlyMethod<Set<Url>> url = expand("http://bit.ly/1YKMfY", "http://foo");
		assertTrue(url.getParameters().size() == 1);
		Assert.assertEquals(2, url.getParameters().get("shortUrl").size());
	}
	
	@Test
	public void applyToDocument() {
		Url url = expand("j3").apply(doc);
		
		assertEquals("lLWr", url.getGlobalHash());
		assertEquals("j3", url.getUserHash());
		assertEquals("http://www.scotster.com/qf/?1152", url.getLongUrl());
	}
}
