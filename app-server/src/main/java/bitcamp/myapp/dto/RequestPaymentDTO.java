package bitcamp.myapp.dto;

import java.util.Map;

public class RequestPaymentDTO {
    private String userNo;
    private String articleNo;
    private int bidAmount;

    public String getUserNo() {
        return userNo;
    }

    public String getArticleNo() {
        return articleNo;
    }

    public int getBidAmount() {
        return bidAmount;
    }

    
    private Map<String, Object> responseData; // 응답 데이터를 저장할 맵

    // 생성자, 게터, 세터 등 필요한 메서드 추가

    public void setResponseData(Map<String, Object> responseData) {
        this.responseData = responseData;
    }

    public Map<String, Object> getResponseData() {
        return responseData;
    }


}
