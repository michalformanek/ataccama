package cz.mformanek.ataccama.interceptor;

import cz.mformanek.ataccama.configuration.DataSourceConfiguration;
import cz.mformanek.ataccama.configuration.DataSourceContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
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

    private final DataSourceConfiguration dataSourceConfiguration;
    private final DataSourceContextHolder dataSourceContextHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {


        Map<String, String> map = new TreeMap<>((Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
        final String databaseName = map.get("databaseName");
        dataSourceConfiguration.setCurrentDatasource(databaseName); //Neexistujici datasource, zahazat query defaultu
        log.info(databaseName);
        log.info("---method executed---");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        log.info("---method executed---");
        dataSourceContextHolder.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        log.info("---Request Completed---");
    }
}