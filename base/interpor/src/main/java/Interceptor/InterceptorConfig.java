//package Interceptor;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * 注册项目中的拦截器
// *
// * @author kong
// *
// */
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// 网关拦截器，不能通过验证的请求，直接返回: 无效请求
//		registry.addInterceptor(new GatewayAuthInterceptor()).addPathPatterns("/**").excludePathPatterns("/user/**");
//	}
//}