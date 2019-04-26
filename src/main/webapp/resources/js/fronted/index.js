$(function() {
    //定义访问后台，获取头条列表以及一级类别列表的URL
    var url = '/o2o/fronted/listmainpageinfo';
    //访问后台
    $.getJSON(url, function (data) {
        if (data.success) {
            //头条列表
            var headLineList = data.headLineList;
            var swiperHtml = '';
            //遍历列表，并拼接出轮播图
            headLineList.map(function (item, index) {
                swiperHtml += ''
                    + '<div class="swiper-slide img-wrap">'
                    +'<a href="'+item.lineLink+'" external>'
                    + '<img class="banner-img" src="'+ item.lineImg +'" alt="'+ item.lineName +'">'
                    + '</a>'
                    + '</div>';
            });
            //将轮播图赋值给前端HTML控件
            $('.swiper-wrapper').html(swiperHtml);
            //设定轮播图轮换时间为3s
            $(".swiper-container").swiper({
                autoplay: 3000,
                //用户对轮播图进行操作时，是否自动停止轮播
                autoplayDisableOnInteraction: false
            });
            //商店大类列表
            var shopCategoryList = data.shopCategoryList;
            var categoryHtml = '';
            //遍历大类列表，拼接出两两一行的类别
            shopCategoryList.map(function (item, index) {
                categoryHtml += ''
                    +  '<div class="col-50 shop-classify" data-category='+ item.shopCategoryId +'>'
                    +      '<div class="word">'
                    +          '<p class="shop-title">'+ item.shopCategoryName +'</p>'
                    +          '<p class="shop-desc">'+ item.shopCategoryDesc +'</p>'
                    +      '</div>'
                    +      '<div class="shop-classify-img-warp">'
                    +          '<img class="shop-img" src="'+ item.shopCategoryImg +'">'
                    +      '</div>'
                    +  '</div>';
            });
            //前端展示
            $('.row').html(categoryHtml);
        }
    });

    //如果点击“我”的时候，显示侧边栏
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    //点击一级类别时，获取一级类别下的商品
    $('.row').on('click', '.shop-classify', function (e) {
        var shopCategoryId = e.currentTarget.dataset.category;
        var newUrl = '/o2o/fronted/shoplist?parentId=' + shopCategoryId;
        window.location.href = newUrl;
    });

});
