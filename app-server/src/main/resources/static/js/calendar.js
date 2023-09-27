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
            $.get("/article/getEvent", { date: clickedDate }, function(data) {
                let eventDetailHtml = '';

                data.forEach(article => {
                    let formattedStartDate = formatDate(article.startDateStr);
                    let formattedEndDate = formatDate(article.endDateStr);
                    eventDetailHtml += `


                        <div class="auction-box">
                            <div class="auction-title">
                                ${article.title}
                            </div>
                            <div class="auction-date">
                                <strong>Start Date:</strong> ${formattedStartDate}
                            </div>
                            <div class="auction-date">
                                <strong>End Date:</strong> ${formattedEndDate}
                            </div>
                        </div>
                    `;
                });
                $("#eventBox").html(eventDetailHtml);
            });
        }
    });
});