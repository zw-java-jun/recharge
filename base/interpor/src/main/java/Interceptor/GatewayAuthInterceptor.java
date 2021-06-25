//package Interceptor;
//
//import com.pj.auth.GatewayAuthUtil;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * 网关验证拦截器
// * @author kong
// *
// */
//public class GatewayAuthInterceptor implements HandlerInterceptor {
//
//
//	// 之前
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		// 验证网关令牌
//		String gatewayAuthToken = request.getHeader(GatewayAuthUtil.REQUEST_TOKEN_KEY);
//		if(GatewayAuthUtil.verifyToken(gatewayAuthToken) == false) {
//			// 验证失败，请求不通过
//			response.setContentType("application/json; charset=utf-8");
//			return false;
//		}
//		// 请求通过
//		return true;
//	}
//
//	// 之后
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
//			throws Exception {
//		// System.out.println("========== 之后 ==========");
//	}
//
//	// 最终
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		// System.out.println("========== 最终 ==========");
//		// System.out.println(ex);
//	}
//
//
//
//
//}
