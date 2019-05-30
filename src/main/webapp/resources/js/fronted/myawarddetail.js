$(function () {
    var userAwardId = getQueryString('userAwardId');
    var awardUrl = '/o2o/fronted/getawardbyuserawardid?userAwardId=' + userAwardId;

    $.getJSON(
        awardUrl, function (data) {
            if (data.success) {
                var award = data.product;
                //商品缩略图
                $('#award-img').attr('src', getContextPath() + award.awardImg);
                $('#create-time').text(new Date(award.lastEditTime).format("Y-m-d"));
                $('#award-name').text(award.awardName)
                $('#award-desc').text(award.awardDesc);

                var swiperHtml = '';
                if (data.usedStatus == 0) {
                    swiperHtml += '<div><img src="/o2o/fronted/generateqrcode4award?userAwardId="' + userAwardId + 'width="100%"/>< /div>'
                    $('#imgList').html(swiperHtml);
                }
            }
        });
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});