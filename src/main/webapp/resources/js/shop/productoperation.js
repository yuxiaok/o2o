$(function () {
    //从URL里获取productId参数的值
    var productId = getQueryString("productId");
    //通过productId获取商品信息的URL
    var infoUrl = '/o2o/shopadmin/getproductbyid?productId=' + productId;
    //获取该店铺的商品类别列表的URL
    var categoryUrl = '/o2o/shopadmin/getproductcategorylist';
    //更新商品信息的URL
    var productPostUrl = '/o2o/shopadmin/modifyProduct';

    //由于商品添加和编辑使用的是同一个页面
    //判断本次是添加操作还是编辑操作
    //判断是下架还是编辑
    var statusChange = true;
    if (productId) {
        //若有productId,则为编辑操作
        getInfo(productId);
        //编辑
        statusChange = false;
    } else {
        //添加操作
        getCategory();
        productPostUrl = '/o2o/shopadmin/addproduct';
    }

    //获取需要编辑的商品的商品信息，并赋值给表单
    function getInfo(id) {
        $.getJSON(infoUrl, function (data) {
            if (data.success) {
                //从返回的JSON当中获取product对象的信息，并赋值给表单
                var product = data.product;
                $('#product-name').val(product.productName);
                $('#priority').val(product.priority);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price').val(product.promotionPrice);
                $('#product-desc').val(product.productDesc);
                //获取原本商品的类别以及该店铺的所有商品类别列表
                var optionHtml = '';
                var optionArr = data.productCategoryList;
                var optionSelected = product.productCategory.productCategoryId;

                //生成前端的HTML商品类别列表，并默认选择编辑前的商品类别
                optionArr.map(function (item, index) {
                    var isSelect =
                        optionSelected === item.productCategoryId ? 'selected' : '';
                    optionHtml += '<option data-value="' + item.productCategoryId + '" ' + isSelect + '>'
                        + item.productCategoryName
                        + '</option>';
                });
                $('#category').html(optionHtml);
            }
        });
    }

    //为商品添加操作提供该店铺下面的所有商品类别列表
    function getCategory() {
        $.getJSON(categoryUrl, function (data) {
            if (data.jg) {
                var productCategoryList = data.data;
                var optionHtml = '';

                productCategoryList.map(function (item, index) {
                    optionHtml += '<option data-value="'
                        + item.productCategoryId
                        + '">'
                        + item.productCategoryName
                        + '</option>';
                });
            }

            $('#category').html(optionHtml);
        });
    }

    //针对商品详情图控件组，若该控件的最后一个元素发生了变化（即上传了图片）
    //且控件总数未超过6个，则生成一个新的文件上传控件
    $('#detail-img').on('change', '.detail-img:last-child', function () {
        if ($('.detail-img').length < 6) {
            $('#detail-img').append('<input type="file" style="margin-top:5px;" class="detail-img">');
        }
    });

    //提交按钮的事件相应，分别对商品添加和编辑操作做不同相应
    $('#submit').click(function () {
        //创建商品JSON对象，并从表单获取对应的属性值
        var product = {};
        product.productName = $('#product-name').val();
        product.productDesc = $('#product-desc').val();
        product.priority = $('#priority').val();
        product.point = $('#point').val();
        product.normalPrice = $('#normal-price').val();
        product.promotionPrice = $('#promotion-price').val();
        //获取选定的商品类别值
        product.productCategory = {
            productCategoryId: $('#category').find('option').not(function () {
                return !this.selected;
            }).data('value')
        };
        product.productId = productId;

        //获取缩略图文件流
        var thumbnail = $('#small-img')[0].files[0];
        //生成表单对象，用于接收参数并传递给后台
        var formData = new FormData();
        formData.append('thumbnail', thumbnail);
        formData.append('productStr', JSON.stringify(product));

        //获取详情图文件流
        $('.detail-img').map(function (index, item) {
            //判断该控件是否已选择了文件
            if ($('.detail-img')[index].files.length > 0) {
                //将第i个文件流赋值给key为productImgi的表单键值对里
                formData.append('productImg' + index,
                    $('.detail-img')[index].files[0]);
            }
        });

        //获取验证码
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
            $.toast('请输入验证码！');
            return;
        }

        formData.append("verifyCodeActual", verifyCodeActual);
        formData.append("statusChange", statusChange);

        //提交数据到后台
        $.ajax({
            url: productPostUrl,
            type: 'post',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                    $('#captcha_img').click();
                } else {
                    $.toast('提交失败！' + data.errMsg);
                    $('#captcha_img').click();
                }
            }
        });
    });

});

