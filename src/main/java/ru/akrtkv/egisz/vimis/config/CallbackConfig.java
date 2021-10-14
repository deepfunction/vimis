package ru.akrtkv.egisz.vimis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.SoapVersion;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import ru.akrtkv.egisz.vimis.interceptor.CallbackInterceptor;

import java.util.List;

import static ru.akrtkv.egisz.vimis.utils.Constants.CALLBACK_NAMESPACE_URI;

@EnableWs
@Configuration
public class CallbackConfig extends WsConfigurerAdapter {

    private final CallbackInterceptor interceptor;

    @Autowired
    public CallbackConfig(CallbackInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> getMessageDispatcherServlet(ApplicationContext applicationContext) {
        var servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/vimis/callback/*");
    }

    /*
     * wsdl доступно по адресу http://localhost:port/vimis/callback/callback.wsdl
     * */
    @Bean(name = "callback")
    public Wsdl11Definition getDefaultWsdl11Definition(XsdSchema callbackSchema) {
        var wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setCreateSoap12Binding(true);
        wsdl11Definition.setCreateSoap11Binding(false);
        wsdl11Definition.setPortTypeName("CallbackPort");
        wsdl11Definition.setLocationUri("/vimis/callback");
        wsdl11Definition.setTargetNamespace(CALLBACK_NAMESPACE_URI);
        wsdl11Definition.setSchema(callbackSchema);
        return wsdl11Definition;
    }

    @Bean
    public SaajSoapMessageFactory getMessageFactory() {
        var messageFactory = new SaajSoapMessageFactory();
        messageFactory.setSoapVersion(SoapVersion.SOAP_12);
        return messageFactory;
    }

    @Bean
    public XsdSchema getCallbackSchema() {
        return new SimpleXsdSchema(new ClassPathResource("/wsdl/callback.xsd"));
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(interceptor);
    }
}
