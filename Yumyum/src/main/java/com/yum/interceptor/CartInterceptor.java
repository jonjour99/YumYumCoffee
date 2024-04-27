package com.yum.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yum.domain.MemberDTO;

public class CartInterceptor implements HandlerInterceptor{
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        System.out.println("preHandle1" + request.getRequestURI());
        	
        // TODO 인증여부 판단 후 로그인 페이지로 보낼 로직
		HttpSession session = request.getSession();
			// TODO 회원 DTO 만들어지면 연결
		MemberDTO mvo = (MemberDTO)session.getAttribute("member");
		
		if(mvo == null) {
			response.sendRedirect("/main");
			return false;
		} else {
			return true;
		}       

    }
 
    @Override	// 클라이언트의 요청을 처리한 뒤에 호출. 컨트롤러에서 예외 발생시 실행 X
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
        System.out.println("postHandle1");
        
        
    }
 
    @Override	// afterCompletion : 클라이언트 요청을 마치고 클라이언트에서 뷰를 통해 응답을 전송한 뒤 실행. 뷰를 생성할 떄 예외에도 실행
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        
        System.out.println("afterCompletion1");
        
        
    }

}