$(function () {

    var productId = getQueryString('productId');
    var productUrl = '/o2o/fronted/listproductdetailpageinfo?productId=' + productId;


    $.getJSON(productUrl, function (data) {
        alert(JSON.stringify(data));
        if (data.success) {
            var product = data.product;
            $('#product-img').attr('src', product.imgAddr);
            $('#product-time').text(
                new Date(product.lastEditTime)
                    .Format("yyyy-MM-dd"));
            if (product.point != undefined) {
                $('#product-point').text("购买可得" + product.point + "积分")
            }
            $('#product-name').text(product.productName);
            $('#product-desc').text(product.productDesc);
            var imgListHtml = '';
            product.productImgList.map(function (item, index) {
                imgListHtml += '<div> <img src="'
                    + item.imgAddr + '"/></div>';
            });
            // 生成购买商品的二维码供商家扫描
            imgListHtml += '<div> <img src="/o2o/fronted/generateqrcode4product?productId='
                + product.productId + '"/></div>';
            $('#imgList').html(imgListHtml);
        }
    });
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});
