/**
 * 
 */
$(function() {
	var shopId = getQueryString("shopId");
	var isEdit = shopId ? true : false;
	var initUrl = '/o2o/shopadmin/getshopinitinfo';
	var registerShopUrl = '/o2o/shopadmin/registershop';
	var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
	var modifyShopUrl = '/o2o/shopadmin/modifyshop';
	if (!isEdit) {
		getShopInitInfo();// 调用方法
	} else {
		getShopInfo(shopId);
	}

	/**
	 * 修改商铺信息时，先获取对应商铺的信息
	 * 
	 * @param shopId
	 * @returns
	 */
	function getShopInfo(shopId) {
		$.getJSON(shopInfoUrl, function(data) {
			if (data.success) {
				var shop = data.shop;
				$('#shop-name').val(shop.shopName);
				$('#shop-addr').val(shop.shopAddr);
				$('#shop-phone').val(shop.phone);
				$('#shop-desc').val(shop.shopDesc);
				var shopCategory = '<option data-id="'
						+ shop.shopCategory.shopCategoryId + '" selected>'
						+ shop.shopCategory.shopCategoryName + '</option>';
				$('#shop-category').html(shopCategory);
				$('#shop-category').attr('disabled', 'disabled');
				var tempAreaHtml = '';
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#area').html(tempAreaHtml);
				$('#area option[data-id="'+shop.area.areaId+'"]').attr('selected','selected');
			}
		});
	}
	/**
	 * 从后台获取类别和区域，并对界面数据进行初始化
	 * 
	 * @returns
	 */
	function getShopInitInfo() {
		$.getJSON(initUrl, function(data) {
			if (data.success) {
				var tempHtml = '';
				var tempAreaHtml = '';
				data.shopCategoryList.map(function(item, index) {
					tempHtml += '<option data-id="' + item.shopCategoryId
							+ '">' + item.shopCategoryName + '</option>';
				});
				data.areaList.map(function(item, index) {
					tempAreaHtml += '<option data-id="' + item.areaId + '">'
							+ item.areaName + '</option>';
				});
				$('#shop-category').html(tempHtml);
				$('#area').html(tempAreaHtml);
			}
		});
	}

	/**
	 * 用户提交时获取填写内容，并通过ajax传送到后台
	 */
	$('#submit').click(function() {
		/**
		 * 将用户填写的商铺信息转为JSON格式
		 */
		var shop = {};
		if(isEdit)
			shop.shopId=shopId;
		shop.shopName = $('#shop-name').val();
		shop.shopAddr = $('#shop-addr').val();
		shop.phone = $('#shop-phone').val();
		shop.shopDesc = $('#shop-desc').val();
		shop.shopCategory = {
			// 双重否定等于肯定
			shopCategoryId : $('#shop-category').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		shop.area = {
			areaId : $('#area').find('option').not(function() {
				return !this.selected;
			}).data('id')
		};
		// 获取上传的图片文件
		var shopImg = $('#shop-img')[0].files[0];
		var formData = new FormData();
		formData.append('shopStr', JSON.stringify(shop));
		formData.append('shopImg', shopImg);
		var verifyCodeActual = $('#j_captcha').val();
		if (!verifyCodeActual) {
			$.toast("请输入验证码!");
			return;
		}
		formData.append('verifyCodeActual', verifyCodeActual);
		// ajax提交到后台Controller
		$.ajax({
			url : (isEdit ? modifyShopUrl : registerShopUrl),
			type : 'post',
			data : formData,
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					$.toast('提交成功！');
				} else {
					$.toast('提交失败！' + data.errMsg);
				}
				$('#captcha_img').click();
			}
		})
	});
});