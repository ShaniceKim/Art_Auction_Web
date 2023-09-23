document.addEventListener('DOMContentLoaded', function() {

    var userPoints = parseInt(document.body.getAttribute("data-user-points"), 10);
    var selectedBank = "카카오뱅크";
    var accountNumberInput = document.getElementById("accountNumber");
    var bankSelect = document.getElementById("bank");

    bankSelect.addEventListener("change", function() {
        selectedBank = bankSelect.value;
        updateHyphenRule();
    });

    updateHyphenRule();

    function updateHyphenRule() {
        var accountNumber = accountNumberInput.value;

                // 은행에 따라 하이픈 규칙 적용
                switch (selectedBank) {
                    case "카카오뱅크":
                        // 카카오뱅크의 경우, 계좌번호 형식에 따라 하이픈을 추가
                        accountNumber = accountNumber.replace(/(\d{3})(\d{2})(\d{7})/, "$1-$2-$3");
                        break;
                    case "NH농협":
                        // NH농협의 경우, 다른 형식에 따라 하이픈을 추가
                        accountNumber = accountNumber.replace(/(\d{3})(\d{4})(\d{4})(\d{2})/, "$1-$2-$3-$4");
                        break;
                    case "KB국민":
                        // KB국민의 경우, 계좌번호 형식에 따라 하이픈을 추가
                        accountNumber = accountNumber.replace(/(\d{6})(\d{2})(\d{6})/, "$1-$2-$3");
                        break;
                    case "신한":
                        // 신한의 경우, 다른 형식에 따라 하이픈을 추가
                        accountNumber = accountNumber.replace(/(\d{3})(\d{3})(\d{6})/, "$1-$2-$3");
                        break;
                    case "하나":
                        // 하나의 경우, 다른 형식에 따라 하이픈을 추가
                        accountNumber = accountNumber.replace(/(\d{4})(\d{3})(\d{6})/, "$1-$2-$3");
                        break;
                    case "우리":
                        // 우리의 경우, 다른 형식에 따라 하이픈을 추가
                        accountNumber = accountNumber.replace(/(\d{4})(\d{3})(\d{6})/, "$1-$2-$3");
                        break;
                }

                // 계좌번호 입력란에 변경된 값 설정
                accountNumberInput.value = accountNumber;
    }

    accountNumberInput.addEventListener("input", function() {
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

    function validateForm() {
        var nameValue = document.getElementById("name").value;
                var accountNumberValue = document.getElementById("accountNumber").value;
                var amountValue = document.getElementById("exchangePoint").value;

                // 이름 검증
                if(!nameValue.trim()) {
                    alert("이름을 입력하세요.");
                    return false; // 함수를 종료하고 다음 코드를 실행하지 않음
                }

                // 계좌번호 검증
                if(!accountNumberValue.trim()) {
                    alert("계좌번호를 입력하세요.");
                    return false; // 함수를 종료하고 다음 코드를 실행하지 않음
                }

                // 환전 금액 검증 (숫자인지 확인)
                if (!/^\d+$/.test(amountValue)) {
                    alert("환전 금액을 숫자로 입력하세요.");
                    return false; // 함수를 종료하고 다음 코드를 실행하지 않음
                }

                // 환전 금액이 사용자의 포인트보다 큰지 검증
                if (parseInt(amountValue, 10) > userPoints) {
                    alert("환전 금액이 보유 포인트를 초과하였습니다.");
                    return false; // 함수를 종료하고 다음 코드를 실행하지 않음
                }

                // 모든 검증이 통과되면 'content' 값을 설정
                var bankValue = document.getElementById("bank").value;
                var contentValue = nameValue + ', ' + accountNumberValue + ', ' + bankValue;
                document.getElementById("content").value = contentValue;

                return true;
    }

    var submitButton = document.querySelector('input[type="submit"]');
    submitButton.addEventListener("click", function(event) {
        event.preventDefault();
        if (validateForm()) {
            document.querySelector("form").submit();
        }
    });

});