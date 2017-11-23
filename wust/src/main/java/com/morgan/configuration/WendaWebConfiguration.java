package com.morgan.configuration;
import com.morgan.interceptor.LoginRequiredInterceptor;
import com.morgan.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
//将 拦截器配置到web项目中
@Component
public class WendaWebConfiguration extends WebMvcConfigurerAdapter{
    @Autowired
    LoginRequiredInterceptor loginRequiredInterceptor;

    @Autowired
    PassportInterceptor passportInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginRequiredInterceptor).addPathPatterns("/**");
       registry.addInterceptor(passportInterceptor).addPathPatterns("/user/*");
        super.addInterceptors(registry);
    }
}
