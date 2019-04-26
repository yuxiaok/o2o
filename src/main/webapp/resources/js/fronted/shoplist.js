$(function () {
    var loading = false;
    //分页允许的最大条数，超过此条数禁止访问后台（滚动分页）
    var maxItems = 999;
    //当前页
    var pageNum = 1;
    //页大小
    var pageSize = 10;
    //获取店铺列表的url
    var listUrl = '/o2o/fronted/listshops';
    //获取店铺类别列表以及区域列表的url
    var searchDivUrl = '/o2o/fronted/listshopspageinfo';
    //从地址栏URL中尝试获取parentId,判断是为了获取一级类别列表还是二级类别列表
    var parentId = getQueryString('parentId');
    var areaId = '';
    var shopCategoryId = '';
    var shopName = '';
    //渲染出店铺类别列表以及区域列表以供搜索
    getSearchDivData();

    // 预先加载10条店铺列表
    addItems(pageSize, pageNum);

    /**
     * 获取店铺类别列表以及区域列表信息
     */
    function getSearchDivData() {
        //如果传入了parentId,则取出此一级类别下的所有二级类别
        var url = searchDivUrl + '?' + 'parentId=' + parentId;
        $
            .getJSON(
                url,
                function (data) {
                    if (data.success) {
                        //获取从后台返回的店铺类别类别
                        var shopCategoryList = data.shopCategoryList;
                        var html = '';
                        html += '<a href="#" class="button" data-category-id=""> 全部类别  </a>';
                        //遍历店铺类别列表
                        shopCategoryList
                            .map(function (item, index) {
                                html += '<a href="#" class="button" data-category-id='
                                    + item.shopCategoryId
                                    + '>'
                                    + item.shopCategoryName
                                    + '</a>';
                            });
                        $('#shoplist-search-div').html(html);
                        //遍历区域信息列表，并进行拼接
                        var selectOptions = '<option value="">全部街道</option>';
                        var areaList = data.areaList;
                        areaList.map(function (item, index) {
                            selectOptions += '<option value="'
                                + item.areaId + '">'
                                + item.areaName + '</option>';
                        });
                        $('#area-search').html(selectOptions);
                    }
                });
    }

    /**
     * 获取分页展示的店铺列表信息（每次条件改变，都调用该方法）
     * @param pageSize
     * @param pageIndex
     */
    function addItems(pageSize, pageIndex) {
        // 拼接查询的URL，获取查询条件
        var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
            + pageSize + '&parentId=' + parentId + '&areaId=' + areaId
            + '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
        //设定加载符，若还在后台获取数据则不能再次访问后台，避免多次重复加载
        loading = true;
        //访问后台获取对应查询条件下的店铺列表
        $.getJSON(url, function (data) {
            if (data.success) {
                //获取条件下的店铺总数
                maxItems = data.count;
                var html = '';
                //遍历店铺列表
                data.shopList.map(function (item, index) {
                    html += '' + '<div class="card" data-shop-id="'
                        + item.shopId + '">' + '<div class="card-header">'
                        + item.shopName + '</div>'
                        + '<div class="card-content">'
                        + '<div class="list-block media-list">' + '<ul>'
                        + '<li class="item-content">'
                        + '<div class="item-media">' + '<img src="'
                        + item.shopImg + '" width="44">' + '</div>'
                        + '<div class="item-inner">'
                        + '<div class="item-subtitle">' + item.shopDesc
                        + '</div>' + '</div>' + '</li>' + '</ul>'
                        + '</div>' + '</div>' + '<div class="card-footer">'
                        + '<p class="color-gray">'
                        + new Date(item.lastEditTime).Format("yyyy-MM-dd")
                        + '更新</p>' + '<span>点击查看</span>' + '</div>'
                        + '</div>';
                });
                $('.list-div').append(html);
                //获取目前已经加载的总数
                var total = $('.list-div .card').length;
                //如果超过了店铺总数则不再加载
                if (total >= maxItems) {
                    // 隐藏加载提示符
                    $('.infinite-scroll-preloader').hide();
                } else {
                    // 显示加载提示符
                    $('.infinite-scroll-preloader').show();
                }
                //否在页码+1，继续load出新的店铺
                pageNum += 1;
                //加载结束，可以再次加载了
                loading = false;
                //刷新页面，显示新加载的店铺
                $.refreshScroller();
            }
        });
    }

    //屏幕下面自动进行分页搜索
    $(document).on('infinite', '.infinite-scroll-bottom', function () {
        if (loading)
            return;
        addItems(pageSize, pageNum);
    });

    //点击店铺的卡片，进入该店铺的详情页
    $('.shop-list').on('click', '.card', function (e) {
        var shopId = e.currentTarget.dataset.shopId;
        window.location.href = '/o2o/fronted/shopdetail?shopId=' + shopId;
    });

    /**
     *需要查询的类别发生改变了，重新查询
     */
    $('#shoplist-search-div').on(
        'click',
        '.button',
        function (e) {
            if (parentId) {// 如果传递过来的是一个父类下的子类
                shopCategoryId = e.target.dataset.categoryId;
                //若之前已选定了别的category,则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    shopCategoryId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                //由于查询条件改变，清空店铺列表再进行查询
                $('.list-div').empty();
                //页码置为1
                pageNum = 1;
                addItems(pageSize, pageNum);
            } else {// 如果传递过来的父类为空，则按照父类查询
                parentId = e.target.dataset.categoryId;
                //若之前已选定了别的category,则移除其选定效果，改成选定新的
                if ($(e.target).hasClass('button-fill')) {
                    $(e.target).removeClass('button-fill');
                    parentId = '';
                } else {
                    $(e.target).addClass('button-fill').siblings()
                        .removeClass('button-fill');
                }
                //由于查询条件改变，清空店铺列表再进行查询
                $('.list-div').empty();
                //重置页码
                pageNum = 1;
                addItems(pageSize, pageNum);
                parentId = '';
            }

        });

    /**
     *需要查询的店铺名字发生改变后，重新查询
     */
    $('#search').on('change', function (e) {
        shopName = e.target.value;
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    /**
     * 区域发生改变，重新查询
     */
    $('#area-search').on('change', function () {
        areaId = $('#area-search').val();
        $('.list-div').empty();
        pageNum = 1;
        addItems(pageSize, pageNum);
    });

    /**
     * 侧边栏
     */
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });

    /**
     *初始化页面
     */
    $.init();
});
