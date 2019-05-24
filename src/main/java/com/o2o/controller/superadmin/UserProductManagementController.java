package com.o2o.controller.superadmin;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.o2o.dto.EchartSeries;
import com.o2o.dto.EchatXAxis;
import com.o2o.dto.UserProductMapExecution;
import com.o2o.entity.Product;
import com.o2o.entity.ProductSellDaily;
import com.o2o.entity.Shop;
import com.o2o.entity.UserProductMap;
import com.o2o.service.ProductSellDailService;
import com.o2o.service.UserProductMapService;
import com.o2o.util.HttpRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class UserProductManagementController {

    @Autowired
    private UserProductMapService userProductMapService;

    @Autowired
    private ProductSellDailService productSellDailService;

    @GetMapping("/listuserproductmapbyshop")
    @ResponseBody
    public Map<String, Object> listUserProductMapByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        int pageIndex = HttpRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpRequestUtil.getInt(request, "pageSize");
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if (pageIndex > -1 && pageSize > -1 && currentShop != null && currentShop.getShopId() != null) {
            // 添加查询条件
            UserProductMap userProductMap = new UserProductMap();
            userProductMap.setShop(currentShop);
            String productName = HttpRequestUtil.getString(request, "productName");
            // 如果通过商品名称查询
            if (productName != null) {
                Product product = new Product();
                product.setProductName(productName);
                userProductMap.setProduct(product);
            }
            // 根据查询条件获取
            UserProductMapExecution userProductMapExecution =
                userProductMapService.listUserProductMap(userProductMap, pageIndex, pageSize);
            modelMap.put("userProductMapList", userProductMapExecution.getUserProductMapList());
            modelMap.put("count", userProductMapExecution.getCount());
            modelMap.put("success", true);
            return modelMap;
        }
        modelMap.put("success", false);
        modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        return modelMap;
    }

    /**
     * echats统计近7天的商品日销量
     * 
     * @param request
     * @return
     */
    @GetMapping("/listproductselldailyinfobyshop")
    @ResponseBody
    public Map<String, Object> listProductSellDailyInfoByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
        if (currentShop != null && currentShop.getShopId() != null) {
            ProductSellDaily productSellDaily = new ProductSellDaily();
            productSellDaily.setShop(currentShop);
            Calendar calendar = Calendar.getInstance();
            // 获取昨天的日期
            calendar.add(Calendar.DATE, -1);
            Date endTime = calendar.getTime();
            // 获取7天前的日期
            calendar.add(Calendar.DATE, -6);
            Date beginTime = calendar.getTime();
            List<ProductSellDaily> productSellDailyList =
                productSellDailService.listProductSellDaily(productSellDaily, beginTime, endTime);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            // 商品列表，保证唯一性
            HashSet<String> legendData = new HashSet<>();
            // x轴数据
            HashSet<String> xData = new HashSet<>();
            // 定义series
            ArrayList<EchartSeries> series = new ArrayList<>();
            // 日销量列表
            ArrayList<Integer> totalList = new ArrayList<>();
            String currentProductName = "";
            for (int i = 0; i < productSellDailyList.size(); i++) {
                ProductSellDaily productSellDaily1 = productSellDailyList.get(i);
                legendData.add(productSellDaily1.getProduct().getProductName());
                xData.add(simpleDateFormat.format(productSellDaily1.getCreateTime()));
                // 如果是不是同一个商品（因为一个商品同一天有多个记录，每个商品全部统计大循环完就重置一次）
                if (!currentProductName.equals(productSellDaily1.getProduct().getProductName())
                    && !currentProductName.isEmpty()) {
                    // 设置上一件商品的数据
                    EchartSeries echartSeries = new EchartSeries();
                    echartSeries.setName(currentProductName);
                    echartSeries.setData(totalList.subList(0, totalList.size()));
                    series.add(echartSeries);
                    // 重置，下一件新的商品开始循环
                    totalList = new ArrayList<>();
                    currentProductName = productSellDaily1.getProduct().getProductName();
                    // 继续添加新的信息
                    totalList.add(productSellDaily1.getTotal());
                } else {
                    // 如果是同一个商品
                    totalList.add(productSellDaily1.getTotal());
                    currentProductName = productSellDaily1.getProduct().getProductName();
                }
                // 队列之末，需要将最后的一个商品销量信息也添加上
                if (i == productSellDailyList.size() - 1) {
                    EchartSeries echartSeries = new EchartSeries();
                    echartSeries.setName(currentProductName);
                    echartSeries.setData(totalList.subList(0, totalList.size()));
                    series.add(echartSeries);
                }
            }
            modelMap.put("series", series);
            modelMap.put("legendData", legendData);
            List<EchatXAxis> echatXAxisArrayList = new ArrayList<>();
            EchatXAxis echatXAxis = new EchatXAxis();
            echatXAxis.setData(xData);
            echatXAxisArrayList.add(echatXAxis);
            modelMap.put("xAxis", echatXAxisArrayList);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;
    }
}
