$(function () {
    var loading = false;
    var maxItems = 20;
    var pageSize = 10;

    var listUrl = '/o2o/fronted/listuserawardmapsbycustomer';

    var pageNum = 1;
    var productName = '';

    function addItems(pageSize, pageIndex) {
        // 生成新条目的HTML
        var url = listUrl + '?shopId=1&' + 'pageIndex=' + pageIndex
            + '&pageSize=' + pageSize + '&productName=' + productName;
        loading = true;
        $.getJSON(url, function (data) {
            if (data.success) {
                maxItems = data.count;
                var html = '';
                data.userAwardMapList.map(function (item, index) {
                    var status = '';
                    if (item.usedStatus == 0) {
                        status = "未领取";
                    } else if (item.usedStatus == 1) {
                        status = "已领取";
                    }
                    html += '' + '<div class="card" data-award-id='
                        + item.userAwardId + '>'
                        + '<div class="card-header">' + item.shop.shopName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.award.awardName
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.createTime).Format("yyyy-MM-dd")
                        + '</p>' + '<span>消费积分:' + item.point + '</span>'
                        + '</div>' + '</div>';
                });
                $('.list-div').append(html);
                var total = $('.list-div .card').length;
                if (total >= maxItems) {
                    // 隐藏加载提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    // 显示加载提示符
                    $('.infinite-scroll-preloader').show();
                }
                pageNum += 1;
                loading = false;
                $.refreshScroller();
            }
        });
    }

    addItems(pageSize, pageNum);

    $('.list-div').on('click', '.card', function (e) {
        var userAwardId = e.currentTarget.dataset.userAwardId;
        window.location.href = '/o2o/fronted/myawarddetail?userAwardId=' + userAwardId;
    })

    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    $('#search').on('change', function (e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});
