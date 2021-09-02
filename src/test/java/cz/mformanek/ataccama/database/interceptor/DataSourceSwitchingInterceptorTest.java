package cz.mformanek.ataccama.database.interceptor;

import cz.mformanek.ataccama.database.configuration.DataSourceContextHolder;
import cz.mformanek.ataccama.database.configuration.DataSourceManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DataSourceSwitchingInterceptorTest {

    @Mock
    DataSourceManager dataSourceManager;

    @Mock
    DataSourceContextHolder dataSourceContextHolder;

    @InjectMocks
    DataSourceSwitchingInterceptor dataSourceSwitchingInterceptor;

    @Test
    void shouldSetDataSourceNameFromRequestParam() throws Exception {
        final var request = new MockHttpServletRequest();
        request.setRequestURI("/database");
        request.setAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, Map.of("databaseName", "test"));
        dataSourceSwitchingInterceptor.preHandle(request, null, null);
        verify(dataSourceManager, times(1)).setCurrentDatasource(eq("test"));
    }

    @Test
    void shouldClearContextWhenDone() throws Exception {
        dataSourceSwitchingInterceptor.postHandle(null, null, null, null);
        verify(dataSourceContextHolder, times(1)).clear();
    }
}