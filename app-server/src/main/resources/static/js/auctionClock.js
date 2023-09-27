// HTML 요소 찾기
var periodElement = document.getElementById('period');
var remainPeriodElement = document.getElementById('RemainPeriod'); // 시간을 표시할 요소 추가

// 가져온 정보에서 필요한 값을 추출
var period = parseInt(periodElement.textContent);

var tid = 0;

function auctionClock() {
    period -= 1;
    if (period < 0) {
        if (tid) clearTimeout(tid);
        return;
    }

    strTime = '';
    var day = parseInt(period / (24 * 60 * 60));
    var hour = parseInt((period / 60 / 60) % 24);
    var minute = parseInt((period / 60) % 60);
    var second = parseInt(period % 60);

    if (day > 0) strTime = day + '일 ';
    strTime = strTime + hour + '시간 ' + minute + '분 ' + second + '초';

    if (remainPeriodElement) {
        remainPeriodElement.innerHTML = strTime; // 시간을 업데이트
    }

    if (tid) clearTimeout(tid);
    tid = window.setTimeout(auctionClock, 1000);
}

if (period > 0) auctionClock();
