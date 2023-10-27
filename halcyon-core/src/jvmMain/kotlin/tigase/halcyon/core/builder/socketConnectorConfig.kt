package tigase.halcyon.core.builder

import tigase.halcyon.core.connector.DnsResolver
import tigase.halcyon.core.connector.socket.DefaultHostnameVerifier
import tigase.halcyon.core.connector.socket.DnsResolverMiniDns
import tigase.halcyon.core.connector.socket.SocketConnectorConfig
import tigase.halcyon.core.connector.socket.XMPPHostnameVerifier
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

@HalcyonConfigDsl
class SocketConnectionBuilder : ConnectionConfigItemBuilder<SocketConnectorConfig> {

	var hostname: String? = null

	var port: Int = 5222

	var trustManager: X509TrustManager? = null

	var dnsResolver: DnsResolver = DnsResolverMiniDns()

	var hostnameVerifier: XMPPHostnameVerifier = DefaultHostnameVerifier()

	override fun build(root: ConfigurationBuilder, defaultDomain: String?): SocketConnectorConfig {
		return SocketConnectorConfig(
			hostname = hostname,
			domain = defaultDomain ?: throw ConfigurationException("Cannot determine domain name."),
			port = port,
			trustManager = trustManager ?: object : X509TrustManager {
				override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
				}

				override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
				}

				override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
			},
			dnsResolver = dnsResolver,
			hostnameVerifier = hostnameVerifier
		)
	}
}

fun ConfigurationBuilder.socketConnector(init: SocketConnectionBuilder.() -> Unit) {
	val n = SocketConnectionBuilder()
	n.init()
	this.connection = n
}