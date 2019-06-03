$(function () {
    var productId = getQueryString('productId');
    var productUrl = '/o2o/fronted/listproductdetailpageinfo?productId=' + productId;
    var $normalPrice = $("#normal-price");
    var $promotionPrice = $("#promotion-price");

    $.getJSON(
        productUrl, function (data) {
            if (data.success) {
                var product = data.product;
                //商品缩略图
                $('#product-img').attr('src', getContextPath() + product.imgAddr);
                $('#product-time').text(new Date(product.lastEditTime).format("Y-m-d"));
                /*$('#product-name').text(product.productName);*/
                $('#title').text(product.productName);
                $('#product-desc').text(product.productDesc);
                if (product.point != undefined && product.point > 0)
                    $('#point').text('购买可得' + product.point + '积分')
                if (product.normalPrice !== undefined && product.promotionPrice !== undefined) {
                    // 如果现价和原价都不为空，则都展示，并给原价加个删除符号
                    $normalPrice.html('原价: <del>¥' + product.normalPrice + '</del>');
                    $promotionPrice.html('现价: ¥' + product.promotionPrice);
                } else if (product.normalPrice !== undefined && product.promotionPrice === undefined) {
                    // 如果原价不为空，而现价为空，则只展示原价
                    $promotionPrice.html('现价: ¥' + product.normalPrice);
                } else if (product.normalPrice === undefined && product.promotionPrice !== undefined) {
                    // 如果原价为空，现价不为空，则只展示现价
                    $promotionPrice.html('现价: ¥' + product.promotionPrice);
                }

                // 获取商品详情图片列表
                var productDetailImgList = product.productImgList;
                var swiperHtml = '';
                productDetailImgList.map(function (item, index) {
                    swiperHtml += ''
                        + '<div class="swiper-slide img-wrap">'
                        + '<a href="' + item.lineLink + '" external>'
                        + '<img class="banner-img img-detail" src="' + getContextPath() + item.imgAddr + '" alt="' + item.imgDesc + '">'
                        + '</a>'
                        + '</div>';
                });
                // if (data.needQRCode) {
                // 生成购买商品的二维码供商家扫描
                swiperHtml += '<div> <img src="/o2o/fronted/generateqrcode4product?productId=' + product.productId + '" ' +
                    'width="100%"/></div>';
                // }
                
                //将轮播图赋值给前端HTML控件
                $('.swiper-wrapper').html(swiperHtml);
                //设定轮播图轮换时间为3s
                /*$(".swiper-container").swiper({
                    autoplay: 1500,
                    //用户对轮播图进行操作时，是否自动停止轮播
                    autoplayDisableOnInteraction: false
                });*/
            }
        });
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});