package day.widdle.widdle.global.config

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.io.FileInputStream
import java.security.KeyStore
import java.security.cert.CertificateFactory
import javax.net.ssl.TrustManagerFactory

@Configuration
class SslConfig {

    @Bean
    fun sslContext(): SslContext {
        val mergedTmf = buildMergedTrustManagerFactory()
        return SslContextBuilder.forClient()
            .trustManager(mergedTmf)
            .build()
    }

    private fun buildMergedTrustManagerFactory(): TrustManagerFactory {
        val defaultKs = loadDefaultKeyStore()
        val customCert = loadCustomCertificate("certs/sectigo-r36.pem")

        val mergedKs = KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            load(null, null)
            for (alias in defaultKs.aliases().toList()) {
                setCertificateEntry(alias, defaultKs.getCertificate(alias))
            }
            setCertificateEntry("sectigo-r36", customCert)
        }

        return TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm()).apply {
            init(mergedKs)
        }
    }

    private fun loadDefaultKeyStore(): KeyStore =
        KeyStore.getInstance(KeyStore.getDefaultType()).apply {
            val cacertsPath = System.getProperty("java.home") + "/lib/security/cacerts"
            FileInputStream(cacertsPath).use { load(it, "changeit".toCharArray()) }
        }

    private fun loadCustomCertificate(path: String) =
        CertificateFactory.getInstance("X.509").let { factory ->
            ClassPathResource(path).inputStream.use { factory.generateCertificate(it) }
        }
}
