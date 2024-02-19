function timeToLimit() {
    let thisDate = new Date();
    let today = thisDate.getDay();
    // 이번주 월요일
    let thisWeekStart = new Date(thisDate.setDate(thisDate.getDate() - today));
    // 이번주 주말
    let thisWeekend = new Date(thisWeekStart.setDate(thisWeekStart.getDate() + 6));

    let endDate = new Date(thisWeekend.getFullYear(), thisWeekend.getMonth(),
        thisWeekend.getDate(), 23, 59, 59);

    let calcDate = endDate - thisDate;

    // 남은 일수
    let calcSec = parseInt(calcDate) / 1000;
    let calcDays = parseInt(calcSec/60/60/24);
    calcSec = (calcSec - (calcDays * 60 * 60 * 24));
    let calcHours = parseInt(calcSec/60/60);
    calcSec = (calcSec - (calcHours * 60 * 60));
    let calcMinutes = parseInt(calcSec/60);
    calcSec = parseInt(calcSec-(calcMinutes*60));

    document.getElementsByClassName("hot-deal-countdown")[0]
        .innerHTML = `<li>
									\t\t\t\t\t\t\t\t<div>
									\t\t\t\t\t\t\t\t\t<h3>${calcDays}</h3>
									\t\t\t\t\t\t\t\t\t<span>Days</span>
									\t\t\t\t\t\t\t\t</div>
									\t\t\t\t\t\t\t</li>
									\t\t\t\t\t\t\t<li>
									\t\t\t\t\t\t\t\t<div>
									\t\t\t\t\t\t\t\t\t<h3>${calcHours}</h3>
									\t\t\t\t\t\t\t\t\t<span>Hours</span>
									\t\t\t\t\t\t\t\t</div>
									\t\t\t\t\t\t\t</li>
									\t\t\t\t\t\t\t<li>
									\t\t\t\t\t\t\t\t<div>
									\t\t\t\t\t\t\t\t\t<h3>${calcMinutes}</h3>
									\t\t\t\t\t\t\t\t\t<span>Mins</span>
									\t\t\t\t\t\t\t\t</div>
									\t\t\t\t\t\t\t</li>
									\t\t\t\t\t\t\t<li>
									\t\t\t\t\t\t\t\t<div>
									\t\t\t\t\t\t\t\t\t<h3>${calcSec}</h3>
									\t\t\t\t\t\t\t\t\t<span>Secs</span>
									\t\t\t\t\t\t\t\t</div>
									\t\t\t\t\t\t\t</li>`
}
function init() {
    timeToLimit();

    setInterval(timeToLimit, 1000);
}

init();