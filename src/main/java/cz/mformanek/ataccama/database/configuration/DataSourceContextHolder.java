package cz.mformanek.ataccama.database.configuration;


import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DataSourceContextHolder {

    private static final ThreadLocal<String> datasource = new ThreadLocal<>();

    public void setDatasource(String datasource) {
        DataSourceContextHolder.datasource.set(datasource);
    }

    public String currentDatasource() {
        return datasource.get();
    }

    public void clear() {
        DataSourceContextHolder.datasource.remove();
    }

}