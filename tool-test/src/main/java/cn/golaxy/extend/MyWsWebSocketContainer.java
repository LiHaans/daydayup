package cn.golaxy.extend;


import org.apache.tomcat.util.res.StringManager;
import org.apache.tomcat.websocket.WsWebSocketContainer;
import org.apache.tomcat.websocket.pojo.PojoEndpointClient;

import javax.websocket.*;
import java.net.URI;
import java.util.Arrays;

public class MyWsWebSocketContainer extends WsWebSocketContainer {
    private static final StringManager sm = StringManager.getManager(WsWebSocketContainer.class);

    public Session connectToServer( Object pojo, ClientEndpointConfig config, URI path) throws DeploymentException {


        ClientEndpoint annotation = (ClientEndpoint)pojo.getClass().getAnnotation(ClientEndpoint.class);
        if (annotation == null) {
            throw new DeploymentException(sm.getString("wsWebSocketContainer.missingAnnotation", new Object[]{pojo.getClass().getName()}));
        } else {
            Endpoint ep = new PojoEndpointClient(pojo, Arrays.asList(annotation.decoders()));
            Class<? extends ClientEndpointConfig.Configurator> configuratorClazz = annotation.configurator();
            ClientEndpointConfig.Configurator configurator = null;
            if (!ClientEndpointConfig.Configurator.class.equals(configuratorClazz)) {
                try {
                    configurator = (ClientEndpointConfig.Configurator)configuratorClazz.getConstructor().newInstance();
                } catch (ReflectiveOperationException var9) {
                    throw new DeploymentException(sm.getString("wsWebSocketContainer.defaultConfiguratorFail"), var9);
                }
            }

             return this.connectToServer((Endpoint)ep, config, path);
        }
    }
}
