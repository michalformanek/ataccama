package cz.mformanek.ataccama.configuration;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DataSourceContextHolder {

    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public void setDatasource(String datasource) {
        threadLocal.set(datasource);
    }

    public String currentDatasource() {
        return threadLocal.get();
    }

    public static void clear() {
        threadLocal.remove();
    }

}