package cz.mformanek.ataccama.database.interceptor;

import cz.mformanek.ataccama.database.configuration.DataSourceContextHolder;
import cz.mformanek.ataccama.database.configuration.DataSourceManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.TreeMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataSourceSwitchingInterceptor implements HandlerInterceptor {

    private final DataSourceManager dataSourceManager;
    private final DataSourceContextHolder dataSourceContextHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Map<String, String> pathVariables = new TreeMap<>((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
        final String databaseName = pathVariables.get("databaseName");
        log.debug("Resolved datasource {} from request", databaseName);
        dataSourceManager.setCurrentDatasource(databaseName);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        log.debug("Clearing context holder");
        dataSourceContextHolder.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
    }
}