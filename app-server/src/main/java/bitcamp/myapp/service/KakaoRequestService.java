package bitcamp.myapp.service;

import bitcamp.myapp.vo.KakaoInfo;
import bitcamp.myapp.vo.SocialInfo;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@ComponentScan(basePackages = "bitcamp.myapp")
public class KakaoRequestService implements SocialRequestService{

    private WebClient webClient;

    private final String grantType = "authorization_code";
    private final String clientId = "6884053a11c3626cab670763f77b2bf0";
    private final String redirectUri = "http://localhost/social/kakao";
    private String code = "";
    private final String accessTokenUri = "https://kauth.kakao.com/oauth/token";
    private final String clientSecret = "";// 보안이 ON 일 경우 필수 설정해야함



    @Override
    public String KakaoTokenRequest(SocialInfo socialInfo) {



        KakaoInfo info = (KakaoInfo) socialInfo;
        code = info.getCode();
        //요청 바디 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", grantType);
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        //params.add("client_secret", clientSecret); // 보안 했을경우 필수

        //요청
//        System.out.println("요청시작" + "\n" + grantType +"\n"+ clientId +"\n"
//                + redirectUri +"\n"
//                + code +"\n"
//                + accessTokenUri +"\n");
        System.out.println("요청시작!!! " + redirectUri);
        String response = WebClient.create("https://api.example.com")
                .post()
                .uri(accessTokenUri) // 대상 서버의 엔드포인트로 변경
                .header("Content-Type", "application/x-www-form-urlencoded;charset=utf-8")
                .body(BodyInserters.fromFormData(params))
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 요청을 동기적으로 실행하고 응답을 받습니다.
        //System.out.println("요청 종료");
        return response;

    }

    public String KakaoUserInfoRequest(String token, String userInfoUri) {

        //요청
        //System.out.println("요청시작" );

        String response = WebClient.create("https://kapi.kakao.com/v2/user/me")
                .get()
                .uri(userInfoUri) // 대상 서버의 엔드포인트로 변경
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(String.class)
                .block(); // 요청을 동기적으로 실행하고 응답을 받습니다.
        //System.out.println("요청 종료");
        return response;

    }
}
