package cz.mformanek.ataccama.database.configuration;

import cz.mformanek.ataccama.database.interceptor.DataSourceSwitchingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfiguration implements WebMvcConfigurer {

    private final DataSourceSwitchingInterceptor dataSourceSwitchingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(dataSourceSwitchingInterceptor).addPathPatterns("/database/**");
    }

}
