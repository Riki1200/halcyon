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
package tigase.halcyon.core.xmpp.modules

import tigase.halcyon.core.requests.Request
import tigase.halcyon.core.xml.Element
import tigase.halcyon.core.xml.element
import tigase.halcyon.core.xml.response
import tigase.halcyon.core.xmpp.ErrorCondition
import tigase.halcyon.core.xmpp.JID
import tigase.halcyon.core.xmpp.XMPPException

class PingModule : tigase.halcyon.core.modules.AbstractXmppIQModule(
	TYPE, arrayOf(XMLNS), tigase.halcyon.core.modules.Criterion.chain(
		tigase.halcyon.core.modules.Criterion.name("iq"), tigase.halcyon.core.modules.Criterion.xmlns(XMLNS)
	)
) {

	companion object {
		const val XMLNS = "urn:xmpp:ping"
		const val TYPE = XMLNS
	}

	fun ping(jid: JID? = null): Request {
		val stanza = element("iq") {
			id()
			if (jid != null) attribute("to", jid.toString())
			"ping"{
				xmlns = XMLNS
			}
		}
		return context.writer.write(stanza)
	}

	override fun processGet(element: Element) {
		context.writer.write(response(element) { })
	}

	override fun processSet(element: Element) {
		throw XMPPException(ErrorCondition.NotAcceptable)
	}

}