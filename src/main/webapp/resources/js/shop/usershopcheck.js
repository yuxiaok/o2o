$(function () {
    var userName = '';

    getList();

    function getList() {
        var listUrl = '/o2o/shopadmin/liseusershopmapsbyshop?pageIndex=1&pageSize=9999&userName=' + userName;
        $.getJSON(listUrl, function (data) {
            if (data.success) {
                var userShopMapList = data.userShopMapList;
                var tempHtml = '';
                userShopMapList.map(function (item, index) {
                    tempHtml += ''
                        + '<div class="row row-usershopcheck">'
                        + '<div class="col-50">' + item.user.userName + '</div>'
                        + '<div class="col-50">' + item.point + '</div>'
                        + '</div>';
                });
                $('.usershopcheck-wrap').html(tempHtml);
            }
        });
    }

    $('#search').on('change', function (e) {
        userName = e.target.value;
        $('.usershopcheck-wrap').empty();
        getList();
    });


});