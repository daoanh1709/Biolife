/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entitybeans.Customers;
import com.entitybeans.OrderDetails;
import com.entitybeans.Orders;
import com.sessionbeans.CustomersFacadeLocal;
import com.sessionbeans.OrderDetailsFacadeLocal;
import com.sessionbeans.OrderStatusFacadeLocal;
import com.sessionbeans.OrdersFacadeLocal;
import com.sessionbeans.ProductsFacadeLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author PC
 */
@Named(value = "orderMB")
@SessionScoped
public class OrderMB implements Serializable {

    @EJB
    private ProductsFacadeLocal productsFacade;

    @EJB
    private OrderStatusFacadeLocal orderStatusFacade;

    @EJB
    private CustomersFacadeLocal customersFacade;

    @EJB
    private OrderDetailsFacadeLocal orderDetailsFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    private LineChartModel lineModel;

    private BarChartModel barModel;

    private Orders order;

    @PostConstruct
    public void init() {
        createLineModel();
        createBarModel();
    }

    public OrderMB() {
        order = new Orders();
    }

    public String view() {
        return "orders";
    }

    public void viewOrder(String id) {
        order = ordersFacade.find(id);
        List<OrderDetails> list = new ArrayList<>();
        Orders o = ordersFacade.find(id);
        if (o != null) {
            list = (List<OrderDetails>) o.getOrderDetailsCollection();
        }
    }
    
    public String findProductImg(String id) {
        return productsFacade.find(id).getProductImageURL();
    }
    
    public String findProductName(String id) {
        return productsFacade.find(id).getProductName();
    }

    public List<OrderDetails> listOrderDetail(String id) {
        List<OrderDetails> listOrderDetails = orderDetailsFacade.findAll();
        List<OrderDetails> list = new ArrayList<>();
        Orders o = ordersFacade.find(id);
        if (o != null) {
            for (int i = 0; i < listOrderDetails.size(); i++) {
                OrderDetails od = listOrderDetails.get(i);
                if (od.getOrderDetailsPK().getOrderID().equals(id)) {
                    list.add(od);
                }
            }
        }

        return list;
    }

    public void cancelOrder(String id) {
        Orders o = ordersFacade.find(id);
        o.setStatusID(orderStatusFacade.find(4));
        ordersFacade.edit(o);
    }
    
    public void receiveOrder(String id) {
        Orders o = ordersFacade.find(id);
        o.setStatusID(orderStatusFacade.find(3));
        ordersFacade.edit(o);
    }

    public void confirmOrder(String id) {
        Orders o = ordersFacade.find(id);
        o.setStatusID(orderStatusFacade.find(1));
        ordersFacade.edit(o);
    }

    public List<Orders> showCustomerOrders(String userID) {
        Customers c = customersFacade.find(userID);
        List<Orders> listOrder = ordersFacade.getCustomerOrders(c);
        return listOrder;
    }

    public List<Orders> showAllOrders() {
        return ordersFacade.findAll();
    }

    public List<Integer> getListOrderByMonth1() {
        List<Integer> listOrderByMonth = new ArrayList<>();

        Date today = new Date();
        int month = today.getMonth() + 1;

        for (int i = 0; i < month; i++) {
            Date fisrtDateofMonth = new Date(today.getYear(), i, 1);
            Date fisrtDateofNextMonth = new Date(today.getYear(), i + 1, 1);
            int num = ordersFacade.countMonthOrder(fisrtDateofMonth, fisrtDateofNextMonth);
            System.out.println(num);
            listOrderByMonth.add(num);
        }

        return listOrderByMonth;
    }

    public List<Double> getListProfitByMonth() {
        List<Double> listProfitByMonth = new ArrayList<>();

        Date today = new Date();
        int month = today.getMonth() + 1;

        for (int i = 0; i < month; i++) {
            Date fisrtDateofMonth = new Date(today.getYear(), i, 1);
            Date fisrtDateofNextMonth = new Date(today.getYear(), i + 1, 1);

            List<Orders> listOrder = new ArrayList<>();
            listOrder = ordersFacade.getOrdersByDate(fisrtDateofMonth, fisrtDateofNextMonth);

            double todayProfit = 0;
            for (int j = 0; j < listOrder.size(); j++) {
                todayProfit = todayProfit + calculateTotalAmount(listOrder.get(j).getOrderID());
            }

            listProfitByMonth.add(todayProfit);
        }

        return listProfitByMonth;
    }

    public List<Object[]> getListOrderByMonth() {
        List<Object[]> list = new ArrayList<>();

        Date today = new Date();
        int month = today.getMonth() + 1;

        for (int i = 0; i < month; i++) {
            Object[] obj = new Object[2];

            Date fisrtDateofMonth = new Date(today.getYear(), i, 1);
            Date fisrtDateofNextMonth = new Date(today.getYear(), i + 1, 1);

            List<Orders> listOrder = new ArrayList<>();
            listOrder = ordersFacade.getOrdersByDate(fisrtDateofMonth, fisrtDateofNextMonth);

            double todayProfit = 0;
            for (int j = 0; j < listOrder.size(); j++) {
                todayProfit = todayProfit + calculateTotalAmount(listOrder.get(j).getOrderID());
            }

            obj[0] = listOrder.size();
            obj[1] = todayProfit;
            list.add(obj);
        }

        return list;
    }

    public double calculateTodayProfit() {
        Date date = new Date();

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        List<Orders> listOrder = new ArrayList<>();
        listOrder = ordersFacade.getOrdersByDate(date, new Date(date.getTime() + (1000 * 60 * 60 * 24)));
        System.out.println(listOrder.size());
        double todayProfit = 0;

        for (int i = 0; i < listOrder.size(); i++) {
            todayProfit = todayProfit + calculateTotalAmount(listOrder.get(i).getOrderID());
        }

        return todayProfit;
    }

    public double calculateTotalProfit() {
        List<OrderDetails> listOrderDetails = new ArrayList<OrderDetails>();
        listOrderDetails = orderDetailsFacade.findAll();

        double totalProfit = 0;

        for (int i = 0; i < listOrderDetails.size(); i++) {
            OrderDetails o = listOrderDetails.get(i);
            totalProfit = totalProfit + (o.getQuantity() * o.getUnitPrice().doubleValue() * (1 - o.getDiscount()));
        }

        return totalProfit;
    }

    public double calculateTotalAmount(String id) {
        List<OrderDetails> listOrderDetails = orderDetailsFacade.findAll();
        double totalAmount = 0;

        for (int i = 0; i < listOrderDetails.size(); i++) {
            OrderDetails o = listOrderDetails.get(i);
            if (o.getOrderDetailsPK().getOrderID().equals(id)) {
                totalAmount = totalAmount + (o.getQuantity() * o.getUnitPrice().doubleValue() * (1 - o.getDiscount()));
            }
        }

        return totalAmount;
    }

    public Long countTodayOrders() {
        createBarModel();
        createLineModel();
        
        Date date = new Date();

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        Long todayOrders = ordersFacade.countOrder(date);
        return todayOrders;
    }

    public void createLineModel() {
        lineModel = new LineChartModel();
        ChartData data = new ChartData();

        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();

        List<String> labels = new ArrayList<>();

        Calendar c = Calendar.getInstance();
        List<Double> list = getListProfitByMonth();

        for (int i = 0; i < list.size(); i++) {
            c.set(Calendar.MONTH, i);
            labels.add(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));

            values.add((Double) list.get(i));
        }

        dataSet.setData(values);
        dataSet.setFill(false);
        dataSet.setLabel("Total Amount");
        dataSet.setBorderColor("rgb(75, 192, 192)");
        dataSet.setLineTension(0.1);
        data.addChartDataSet(dataSet);

        data.setLabels(labels);

        //Options
        LineChartOptions options = new LineChartOptions();
        Title title = new Title();
        title.setDisplay(true);
        title.setText("Line Chart");
        options.setTitle(title);

        lineModel.setOptions(options);
        lineModel.setData(data);
    }

    public void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Number of Orders");

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        List<String> bgColor = new ArrayList<>();
        String[] bgColorArr = {"rgba(255, 99, 132, 0.2)",
            "rgba(255, 159, 64, 0.2)",
            "rgba(255, 205, 86, 0.2)",
            "rgba(75, 192, 192, 0.2)",
            "rgba(54, 162, 235, 0.2)",
            "rgba(153, 102, 255, 0.2)",
            "rgba(201, 203, 207, 0.2)"};

        List<String> borderColor = new ArrayList<>();
        String[] borderColorArr = {"rgb(255, 99, 132)",
            "rgb(255, 159, 64)",
            "rgb(255, 205, 86)",
            "rgb(75, 192, 192)",
            "rgb(54, 162, 235)",
            "rgb(153, 102, 255)",
            "rgb(201, 203, 207)"};

        Calendar c = Calendar.getInstance();
        List<Integer> list = getListOrderByMonth1();
        int num = 0;
        for (int i = 0; i < list.size(); i++) {
            c.set(Calendar.MONTH, i);
            labels.add(c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()));

            bgColor.add(bgColorArr[num]);
            borderColor.add(borderColorArr[num]);

            if (num < bgColorArr.length - 1) {
                num++;
            } else {
                num = 0;
            }

            values.add((Integer) list.get(i));
        }

        barDataSet.setData(values);

        barDataSet.setBackgroundColor(bgColor);

        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        data.setLabels(labels);
        barModel.setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Bar Chart");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        barModel.setOptions(options);
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

}
