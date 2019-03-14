/*
 * Tigase Halcyon XMPP Library
 * Copyright (C) 2018 Tigase, Inc. (office@tigase.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */
package tigase.halcyon.core.xml

import junit.framework.TestCase.*
import org.junit.Test

class ElementTest {

	private fun createElement(): Element {
		val b = ElementBuilder.create("message").attribute("to", "romeo@example.net").attribute("from", "juliet@example.com/balcony").attribute("type", "chat").child("subject")
			.value("I implore you!").up().child("body").value("Wherefore art thou, Romeo?").up().child("thread").value("e0ffe42b28561960c6b12b944a092794b9683a38").up().child("x").value("tigase:offline").xmlns("tigase")
		return b.build()
	}

	@Test
	fun testFindChild() {
		val element = createElement()

		var nullElement = element.findChild("message", "missing")
		assertNull(nullElement)

		nullElement = element.findChild("x", "body")
		assertNull(nullElement)

		val c = element.findChild("message", "body")
		assertNotNull(c)
		assertEquals("body", c!!.name)
		assertEquals("Wherefore art thou, Romeo?", c.value)

		System.out.println(element.getAsString())
	}

	@Test
	fun testNextSibling() {
		val element = createElement()
		val c = element.findChild("message", "body")
		assertNotNull(c)
		assertEquals("body", c!!.name)
		assertEquals("thread", c.getNextSibling()!!.name)
	}
}
