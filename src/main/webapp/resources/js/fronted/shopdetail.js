$(function () {
    //是否正在加载
    var loading = false;
    //分页允许返回的最大条数，超过此数则禁止访问后台
    var maxItems = 20;
    //默认一页返回的商品数量
    var pageSize = 10;
    //列出商品列表的URL
    var listUrl = '/o2o/fronted/listproductsbyshop';

    var pageNum = 1;
    var shopId = getQueryString('shopId');
    var productCategoryId = '';
    var productName = '';
    //获取本店铺信息以及商品类别信息列表的URL
    var searchDivUrl = '/o2o/fronted/listshopdetailpageinfo?shopId='
        + shopId;
    //渲染出店铺基本信息以及商品类别列表以供搜索
    getSearchDivData();
    //预先加载10条商品信息
    addItems(pageSize, pageNum);

    //给兑换礼品的a标签赋值兑换礼品的URL
    $('#exchangelist').attr('href', '/o2o/fronted/awardlist?shopId=' + shopId);

    /**
     *获取本店铺信息以及商品类别信息列表
     */
    function getSearchDivData() {
        var url = searchDivUrl;
        $
            .getJSON(
                url,
                function (data) {
                    if (data.success) {
                        var shop = data.shop;
                        $('#shop-cover-pic').attr('src', getContextPath() + shop.shopImg);
                        $('#shop-update-time').html(
                            new Date(shop.lastEditTime)
                                .Format("yyyy-MM-dd"));
                        $('#shop-name').html(shop.shopName);
                        $('#shop-desc').html(shop.shopDesc);
                        $('#shop-addr').html(shop.shopAddr);
                        $('#shop-phone').html(shop.phone);

                        //获取后台返回的商品类别列表
                        var productCategoryList = data.productCategoryList;
                        var html = '';
                        //遍历赋值
                        productCategoryList
                            .map(function (item, index) {
                                html += '<a href="#" class="button" data-product-search-id='
                                    + item.productCategoryId
                                    + '>'
                                    + item.productCategoryName
                                    + '</a>';
                            });
                        //绑定
                        $('#shopdetail-button-div').html(html);
                    }
                });
    }

    /**
     * 获取分页展示的商品列表信息
     * @param pageSize
     * @param pageIndex
     */
    function addItems(pageSize, pageIndex) {
        // 拼接参数
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&productCategoryId=' + productCategoryId
            + '&productName=' + productName + '&shopId=' + shopId;
        //如果还在获取数据，则不能再次访问后台，避免多次重复加载
        loading = true;
        //获取商品列表
        $.getJSON(url, function (data) {
            if (data.success) {
                //获取当前条件下的商品总数
                maxItems = data.count;
                var html = '';
                data.productList.map(function (item, index) {
                    html += '' + '<div class="card" data-product-id='
                        + item.productId + '>'
                        + '<div class="card-header">' + item.productName
                        + '</div>' + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + getContextPath() + item.imgAddr + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.productDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);
                //已显示的总数，包含之前加载的
                var total = $('.list-div .card').length;
                //如果大于总条数了
                if (total >= maxItems) {
                    // 隐藏加载提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    //显示提示符
                    $('.infinite-scroll-preloader').show();
                }
                //否则就获取下一页数据
                pageNum += 1;
                loading = false;
                //刷新页面
                $.refreshScroller();
            }
        });
    }

//下滑屏幕自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });
//选择新的商品类别之后，重置页码，清空原先的商品列表，按照新的类别去查询
    $('#shopdetail-button-div').on(
        'click',
        '.button',
        function (e) {
            productCategoryId = e.target.dataset.productSearchId;
            if (productCategoryId) {
                //若之前已选定了别的Category,则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    productCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                $('.list-div').empty();
                pageNum = 1;
                addItems(pageSize, pageNum);
            }
        });

    //点击商品的卡片进入该商品的详情页
    $('.list-div')
        .on(
            'click',
            '.card',
            function (e) {
                var productId = e.currentTarget.dataset.productId;
                window.location.href = '/o2o/fronted/productdetail?productId=' + productId;
            });

    //需要查询的商品名字发生变化后，重置页码，清空原先的商品列表，按照新的名字查询
    $('#search').on('change', function (e) {
        productName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    //右侧栏
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    //初始化
    $.init();
});
