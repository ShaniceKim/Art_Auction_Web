// 함수로 만들어진 클릭 이벤트 핸들러
function loadBidList() {
    document.getElementById('bidTable').style.display = 'table';
    fetch('/history/api/bidList')
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById('bidListBody');
            tbody.innerHTML = ""; // 내용 초기화
            data.forEach((history, index) => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                <td>${index + 1}</td>
                <td>${history.article.title}</td>
                <td>${history.price}</td>
                <td>${history.winBid}</td>
                <td>${new Date(history.bidDate).toLocaleString()}</td>

            `;
                tbody.appendChild(tr);
            });
        })
        .catch(error => {
            console.error('Error:', error);
            alert('입찰 내역을 불러오는 중 오류가 발생했습니다.');
        });
}

// 클릭 이벤트 핸들러 함수를 연결
document.querySelector('h4.panel-title').addEventListener('click', loadBidList);
document.querySelector('li a[href="#faq-cat-2"]').addEventListener('click', loadBidList);
