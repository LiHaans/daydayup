package cn.golaxy.extend;

import javax.websocket.ContainerProvider;
import java.util.Iterator;
import java.util.ServiceLoader;

public class MyContainerProvider  extends ContainerProvider {

    @Override
    protected MyWsWebSocketContainer getContainer() {
        MyWsWebSocketContainer result = null;
        ServiceLoader<MyContainerProvider> serviceLoader = ServiceLoader.load(MyContainerProvider.class);

        for(Iterator<MyContainerProvider> iter = serviceLoader.iterator(); result == null && iter.hasNext(); result = ((MyContainerProvider)iter.next()).getContainer()) {
        }

        if (result == null) {
            try {
                Class<MyWsWebSocketContainer> clazz = (Class<MyWsWebSocketContainer>) Class.forName(MyWsWebSocketContainer.class.getName());
                result = (MyWsWebSocketContainer)clazz.getConstructor().newInstance();
            } catch (IllegalArgumentException | SecurityException | ReflectiveOperationException var4) {
            }
        }

        return result;
    }

    public static MyWsWebSocketContainer getWebSocketContainer() {
        MyWsWebSocketContainer result = null;
        ServiceLoader<MyContainerProvider> serviceLoader = ServiceLoader.load(MyContainerProvider.class);

        for(Iterator<MyContainerProvider> iter = serviceLoader.iterator(); result == null && iter.hasNext(); result = ((MyContainerProvider)iter.next()).getContainer()) {
        }

        if (result == null) {
            try {
                Class<MyWsWebSocketContainer> clazz = (Class<MyWsWebSocketContainer>) Class.forName(MyWsWebSocketContainer.class.getName());
                result = (MyWsWebSocketContainer) clazz.getConstructor().newInstance();
            } catch (IllegalArgumentException | SecurityException | ReflectiveOperationException var4) {
            }
        }

        return result;
    }
}
