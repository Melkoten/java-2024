import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.security.Key;
import java.security.KeyStore;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

public class Main {
    public static void main(String[] args) throws Exception {
        // Загрузка keystore, вывод ключа и сертификата из него
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyStoreStream = new FileInputStream("C:/Users/Melkoten/IdeaProjects/ForApi/clientkeystore.jks")) {
            keyStore.load(keyStoreStream, "123456".toCharArray());
        }
        System.out.println(keyStore.containsAlias("keypair")); //Такой alias есть -true
        System.out.println(keyStore.containsAlias("keypair1"));//Такой тоже есть -true
        System.out.println(keyStore.containsAlias("keypair2"));//Такой alias отсутствует -false

        Key key=keyStore.getKey("keypair", "123456".toCharArray());//Возьмём ключ из хранилища
        System.out.println(key); //Выведем ключ по объекту
        System.out.println(keyStore.getKey("keypair", "123456".toCharArray())); //Выведем ключ без создания объекта Key

        Certificate cert= keyStore.getCertificate("serverCert");//Возьмём сертификат из хранилища
        System.out.println(cert);//Выведем сертификат по объекту
        System.out.println(keyStore.getCertificate("serverCert"));//Выведем сертификат без создания объекта

        System.out.println(keyStore.getCertificateAlias(cert));


        // Загрузка cacerts, вывод сертификата из него по alias
        KeyStore serverStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyStoreStream = new FileInputStream("C:/Users/Melkoten/.jdks/openjdk-23/lib/security/cacerts")) {
            serverStore.load(keyStoreStream, "123456".toCharArray());
        }
        System.out.println(serverStore.aliases());
        System.out.println(serverStore.getCertificate("localhost:3000"));
    }
}