// HTML 요소 찾기
var userNoElement = document.getElementById('userNo');
var userPointElement = document.getElementById('userPoint');


// 가져온 정보에서 필요한 값을 추출
var userNo = parseInt(userNoElement.textContent);
var userPoint = parseInt(userPointElement.textContent);

var IMP = window.IMP;

// 천 단위로 콤마(,) 추가 함수
function formatCurrency(input) {
    // 숫자 형식으로 변환하여 콤마 추가
    var value = input.value.replace(/\D/g, '');
    input.value = numberWithCommas(value);
}

// 숫자에 콤마(,) 추가하는 함수
function numberWithCommas(x) {
    return x.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',');
}

// "포인트 충전" 버튼 클릭 시 실행될 함수
document.getElementById('rechargeButton').addEventListener('click', function () {
    var rechargeInput = document.getElementById('rechargeAmount');
    var rechargeAmount = parseInt(rechargeInput.value.replace(/\D/g, '')); // 입력된 충전 포인트를 숫자로 변환


    if (isNaN(rechargeAmount) || rechargeAmount <= 0) {
        alert('올바른 포인트를 입력하세요.'); // 유효한 포인트를 입력하지 않은 경우 알림
        return;
    }

    // KG이니시스 창을 열어 포인트를 충전하고, 성공 또는 실패 여부를 처리합니다.
    IMP.init('imp15221536'); // KG이니시스 가맹점 식별코드를 설정

    IMP.request_pay({
        pg: 'html5_inicis', // PG사 설정 (KG이니시스)
        pay_method: 'card', // 결제 방식 (신용카드)
        merchant_uid: 'recharge_' + new Date().getTime(), // 충전 고유 번호 생성
        name: '비트갤러리_포인트충전',
        amount: rechargeAmount, // 충전 금액 설정
        stats: '충전', // 변경: stats 추가
        buyer_email: '', // 구매자 이메일
    }, function (rsp) {
        if (rsp.success) {
            alert('포인트 충전이 완료되었습니다.');
            console.log(rsp); // 충전 정보 확인

            // AJAX 요청을 서버로 보냄
            $.ajax({
                url: '/points/chargePoint', // 포인트 업데이트 API 엔드포인트 URL
                method: 'POST',
                data: {
                    userNo: userNo, // 사용자 번호
                    bidAmount: rechargeAmount, // 충전 금액
                },
                success: function (data) {
                    console.log(data);
                    try {
                        location.reload();
                        alert('포인트 충전 결과:\n사용자 번호: ' + resultData.userNo + '\n입찰 포인트: ' + resultData.bidAmount);
                    } catch (e) {
                        console.error('JSON 파싱 오류:', e);
                    }
                },
                error: function (error) {
                    console.error('포인트 업데이트 실패', error);
                }
            });
        } else {
            alert('포인트 충전에 실패하였습니다.');
            console.log(rsp); // 충전 실패 정보 확인
        }
    });
});
