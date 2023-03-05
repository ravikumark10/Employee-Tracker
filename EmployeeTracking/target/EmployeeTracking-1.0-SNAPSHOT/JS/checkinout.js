window.onload = function () {
    var seconds = 00;
    var minutes = 00;
    var hours = 00;
    var timer = document.getElementById("timer")
    var buttontoggle = document.getElementById('toggle-check');
    var Interval ;
    var flag="1";
    function checkToggle(){

        if(flag==="1") {
            flag="0";
            buttontoggle.innerHTML = "Check Out";
            clearInterval(Interval);
            Interval = setInterval(startTimer, 1000);
            // window.location.href='./checkin';
        }else {
            flag="1";
            buttontoggle.innerHTML = "Check In";
            clearInterval(Interval);
            timer.innerHTML = "00:00:00";
        }

        function startTimer () {
            seconds++;
            if(seconds===60){
                seconds=0;
                minutes++;
                if(minutes===60){
                    minutes=0;
                    hours++;
                }
            }
            if(seconds <= 9){
                seconds = "0" + seconds;
            }
            if (hours<=9) {
                if(minutes<=9) {
                    timer.innerHTML = "0" + hours + ":" + "0" + minutes + ":" + seconds;
                }else{
                    timer.innerHTML = "0" + hours + ":" + minutes + ":" + seconds;
                }
            }else{
                if(minutes<=9) {
                    timer.innerHTML = "0" + hours + ":" + "0" + minutes + ":" + seconds;
                }else{
                    timer.innerHTML = "0" + hours + ":" + minutes + ":" + seconds;
                }
            }
        }
    }



    // buttonReset.onclick = function() {
    //     clearInterval(Interval);
    //     tens = "00";
    //     seconds = "00";
    //     appendTens.innerHTML = tens;
    //     appendSeconds.innerHTML = seconds;
    // }

}