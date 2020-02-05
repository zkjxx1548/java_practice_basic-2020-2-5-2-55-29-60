package com.thoughtworks;

import java.util.Arrays;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("请点餐（菜品Id x 数量，用逗号隔开）：");
    String selectedItems = scan.nextLine();
    String summary = bestCharge(selectedItems);
    System.out.println(summary);
  }

  /**
   * 接收用户选择的菜品和数量，返回计算后的汇总信息
   *
   * @param selectedItems 选择的菜品信息
   */
  public static String bestCharge(String selectedItems) {
    // 此处补全代码
    String itemId;
    int itemCount;
    int itemIndex;
    int itemPrice;
    int allPrice = 0;
    int fullReduction;
    int halfReduction = 0;
    String halfItemNames = "";
    String[] arrSelect = selectedItems.split(",");
    String res = "============= 订餐明细 =============\n";
    for (int i=0; i<arrSelect.length; i++) {
      itemId = arrSelect[i].substring(0, 8);
      itemCount = Integer.parseInt(arrSelect[i].substring(11));
      itemIndex = Arrays.binarySearch(getItemIds(), itemId);
      itemPrice = (int) getItemPrices()[itemIndex];
      allPrice += itemCount*itemPrice;
      if (Arrays.binarySearch(getHalfPriceIds(), itemId) == 0 || Arrays.binarySearch(getHalfPriceIds(), itemId) == 1) {
        halfReduction += itemCount * itemPrice / 2;
        if (halfItemNames == "") {
          halfItemNames += getItemNames()[itemIndex];
        } else {
          halfItemNames += "，" + getItemNames()[itemIndex];
        }
      }
      res += getItemNames()[itemIndex] + " x " + itemCount + " = " + itemCount*itemPrice + "元\n";
    }
    res += "-----------------------------------\n";
    fullReduction = allPrice / 30 * 6;

    //判断用什么优惠
    if (fullReduction != 0 || halfReduction !=0) {
      if (fullReduction >= halfReduction) {
        res += "使用优惠:\n"
                + "满30减6元，省" + fullReduction + "元\n"
                + "-----------------------------------\n"
                + "总计：" + (allPrice-fullReduction) + "元\n";
      } else {
        res += "使用优惠:\n"
                + "指定菜品半价(" + halfItemNames + ")，省" + halfReduction + "元\n"
                + "-----------------------------------\n"
                + "总计：" + (allPrice-halfReduction) + "元\n";
      }
    } else {
      res += "总计：" + allPrice + "元\n";
    }
    res += "===================================";

    return res;
  }

  /**
   * 获取每个菜品依次的编号
   */
  public static String[] getItemIds() {
    return new String[]{"ITEM0001", "ITEM0013", "ITEM0022", "ITEM0030"};
  }

  /**
   * 获取每个菜品依次的名称
   */
  public static String[] getItemNames() {
    return new String[]{"黄焖鸡", "肉夹馍", "凉皮", "冰粉"};
  }

  /**
   * 获取每个菜品依次的价格
   */
  public static double[] getItemPrices() {
    return new double[]{18.00, 6.00, 8.00, 2.00};
  }

  /**
   * 获取半价菜品的编号
   */
  public static String[] getHalfPriceIds() {
    return new String[]{"ITEM0001", "ITEM0022"};
  }
}
