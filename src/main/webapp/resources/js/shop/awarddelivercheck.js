$(function () {
    var awardName = '';

    getList();

    function getList() {
        var listUrl = '/o2o/shopadmin/listuserawardmapsbyshop?pageIndex=1&pageSize=9999&awardName=' + awardName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userAwardMapList = data.userAwardMapList;
                var tempHtml = '';
                userAwardMapList.map(function (item, index) {
                    tempHtml += '' + '<div class="row row-awarddeliver">'
                        + '<div class="col-40">' + item.award.awardName
                        + '</div>'
                        + '<div class="col-30 awarddeliver-time">'
                        + new Date(item.createTime).Format("yyyy-MM-dd")
                        + '</div>' + '<div class="col-15">' + item.user.userName
                        + '</div>'
                        + '<div class="col-15">' + item.point + '</div>'
                        + '</div>';
                });
                $('.awarddeliver-wrap').html(tempHtml);
            }
        });
    }

    $('#search').on('change', function (e) {
        awardName = e.target.value;
        $('.awarddeliver-wrap').empty();
        getList();
    });


});