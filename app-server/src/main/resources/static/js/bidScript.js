// HTML 요소 찾기
var userNoElement = document.getElementById('userNo');
var userPointElement = document.getElementById('userPoint');
var articleNoElement = document.getElementById('articleNo');
var cuPriceNoElement = document.getElementById('cuPrice');
var currentPageElement = document.getElementById('currentPage');


// 가져온 정보에서 필요한 값을 추출
var userNo = parseInt(userNoElement.textContent);
var userPoint = parseInt(userPointElement.textContent);
var articleNo = parseInt(articleNoElement.textContent);
var cuPrice = parseInt(cuPriceNoElement.textContent);
var currentPage = parseInt(currentPageElement.textContent);

// AJX 요청을 저장할 변수
var ajaxRequest;

// 숫자에 콤마(,) 추가하는 함수
function formatCurrency(input) {
    // 숫자 형식으로 변환하여 콤마 추가
    var value = input.value.replace(/\D/g, '');
    input.value = numberWithCommas(value);
}

// 숫자에 콤마(,) 추가하는 함수
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}


// "입찰" 버튼 클릭 시 실행될 함수
document.getElementById('bidButton').addEventListener('click', function () {
    var bidInput = document.getElementById('bidAmount');
    var bidAmount = parseInt(bidInput.value.replace(/\D/g, '')); // 입력된 입찰 금액을 숫자로 변환


    if (isNaN(bidAmount) || bidAmount <= 0) {
        alert('올바른 입찰 금액을 입력하세요.'); // 유효한 금액을 입력하지 않은 경우 알림
        if (ajaxRequest) {
            ajaxRequest.abort();
        }
        return;
    }

    if (userPoint < bidAmount) {
        alert('보유 포인트가 부족합니다'); // 보유 포인트가 입찰 금액보다 작은 경우 알림
        if (ajaxRequest) {
            ajaxRequest.abort();
        }
        return;
    }

    if (endPrice < bidAmount) {
        alert('즉시 구매가격 보다 입찰금액이 높습니다.'); // 보유 포인트가 입찰 금액보다 작은 경우 알림
        if (ajaxRequest) {
            ajaxRequest.abort();
        }
        return;
    }

    // AJAX 요청을 서버로 보냄
    // 결제검증 (DB의 최신값을 불러와서 요청한 입찰금액과 맞는 지 확인하고 결제를 진행함.)
    ajaxRequest  = $.ajax({
        url: '/points/checkPoint', // 결제 검증을 위한 엔드포인트 URL
        method: 'POST',
        data: {
            userNo: userNo, // 사용자 번호
            articleNo: articleNo, // 게시글 번호
            bidAmount: bidAmount, // 입찰 금액
            currentPage: currentPage, // 현재 페이지
        },
        async: false, // 동기적으로 서버 응답을 처리
        success: function (data) {
            console.log(data);
            console.log("현재페이지 : " + currentPage)
            if (data.isValidBid === true) { // isValidBid 값을 확인하여 성공 또는 실패 처리
                // 결제 검증이 성공하면 포인트 업데이트 요청을 보냄
                $.ajax({
                    url: '/points/bidPoint', // 포인트 업데이트 API 엔드포인트 URL
                    method: 'POST',
                    data: {

                        userNo: userNo, // 사용자 번호
                        articleNo: articleNo, // 게시글 번호
                        bidAmount: bidAmount, // 입찰 금액
                        currentPage: currentPage,
                    },
                    async: false, // 동기적으로 서버응답을 처리
                    success: function (data) {
                        console.log(data);
                        try {
                            var resultData = JSON.parse(JSON.stringify(data));
                            alert(resultData.bidAmount+' 포인트를 입찰하였습니다.' );
                            location.reload();

                        } catch (e) {
                            console.error('JSON 파싱 오류:', e);
                        }
                    },
                    error: function (error) {
                        console.error('포인트 업데이트 실패', error);
                    }
                });
            } else {
                alert('현재가격보다 높은 금액을 입찰해주세요');
                location.reload();
            }
            console.log("d")

        },
        error: function (error) {
            console.error('입찰 실패', error);
        }
    });

});
