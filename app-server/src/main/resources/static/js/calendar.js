function formatDate(dateStr) {
    let dateObj = new Date(dateStr);
    let year = dateObj.getFullYear();
    let month = (1 + dateObj.getMonth()).toString().padStart(2, '0');
    let day = dateObj.getDate().toString().padStart(2, '0');
    return year + '-' + month + '-' + day;
}

$(document).ready(function() {
    $('#calendar').fullCalendar({
        dayClick: function(date, jsEvent, view) {
            const clickedDate = date.format();
            $.get("/auctionArticle/getEvent", { date: clickedDate }, function(data) {
                let eventDetailHtml = '';
                data.forEach(auctionArticle => {
                    let formattedStartDate = formatDate(auctionArticle.startDateStr);
                    let formattedEndDate = formatDate(auctionArticle.endDateStr);
                    eventDetailHtml += `
                        <div>
                            <strong>Title:</strong> ${auctionArticle.title}
                            <br>
                            <strong>Start Date:</strong> ${formattedStartDate}
                            <br>
                            <strong>End Date:</strong> ${formattedEndDate}
                            <br><br>
                        </div>
                    `;
                });
                $("#eventBox").html(eventDetailHtml);
            });
        }
    });
});