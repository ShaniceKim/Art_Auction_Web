let currentUserId;  // Declare currentUserId at top level, so that it can be set from the HTML page.

// 환전 내역 함수 정의
function fetchExchangeData() {
    fetch('/exchange/listUserExchanges')
        .then(response => response.json())
        .then(data => {

            const tbody = document.getElementById('exchangeList');
            tbody.innerHTML = "";

            data.forEach((exchange, index) => {
                const [depositor, accountNumber, bank] = exchange.content.split(',').map(str => str.trim());

                let row = `
                    <tr>
                        <td>${index + 1}</td>
                        <td>${depositor}</td>
                        <td>${bank}</td>
                        <td>${accountNumber}</td>
                        <td>${exchange.exchangePoint}</td>
                        <td>${new Date(exchange.createdDate).toISOString().split('T')[0]}</td>
                    </tr>`;
                tbody.innerHTML += row;
            });

            document.getElementById('exchangeTable').style.display = "table";
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

document.querySelector('a[href="#faq-cat-1"]').addEventListener('click', fetchExchangeData);
document.querySelector('a[data-parent="#accordion-cat-1"]').addEventListener('click', fetchExchangeData);