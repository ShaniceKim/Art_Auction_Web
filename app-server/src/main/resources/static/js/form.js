document.addEventListener('DOMContentLoaded', function () {

    // 사용자 포인트 및 은행
    var userPoints = parseInt(document.getElementById("userPoints").textContent.replace(/\D/g, ''), 10); // 수정
    var selectedBank = "선택";
    var accountNumberInput = document.getElementById("accountNumber");
    var bankSelect = document.getElementById("bank");

    // 은행 선택 항목 변경 이벤트를 감지하고 은행에 따라 하이픈 규칙 업데이트
    bankSelect.addEventListener("change", function () {
        selectedBank = bankSelect.value;
        resetHyphenRule();
    });

    // 초기 하이픈 규칙 업데이트 실행
    updateHyphenRule();

    // 선택한 은행에 따라 하이픈 규칙을 적용하는 함수
    function updateHyphenRule() {
        var accountNumber = accountNumberInput.value;

        // 은행에 따라 하이픈 규칙 적용
        switch (selectedBank) {
            case "카카오뱅크":
                accountNumber = accountNumber.replace(/(\d{3})(\d{2})(\d{7})/, "$1-$2-$3");
                break;
            case "신한":
                accountNumber = accountNumber.replace(/(\d{3})(\d{3})(\d{6})/, "$1-$2-$3");
                break;
            case "NH농협":
                accountNumber = accountNumber.replace(/(\d{3})(\d{4})(\d{4})(\d{2})/, "$1-$2-$3-$4");
                break;
            case "KB국민":
                accountNumber = accountNumber.replace(/(\d{6})(\d{2})(\d{6})/, "$1-$2-$3");
                break;
            case "하나":
            case "우리":
                accountNumber = accountNumber.replace(/(\d{4})(\d{3})(\d{6})/, "$1-$2-$3");
                break;
        }

        // 계좌번호 입력란에 변경된 값 설정
        accountNumberInput.value = accountNumber;
    }

    // 은행 선택 항목 변경 시 하이픈 규칙을 초기화하는 함수
    function resetHyphenRule() {
        var accountNumber = accountNumberInput.value;

        // 하이픈 제거
        accountNumber = accountNumber.replace(/-/g, "");

        // 계좌번호 입력란에 변경된 값 설정
        accountNumberInput.value = accountNumber;

        // 다시 하이픈 규칙 적용
        updateHyphenRule();
    }

    // 계좌번호 입력란의 input 이벤트를 감지하고 하이픈을 추가 및 포맷 변경
    accountNumberInput.addEventListener("input", function () {
        var formattedAccountNumber = accountNumberInput.value.replace(/[^0-9-]/g, ""); // 숫자와 하이픈만 남김
        var parts = formattedAccountNumber.split("-"); // 하이픈을 기준으로 나눔
        var formattedParts = [];

        // 각 파트에 하이픈 추가
        for (var i = 0; i < parts.length; i++) {
            if (i === 0) {
                // 첫 번째 파트는 은행에 따라 길이가 다르므로 그대로 유지
                formattedParts.push(parts[i]);
            } else {
                // 나머지 파트는 4자리로 만들고 하이픈 추가
                var part = parts[i].substr(0, 4);
                formattedParts.push(part);
            }
        }

        // 하이픈으로 연결된 문자열 생성
        var formattedAccountNumberWithHyphen = formattedParts.join("-");

        // 입력란에 적용
        accountNumberInput.value = formattedAccountNumberWithHyphen;
    });

    // 숫자를 천 단위로 쉼표를 추가하는 함수
    function 쉼표추가(number) {
        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }

// exchangePoint 입력 필드에 값을 입력할 때 쉼표를 제거하는 이벤트 리스너 추가
    var exchangePointInput = document.getElementById("exchangePoint");
    exchangePointInput.addEventListener("input", function () {
        var value = exchangePointInput.value.replace(/,/g, ''); // 쉼표 제거
        exchangePointInput.value = value;
    });


// 숫자를 천 단위로 쉼표를 추가하는 함수
    function 쉼표추가(number) {
        return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }


    // 폼 유효성 검사 함수
    function validateForm() {
        var nameValue = document.getElementById("name").value;
        var accountNumberValue = document.getElementById("accountNumber").value;
        var exchangePointInput = document.getElementById("exchangePoint"); // 수정
        var exchangePointValue = exchangePointInput.value.replace(/,/g, '');


        // 환전 금액이 사용자의 포인트보다 큰지 검증
        if (!accountNumberValue.includes("-")) {
            // "-" 하이픈이 포함되지 않은 경우
            alert("올바른 계좌를 입력해주세요.");

            return false;
        }


        // 이름 검증
        if (!nameValue.trim()) {
            alert("이름을 입력하세요.");
            return false;
        }

        // 계좌번호 검증
        if (!accountNumberValue.trim()) {
            alert("계좌번호를 입력하세요.");
            return false;
        }

        // 환전 금액 검증 (쉼표와 숫자로 확인)
        if (!/^\d+(,\d{1,3})*$/.test(exchangePointValue)) {
            alert("환전 금액을 숫자로 입력하세요.");
            return false;
        }


        // 환전 금액이 사용자의 포인트보다 큰지 검증
        if (parseInt(exchangePointValue, 10) > userPoints) {
            alert("환전 금액이 보유 포인트를 초과하였습니다.");
            return false;
        }

        // 모든 검증이 통과되면 'content' 값을 설정
        var bankValue = document.getElementById("bank").value;
        var contentValue = nameValue + ', ' + accountNumberValue + ', ' + bankValue;
        document.getElementById("content").value = contentValue;

        return true;
    }

    // 제출 버튼 클릭 시 폼 제출 전에 validateForm 함수를 호출하여 유효성 검사 수행
    var submitButton = document.querySelector('input[type="submit"]');
    submitButton.addEventListener("click", function (event) {
        event.preventDefault();
        if (validateForm()) {
            document.querySelector("form").submit();
        }
    });

});
