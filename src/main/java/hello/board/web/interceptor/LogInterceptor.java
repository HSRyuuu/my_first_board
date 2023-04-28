package hello.board.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString(); // uuid 생성
        request.setAttribute(LOG_ID,uuid); //afterCompletion에서 사용하기위해 request에 넣는다.

        //@RequestMapping을 사용하는 경우 : HandlerMethod가 사용된다.
        //정적 리소스를 사용하는 경우 : ResourceHttpRequestHandler 가 사용된다.
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod) handler;
            //HandlerMethod의 여러가지 메소드를 사용하여 호출할 컨트롤러 메서드의 정보를 얻을 수 있다.
        }
        //log.info("[ REQUEST URI : {}] | [handler : {}] | [uuid : {}]", requestURI, handler, uuid);
        log.info("[ REQUEST URI : {} ]  [ handler : {} ]", requestURI, handler);

        //true 반환 시 다음 인터셉터 또는 컨트롤러가 호출된다.
        //false 반환 시 다음이 호출되지 않고 종료된다.
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(LOG_ID); //preHandle에서 생성한 uuid를 가져온다.
        //오류발생시 오류 로그도 찍어주자.
        if(ex!=null){
            log.error("afterCompletion error!!", ex);
        }
    }
}
