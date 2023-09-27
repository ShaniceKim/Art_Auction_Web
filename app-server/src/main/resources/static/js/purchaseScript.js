// HTML 요소 찾기
var userNoElement = document.getElementById('userNo');
var userPointElement = document.getElementById('userPoint');
var articleNoElement = document.getElementById('articleNo');
var currentPageElement = document.getElementById('currentPage');
var pathElement = document.getElementById('path');
var endPriceElement = document.getElementById('endPrice');


// 가져온 정보에서 필요한 값을 추출
var userNo = parseInt(userNoElement.textContent);
var userPoint = parseInt(userPointElement.textContent);
var articleNo = parseInt(articleNoElement.textContent);
var currentPage = parseInt(currentPageElement.textContent);
var path = parseInt(pathElement.textContent);
var endPrice = parseInt(endPriceElement.textContent);


document.getElementById('purchaseButton').addEventListener('click', () => {
    // 알림창으로 즉시구매 정보 표시
    var confirmation = confirm(
        '즉시구매가격: ' + endPrice + '\n현재 포인트: ' + userPoint + '\n\n정말 즉시구매 하시겠습니까?'
    );


    // redirectToArticleDetail 함수 정의
    function redirectToArticleDetail() {
        var articleDetailURL = '/article/detail?articleNo=' + articleNo + '&path=' + path + '&currentPage=' + currentPage;
        window.location.href = articleDetailURL;
    }


    if (confirmation) {
        if (userPoint < endPrice) {
            return alert("포인트가 부족합니다.");
        }

        // AJAX 요청
        $.ajax({
            url: '/points/purchasePoint', // 즉시구매 API 엔드포인트 URL
            method: 'POST',
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            async: false, // 동기적으로 서버응답을 처리.
            data: JSON.stringify({
                userNo: userNo, // 사용자 번호
                articleNo: articleNo, // 게시글 번호
                bidAmount: endPrice, // 즉시구매 가격
            }),
            success: (response) => {
                console.log(response);
                if (response === true) {
                    alert("즉시구매가 완료되었습니다.");
                    redirectToArticleDetail();
                } else {
                    alert("즉시구매가 실패했습니다."); // 실패 처리
                }
            }
        });
    }
});
